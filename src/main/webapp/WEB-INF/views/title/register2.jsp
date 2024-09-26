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
  <link href="css/sb-admin-2.min.css" rel="stylesheet">
  <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
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
            <form class="user">
              <!-- Verification Code Input -->
              <div class="form-group">
                <input type="text" class="form-control form-control-user" id="exampleInputCode"
                       placeholder="Enter Verification Code">
              </div>
              <!-- Submit Code -->
              <a href="verification-success.html" class="btn btn-primary btn-user btn-block">
                Verify Account
              </a>
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