document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const username = document.querySelector("#username");
  const password = document.querySelector("#password");

  form.addEventListener("submit", (event) => {
    if (username.value.trim() === "" || password.value.trim() === "") {
      event.preventDefault(); // 서버로 전송 막기
      alert("아이디와 비밀번호를 모두 입력해주세요!");
    }
  });
});
