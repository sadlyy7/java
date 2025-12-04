document.addEventListener("DOMContentLoaded", () => {
    console.log("Calendar JS Loaded!");

    // 🔥 로그인한 학생 ID 가져오기
    const studentId = document.getElementById("studentId").value;
    console.log("로그인 학생 ID:", studentId);

    const year = 2025;
    const month = 12;

    const todoList = document.getElementById("todo-list");
    const addBtn = document.getElementById("add-todo");
    const todoInput = document.getElementById("todo-input");

    renderCalendar(year, month);

    // 🔥 오늘 날짜 셀 자동 선택
    const todayCell = document.querySelector(".calendar-day.today");
    if (todayCell) {
        todayCell.classList.add("selected");
        loadTodosByDate(todayCell.dataset.date);
    }

    // 🔥 TODO 추가하기
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
                studentId: studentId,
                title: "TODO",
                content: text,
                type: "TODO",
                noteDate: date
            }),
        })
            .then(res => res.text())
            .then(() => {
                alert("할 일 저장 완료!");
                loadTodosByDate(date);  // 🔥 저장 후 즉시 새로고침
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

    // 제목
    const title = document.createElement("h3");
    title.textContent = `${year}년 ${month}월`;
    title.style.textAlign = "center";
    title.style.color = "#357dad";
    calendar.appendChild(title);

    // 요일 헤더
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

            loadTodosByDate(this.dataset.date);
        });

        grid.appendChild(cell);
    }

    calendar.appendChild(grid);
}


// ============================
// 🔥 특정 날짜의 TODO 로드
// ============================
function loadTodosByDate(date) {
    const studentId = document.getElementById("studentId").value;

    fetch(`/note/todo/list/byDate?studentId=${studentId}&date=${date}`)
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("todo-list");
            list.innerHTML = "";

            if (!data || data.length === 0) {
                list.innerHTML = "<li>할 일이 없습니다</li>";
                return;
            }

            data.forEach(todo => {
                const li = document.createElement("li");
                li.textContent = todo.content;
                list.appendChild(li);
            });
        })
        .catch(err => console.error("TODO 로드 오류:", err));
}

// ================================
// 🔥 팀 선택창 (버튼 → 모달 열림)
// ================================
document.addEventListener("DOMContentLoaded", () => {
    const teamBtn = document.getElementById("team-select-btn");
    const teamModal = document.getElementById("team-modal");
    const teamListBox = document.getElementById("team-list");

    if (!teamBtn || !teamModal) {
        console.error("팀 선택 버튼 또는 모달을 찾을 수 없습니다.");
        return;
    }

    // 버튼 클릭 → 모달 열기
    teamBtn.addEventListener("click", () => {
        teamModal.classList.remove("hidden");

        fetch("/api/teams")
            .then(res => res.json())
            .then(list => {
                teamListBox.innerHTML = "";

                if (!list || list.length === 0) {
                    teamListBox.innerHTML = "<p>팀이 없습니다</p>";
                    return;
                }

                list.forEach(team => {
                    const div = document.createElement("div");
                    div.className = "team-item";
                    div.textContent = team.name;

                    div.addEventListener("click", () => {
                        localStorage.setItem("selectedTeamId", team.id);
                        alert(`${team.name} 팀 선택됨!`);
                        teamModal.classList.add("hidden");
                    });

                    teamListBox.appendChild(div);
                });
            })
            .catch(err => console.error("팀 목록 불러오기 실패:", err));
    });

    // 모달 바깥 클릭 → 닫기
    teamModal.addEventListener("click", (e) => {
        if (e.target.id === "team-modal") {
            teamModal.classList.add("hidden");
        }
    });
});
