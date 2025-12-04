document.addEventListener("DOMContentLoaded", () => {
  loadDates();
});

const dateList = document.getElementById("dateList");
const noteThumbList = document.getElementById("noteThumbList");
const noteContentArea = document.getElementById("noteContentArea");
const selectedDateTitle = document.getElementById("selectedDateTitle");
const noDateMessage = document.getElementById("noDateMessage");

// 🔥 로그인한 학생 ID
const studentIdInput = document.getElementById("studentId");
const studentId = studentIdInput ? studentIdInput.value : null;

function loadDates() {
  fetch("/note/dates")
    .then(res => res.json())
    .then(dates => {
      dateList.innerHTML = "";
      noteThumbList.innerHTML = "";
      noteContentArea.innerHTML = `
        <div class="note-main-placeholder">
          날짜를 선택해주세요.
        </div>
      `;

      if (dates.length === 0) {
        noDateMessage.style.display = "block";
        return;
      }

      noDateMessage.style.display = "none";

      dates.forEach((d, i) => {
        const li = document.createElement("li");
        li.className = "date-item" + (i === 0 ? " active" : "");
        li.dataset.date = d;
        li.textContent = d;

        li.addEventListener("click", () => {
          document.querySelectorAll(".date-item")
            .forEach(el => el.classList.remove("active"));
          li.classList.add("active");
          loadNotesByDate(d);
        });

        dateList.appendChild(li);
      });

      // 첫 날짜 필기 로드
      loadNotesByDate(dates[0]);
    });
}

function loadNotesByDate(date) {
  if (!studentId) {
    console.error("studentId 없음");
    return;
  }

  selectedDateTitle.textContent = date + " 필기";

  fetch(`/note/list/byDate/student?studentId=${studentId}&date=${date}`)
    .then(res => res.json())
    .then(notes => {

      if (!Array.isArray(notes)) {
        console.error("서버 응답이 배열이 아님:", notes);
        noteThumbList.innerHTML = "";
        noteContentArea.innerHTML = "<div class='note-main-placeholder'>필기 없음</div>";
        return;
      }

      noteThumbList.innerHTML = "";

      if (notes.length === 0) {
        noteContentArea.innerHTML = `
          <div class="note-main-placeholder">필기 없음</div>
        `;
        return;
      }

      notes.forEach((note, idx) => {
        const btn = document.createElement("button");
        btn.className = "note-thumb-btn";
        btn.textContent = note.title || "(제목 없음)";

        btn.addEventListener("click", () => {
          document.querySelectorAll(".note-thumb-btn")
            .forEach(el => el.classList.remove("active"));
          btn.classList.add("active");

          noteContentArea.textContent = note.content;
        });

        noteThumbList.appendChild(btn);

        if (idx === 0) noteContentArea.textContent = note.content;
      });
    })
    .catch(err => {
      console.error("loadNotesByDate 에러:", err);
    });
}
