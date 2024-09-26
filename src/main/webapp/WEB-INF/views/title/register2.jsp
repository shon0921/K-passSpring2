<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Email Verification</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link
          href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="/css/sb-admin-2.min.css" rel="stylesheet">
  <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
  <script type="text/javascript">

    let emailAuthNumber = sessionStorage.getItem('authNumber');  // 저장된 인증번호 가져오기

    $(document).ready(function () {

      //회원가입
      $("#bthSend").on("click",function (){ // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
        doSubmit();
      })


    });

    function doSubmit() {
      const f = document.getElementById("f");
      const enteredAuthNumber = f.authNumber.value;

      if (enteredAuthNumber === "") {
        alert("이메일 인증번호를 입력하세요.");
        f.authNumber.focus();
        return;
      }

      if (enteredAuthNumber !== emailAuthNumber) {
        alert("이메일 인증번호가 일치하지 않습니다.");
        f.authNumber.focus();
        return;

      }
      $.ajax({
        url: "/title/insertUser",
        type: "post",   // 전송방식은 Post
        dataType: "json",   // 전송 결과는 json으로 받기
        data: {
        userId: sessionStorage.getItem('userId'),
              email: sessionStorage.getItem('email'),
              password: sessionStorage.getItem('password')},// form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
        success: function (json) {  // /notice/noticeUpdate 호출이 성공했다면..

          if (json.result === 1) {    // 회원가입 성공
            alert(json.msg);    // 메시지 띄우기
            location.href = "login";   // 로그인 페이지 이동

          } else {    // 회원가입 실패
            alert(json.msg);
          }

        }
      })
    }

  </script>
</head>

<body class="bg-gradient-primary">

<div class="container">

  <div class="card o-hidden border-0 shadow-lg my-5">
    <div class="card-body p-0">
      <!-- Nested Row within Card Body -->
      <div class="row">
        <div class="col-lg-5 d-none d-lg-block bg-verify-image"></div>
        <div class="col-lg-7">
          <div class="p-5">
            <div class="text-center">
              <h1 class="h4 text-gray-900 mb-4">Verify Your Email Address</h1>
              <p class="mb-4">Enter the verification code sent to your email address.</p>
            </div>
            <form id="f" class="user">
              <!-- Verification Code Input -->
              <div class="form-group">
                <input type="text" name="authNumber" class="form-control form-control-user" id="authNumber"
                        placeholder="Enter Verification Code">
              </div>
              <!-- Submit Code -->
              <button id="bthSend" type="button" class="btn btn-primary btn-user btn-block">이메일 인증</button>
              <hr>
              <!-- Resend Verification Code -->
              <a href="#" id="resendButton" class="btn btn-secondary btn-user btn-block" onclick="resendCode()">
                Resend Verification Code
              </a>
              <!-- Success Message -->
              <p id="resendMessage" class="text-success text-center mt-3" style="display:none;">Verification code has been resent!</p>
            </form>
            <hr>
            <div class="text-center">
              <a class="small" href="login2.html">Already verified? Login!</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<script>
  // Function to show resend confirmation message
  function resendCode() {
    // Simulate resending the code (you would send a request to your server here)
    // Show success message
    document.getElementById('resendMessage').style.display = 'block';

    // Optionally, disable the resend button to prevent multiple clicks
    document.getElementById('resendButton').disabled = true;

    // After a few seconds, re-enable the resend button if needed
    setTimeout(() => {
      document.getElementById('resendButton').disabled = false;
    }, 5000);  // Re-enable after 5 seconds
  }
</script>

</body>

</html>