<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>회원가입</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        // 아이디 중복체크여부 (중복 Y / 중복아님 : N)
        let userIdCheck = "Y";

        // 이메일 중복체크 인증번호 발송 값
        let emailAuthNumber = "";

        $(document).ready(function () {
            let f = document.getElementById("f"); // form 태그

            // 아이디,이메일 중복체크
            $("#bthSend").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                doSubmit(f);
            });
        });

        // 회원가입 정보의 유효성 체크하기
        function doSubmit(f) {

            if (f.userId.value === "") {
                alert("아이디를 입력하세요.");
                f.userId.focus();
                return;
            }

            if (f.password.value === "") {
                alert("비밀번호를 입력하세요.");
                f.password.focus();
                return;
            }

            if (f.password2.value === "") {
                alert("비밀번호확인을 입력하세요.");
                f.password2.focus();
                return;
            }
            if (f.password.value !== f.password2.value) {
                alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                f.password.focus();
                return;
            }
            if (f.email.value === "") {
                alert("이메일을 입력하세요.");
                f.email.focus();
                return;
            }

            $.ajax({
                url: "/title/getUserIDEmailExists",
                type: "post",   //  전송방식은 Post
                dataType: "json",   // 전송 결과는 JSON으로 받기
                data: $("#f").serialize(),  // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
                success: function (json) {  // 호출이 성공했다면..

                    // 아이디 중복 확인
                    if (json.existsYn === "Y") {
                        alert("이미 사용 중인 아이디입니다.");
                        f.userId.focus();  // 아이디 입력란으로 포커스 이동
                    } else {
                        // 이메일 중복 확인
                        if (json.existsYn === "Y") {
                            alert("이미 가입된 이메일 주소가 존재합니다.");
                            f.email.focus();  // 이메일 입력란으로 포커스 이동
                        } else {
                            alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.");
                            emailAuthNumber = json.authNumber;  // 인증번호 저장

                            // 리디렉션 처리
                            if (json.redirectUrl) {
                                window.location.href = json.redirectUrl; // 지정한 URL로 이동
                            }

                        }
                    }
                }
            })
        }

    </script>

    <style>
        body {
            background-color: #68f6c4 !important;
            margin-top: 50px;
        }

        .card {
            background-color: rgba(255, 255, 255, 0.8) !important; /* 반투명 효과 */
            position: relative;
            z-index: 1; /* 카드가 로고 뒤에 오도록 설정 */
        }

        .logo-container {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            width: 90px; /* 로고 크기 */
            height: 90px;
            border-radius: 50%;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: white; /* 로고 배경 */
            z-index: 2; /* 로고가 카드 위에 오도록 설정 */
            align-items: center;
            justify-content: center;
        }

        .logo-container img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
        }

        .card-body {
            padding-top: 60px; /* 카드 내용이 로고와 겹치지 않도록 패딩 추가 */
        }
    </style>

</head>

<body>

<div class="container">

    <!-- 로고를 카드 외부에 위치 -->
    <div class="logo-container">
        <img src="img/logo1.png" alt="Logo"> <!-- 로고 이미지 경로 설정 -->
    </div>

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
                                </div>
                                <form class="user" id="f">
                                    <div class="form-group">
                                        <input type="text" name="userId" class="form-control form-control-user"
                                               id="exampleInputID" aria-describedby="IDHelp"
                                               placeholder="아이디">
                                    </div>
                                    <div class="form-group">
                                        <input type="email" name="email" class="form-control form-control-user" id="exampleInputEmail"
                                               placeholder="이메일">
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                            <input type="password" name="password" class="form-control form-control-user"
                                                   id="exampleInputPassword" placeholder="비밀번호">
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="password" name="password2" class="form-control form-control-user"
                                                   id="exampleRepeatPassword" placeholder="비밀번호 재확인">
                                        </div>
                                    </div>
                                    <button id="bthSend" type="button" class="btn btn-primary btn-user btn-block">
                                        회원가입
                                    </button>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href="forgot-password.html">비밀번호 찾기</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="login.html">로그인</a>
                                </div>
                            </div>
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

</body>

</html>