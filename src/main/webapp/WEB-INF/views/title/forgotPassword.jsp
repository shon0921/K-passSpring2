<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Forgot Password</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/css/sb-admin-2.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 비밀번호 찾기
            $("#bthSearchPassword").on("click",function () {
                let f = document.getElementById("f"); // form 태그

                if (f.userId.value === "") {
                    alert("아이디를 입력하세요.");
                    f.userId.focus();
                    return;
                }


                if (f.email.value === "") {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return;
                }

                f.method = "post"; // 비밀번호 찾기 정보 전송 방식
                f.action = "/title/forgotPasswordProc2" // 비밀번호 찾기 URL

                f.submit(); // 비밀번호 찾기 정보 전송하기
            })
        })
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
                            <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-2">비밀번호 찾기</h1>
                                        <p class="mb-4">We get it, stuff happens. Just enter your email address below
                                            and we'll send you a link to reset your password!</p>
                                    </div>
                                    <form id="f" class="user">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user"
                                                   name="userId" id="userId" aria-describedby="emailHelp"
                                                   placeholder="아이디 입력">
                                            <input type="email" class="form-control form-control-user"
                                                name="email" id="email" aria-describedby="emailHelp"
                                                placeholder="이메일 입력">
                                        </div>
                                        <a id="bthSearchPassword" class="btn btn-primary btn-user btn-block">
                                            이메일 전송
                                        </a>
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="register.jsp">회원가입</a>
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