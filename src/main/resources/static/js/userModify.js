function confirmPassword() {
  event.preventDefault();
  const data = {
    "password": $("#confirmPassword").val()
  }
  $.ajax({
    type: "post",
    url: "/login/confirmPassword",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: data => {
      renderUserModifyView(data);
    },
    error: data => {
      $("#confirmPasswordError").css("display", "block");
    }
  })
}

function renderUserModifyView(user) {
  const el = ".content-container"
  $(el).empty();
  const view = `<div class="content-container">
    <h1>회원정보 수정</h1>
    <table><tbody>
    <tr><th scope="row">아이디(이메일)</th>
    <td><div><input type="text" id="email" value="${user.email}">
    <button type="button" onclick="change('email')">이메일 변경</button>
    <span class="error-message" id="emailError">이메일 변경 실패</span>
    <span class="success-message" id="emailSuccess">이메일 변경 성공</span>
    </div></td></tr>
    <tr><th scope="row">이름</th>
    <td><div><input type="text" id="username" value="${user.username}">
    <button type="button" onclick="change('username')">이름 변경</button>
    <span class="error-message" id="usernameError">이름 변경 실패</span>
    <span class="success-message" id="usernameSuccess">이름 변경 성공</span></div></td></tr>
    <tr><th scope="row">휴대폰 번호</th><td><div>
    <input type="text" id="phoneNumber" value="${user.phoneNumber}">
    <button type="button" onclick="change('phoneNumber')">휴대폰 번호 변경</button>
    <span class="error-message" id="phoneNumberError">휴대폰 번호 변경 실패</span>
    <span class="success-message" id="phoneNumberSuccess">휴대폰 번호 변경 성공</span></div></td></tr>
    <tr><th scope="row">비밀번호변경</th>
    <td><div><table><tbody>
    <tr><th>현재 비밀번호</th>
    <td><input type="password" id="originPassword"></td></tr><tr>
    <th>새 비밀번호</th>
    <td><input type="password" id="newPassword"></td></tr>
    <tr><th>비밀번호 다시 입력</th>
    <td><input type="password" id="newPasswordConfirm"></td></tr><tr><td></td>
    <td><button type="button" onclick="changePassword()">비밀번호 변경</button>
    <span class="error-message" id="passwordError">비밀번호 변경 실패</span>
    <span class="success-message" id="passwordSuccess">비밀번호 변경 성공</span></td></tr></tbody></table>
    </div></td></tr></tbody></table></div>
    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#secessionModal">탈퇴</button>
    </div>`;
  $(el).append(view);
}

function change(att) {
  event.preventDefault();
  const data = {};
  data[att] = $("#" + att).val();
  console.log(data);
  $.ajax({
    type: "put",
    url: "/user/" + att,
    data: JSON.stringify(data),
    contentType: "application/json",
    success: data => {
      $("#" + att).val($("#" + att).val());
      $("#" + att + "Error").css("display", "none");
      $("#" + att + "Success").css("display", "inline-block");
    },
    error: data => {
      $("#" + att + "Error").css("display", "inline-block");
      $("#" + att + "Success").css("display", "none");
    }
  })
}

function changePassword() {
  event.preventDefault();
  const data = {
    "originPassword": $("#originPassword").val(),
    "newPassword": $("#newPassword").val(),
    "newPasswordConfirm": $("#newPasswordConfirm").val()
  }
  $.ajax({
    type: "put",
    url: "/user/password",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: data => {
      $("#passwordError").css("display", "none");
      $("#passwordSuccess").css("display", "inline-block");
    },
    error: data => {
      $("#passwordError").css("display", "inline-block");
      $("#passwordSuccess").css("display", "none");
    }
  })
  $("#originPassword").val("");
  $("#newPassword").val("");
  $("#newPasswordConfirm").val("");
}

function secession() {
  $.ajax({
    type: "delete",
    url: "/user/me",
    success: data => {
      location.href = "/";
    },
    error: data => {

    }
  })
}
