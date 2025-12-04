document.addEventListener("DOMContentLoaded", () => {
    console.log("[note] loaded");

    const $ = s => document.querySelector(s);

    // 🔥 로그인한 학생 ID (이제 무조건 있음)
    const studentId = document.getElementById("studentId").value;
    console.log("studentId =", studentId);

    // ===== PDF UI =====
    const fileInput = $("#fileInput");
    const btnPick = $("#btnPick");
    const pdfObj = $("#pdf");
    const placeholder = $("#placeholder");
    const dropzone = $("#dropzone");

    // ===== Notes =====
    const note = $("#note");
    const preview = $("#preview");
    const previewToggle = $("#previewToggle");
    const btnCopy = $("#btnCopy");
    const btnSave = $("#btnSave");

    // ===== Categories =====
    const categorySelect = $("#categorySelect");
    const btnAddCategory = $("#btnAddCategory");
    const currentCategoryLabel = $("#currentCategoryLabel");

    // ===== 날짜바 & 이미지 리스트 =====
    const dateBar = $("#noteDateBar");
    const noteImageList = $("#noteImageList");

    let categories = [];
    let selectedCategoryId = "default";
    let currentFileName = "default.pdf";

    /* ==========================================
       PDF 열기
    ========================================== */
    btnPick.addEventListener("click", () => fileInput.click());

    fileInput.addEventListener("change", () => {
        const f = fileInput.files[0];
        if (!f) return;

        openPDF(f);
    });

    // Drag & Drop
    ["dragenter", "dragover"].forEach(evt =>
        dropzone.addEventListener(evt, e => {
            e.preventDefault();
            dropzone.classList.add("hover");
        })
    );

    ["dragleave", "drop"].forEach(evt =>
        dropzone.addEventListener(evt, e => {
            e.preventDefault();
            dropzone.classList.remove("hover");
        })
    );

    dropzone.addEventListener("drop", e => {
        const f = e.dataTransfer.files[0];
        if (f && f.type === "application/pdf") openPDF(f);
    });

    function openPDF(f) {
        currentFileName = f.name;
        const url = URL.createObjectURL(f);

        placeholder.style.display = "none";
        pdfObj.style.display = "block";
        pdfObj.data = url + "#toolbar=1&view=FitH";

        const today = new Date().toISOString().split("T")[0];
        loadNoteFromDB(today);
    }

    /* ==========================================
       카테고리
    ========================================== */
    function loadCategories() {
        const saved = localStorage.getItem("note::categories");
        if (saved) categories = JSON.parse(saved);
        else categories = [{ id: "default", name: "기본" }];

        renderCategorySelect();
        selectedCategoryId = categories[0].id;
        updateCurrentCategoryLabel();
    }

    function renderCategorySelect() {
        categorySelect.innerHTML = "";
        categories.forEach(c => {
            const opt = document.createElement("option");
            opt.value = c.id;
            opt.textContent = c.name;
            categorySelect.appendChild(opt);
        });
        categorySelect.value = selectedCategoryId;
    }

    btnAddCategory.addEventListener("click", () => {
        const name = prompt("카테고리 이름?");
        if (!name || name.trim() === "") return;

        const id = "cat_" + Date.now();
        categories.push({ id, name: name.trim() });
        localStorage.setItem("note::categories", JSON.stringify(categories));
        selectedCategoryId = id;

        renderCategorySelect();
        updateCurrentCategoryLabel();
    });

    categorySelect.addEventListener("change", e => {
        selectedCategoryId = e.target.value;
        updateCurrentCategoryLabel();
    });

    function updateCurrentCategoryLabel() {
        const cat = categories.find(c => c.id === selectedCategoryId);
        currentCategoryLabel.textContent = `${cat.name} 카테고리`;
    }

    /* ==========================================
       필기 저장 / 불러오기 (DB)
    ========================================== */
    btnSave.addEventListener("click", saveNoteToDB);

	function saveNoteToDB() {
	    if (!selectedCategoryId) selectedCategoryId = "basic";

	    fetch("/note/save", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({
	            studentId: studentId,
	            title: currentFileName || "메모",
	            content: note.value,
	            noteDate: new Date().toISOString().split("T")[0],
	            type: "NOTE"
	        })
	    })
	    .then(res => res.text())
	    .then(msg => alert("🔥 필기 DB 저장 완료!"))
	    .catch(err => alert("⚠ 저장 오류: " + err));
	}

	function loadNoteFromDB(date) {
	    if (!date) return;

	    fetch(`/note/list/byDate/student?studentId=${studentId}&date=${date}`)
	        .then(res => res.json())
	        .then(data => {
	            if (data.length > 0) {
	                note.value = data[0].content;
	            } else {
	                note.value = "";
	            }
	        });
	}

    /* ==========================================
       프리뷰
    ========================================== */
    previewToggle.addEventListener("change", () => {
        const on = previewToggle.checked;
        note.style.display = on ? "none" : "block";
        preview.style.display = on ? "block" : "none";
        if (on) renderPreview();
    });

    note.addEventListener("input", () => {
        if (previewToggle.checked) renderPreview();
    });

    function renderPreview() {
        preview.innerHTML = note.value
            .replace(/</g, "&lt;")
            .replace(/\n/g, "<br>");
    }

    /* ==========================================
       복사
    ========================================== */
    btnCopy.addEventListener("click", () => {
        note.select();
        document.execCommand("copy");
        alert("복사됨!");
    });

    /* ==========================================
       날짜 / 이미지 리스트
    ========================================== */
    function loadDatePills() {
        fetch("/note-images/dates")
            .then(res => res.json())
            .then(dates => {
                dateBar.innerHTML = "";
                dates.forEach((date, idx) => {
                    const btn = document.createElement("button");
                    btn.className = "date-pill" + (idx === 0 ? " active" : "");
                    btn.textContent = date;

                    btn.addEventListener("click", () => {
                        document
                            .querySelectorAll(".date-pill")
                            .forEach(p => p.classList.remove("active"));
                        btn.classList.add("active");
                        loadImagesByDate(date);
                    });

                    dateBar.appendChild(btn);
                });
            });
    }

    function loadImagesByDate(date) {
        fetch(`/note-images?date=${encodeURIComponent(date)}`)
            .then(res => res.json())
            .then(images => {
                noteImageList.innerHTML = "";
                images.forEach(url => {
                    const img = document.createElement("img");
                    img.src = url;
                    img.addEventListener("click", () => {
                        pdfObj.style.display = "none";
                        pdfObj.data = "";
                        placeholder.style.display = "none";
                    });
                    noteImageList.appendChild(img);
                });
            });
    }

    /* ============================
       초기화
    ============================ */
    loadCategories();
});
