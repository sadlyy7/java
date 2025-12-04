document.addEventListener("DOMContentLoaded", () => {
    console.log("Calendar JS Loaded!");

    const year = 2025;
    const month = 12;

    const todoList = document.getElementById("todo-list");
    const addBtn = document.getElementById("add-todo");
    const todoInput = document.getElementById("todo-input");

    const teamBtn = document.getElementById("team-select-btn");
    if (teamBtn) {
        teamBtn.addEventListener("click", () => {
            alert("팀 선택 창 열기");
        });
    }

    renderCalendar(year, month);

    const todayCell = document.querySelector(".calendar-day.today");
    if (todayCell) {
        todayCell.classList.add("selected");
        loadNotes(todayCell.dataset.date);
    }


    addBtn.addEventListener("click", () => {
        const text = todoInput.value.trim();
        if (!text) return;

        const selectedDay = document.querySelector(".calendar-day.selected");
        if (!selectedDay) {
            alert("날짜를 먼저 선택하세요!");
            return;
        }

        const date = selectedDay.getAttribute("data-date");

        fetch("/note/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                studentId: 1, // 로그인 연동 시 변경
                title: "TODO",
                content: text,
                noteDate: date,
                type: "TODO"
            }),
        })
            .then(res => res.text())
            .then(msg => {
                alert("TODO 저장 완료!");
                loadNotes(date);
                todoInput.value = "";
            })
            .catch(err => alert("저장 실패: " + err));
    });
});

// ============================
// 🔥 달력 생성 함수
// ============================
function renderCalendar(year, month) {
    const calendar = document.getElementById("calendar");
    calendar.innerHTML = "";

    const now = new Date();
    const todayYear = now.getFullYear();
    const todayMonth = now.getMonth() + 1;
    const todayDate = now.getDate();

    const title = document.createElement("h3");
    title.textContent = `${year}년 ${month}월`;
    title.style.textAlign = "center";
    title.style.color = "#357dad";
    calendar.appendChild(title);

    const daysOfWeek = ["일", "월", "화", "수", "목", "금", "토"];
    const headerRow = document.createElement("div");
    headerRow.className = "calendar-header";

    daysOfWeek.forEach(day => {
        const cell = document.createElement("div");
        cell.textContent = day;
        headerRow.appendChild(cell);
    });
    calendar.appendChild(headerRow);

    const firstDay = new Date(year, month - 1, 1).getDay();
    const lastDate = new Date(year, month, 0).getDate();

    const grid = document.createElement("div");
    grid.className = "calendar-grid";

    for (let i = 0; i < firstDay; i++) grid.appendChild(document.createElement("div"));

    for (let date = 1; date <= lastDate; date++) {
        const cell = document.createElement("div");
        cell.classList.add("calendar-day");

        const yyyy = year;
        const mm = String(month).padStart(2, "0");
        const dd = String(date).padStart(2, "0");

        cell.dataset.date = `${yyyy}-${mm}-${dd}`;
        cell.textContent = date;

        if (year === todayYear && month === todayMonth && date === todayDate) {
            cell.classList.add("today");
        }

        cell.addEventListener("click", function () {
            document.querySelectorAll(".calendar-day").forEach(d => d.classList.remove("selected"));
            this.classList.add("selected");

            loadNotes(this.dataset.date);
        });

        grid.appendChild(cell);
    }

    calendar.appendChild(grid);
}

// ============================
// 🔥 해당 날짜 TODO 로드
// ============================
function loadNotes(date) {
    fetch(`/note/list/byDate/student?studentId=1&date=${date}`)
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("todo-list");
            list.innerHTML = "";

            if (!data || data.length === 0) {
                list.innerHTML = "<li>필기가 없습니다</li>";
                return;
            }

            data.forEach(note => {
                const li = document.createElement("li");
                li.textContent = note.content;
                list.appendChild(li);
            });
        })
        .catch(err => console.error("loadNotes 에러:", err));
}

document.addEventListener("DOMContentLoaded", () => {
    const teamBtn = document.getElementById("team-select-btn");
    const teamModal = document.getElementById("team-modal");

    teamBtn.addEventListener("click", () => {
        teamModal.classList.remove("hidden");

        fetch("/api/teams")
            .then(res => res.json())
            .then(list => {
                console.log("팀 목록:", list); // 확인용

                const teamList = document.getElementById("team-list");
                teamList.innerHTML = "";

                if (!list || list.length === 0) {
                    teamList.innerHTML = "<p>팀이 없습니다</p>";
                    return;
                }

                list.forEach(team => {
                    const div = document.createElement("div");
                    div.className = "team-item";
                    div.textContent = team.name;
                    div.dataset.id = team.id;

                    div.addEventListener("click", () => {
                        localStorage.setItem("selectedTeamId", team.id);
                        alert(team.name + " 팀 선택됨!");
                        teamModal.classList.add("hidden");
                    });

                    teamList.appendChild(div);
                });
            })
            .catch(err => {
                console.error("팀 목록 불러오기 실패:", err);
            });
    });

    // 모달 바깥 클릭 시 닫기
    teamModal.addEventListener("click", (e) => {
        if (e.target.id === "team-modal") {
            teamModal.classList.add("hidden");
        }
    });
});

