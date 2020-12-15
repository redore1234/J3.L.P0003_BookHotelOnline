<%-- 
    Document   : login
    Created on : Dec 14, 2020, 6:28:23 PM
    Author     : phamt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
        <!-- reCAPTCHA script with English language -->
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
    </head>
    <body>
        <div class="container-fluid h-100 bg-custom">
            <div class="row h-100 justify-content-center align-items-center">	
                <div class="card col-10 col-md-8 col-lg-6 p-5">
                    <h1 class="text-center">Login</h1>
                    <form action="Login" method="POST" class="form-group">
                        <label>Username</label> 
                        <input type="text" name="txtUsername" value="${param.txtUsername}" class="form-control" required/><br/>
                        <label>Password</label>
                        <input type="password" name="txtPassword" value="" class="form-control" required/>
                        <br/>
                        <div class="text-center">
                            <!-- reCAPTCHA -->
                            <div class="g-recaptcha"
                                 data-sitekey="6Lcvtv0ZAAAAAGWiju75FrjEg0vyC9dSEoecPvpA"></div>
                            <div class="h4 text-danger text-bold">${requestScope.CAPTCHA_ERROR}</div>

                            <!-- Login button -->
                            <input type="submit" value="Login" name="btnAction" class="btn btn-primary px-5 my-3"/>
                        </div>	

                    </form>
                    <div class="text-center">
                        <form id="LoginGoogle" method="POST" action="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/J3.L.P0003_BookHotelOnline/LoginGoogle&response_type=code&client_id=1017604108080-4vqusr3v1c7ecjd7at2095okof7q9vkh.apps.googleusercontent.com&approval_prompt=force">
                            <div class="text-center">
                                <a class="btn btn-outline-secondary" role="button" style="text-transform:none" onclick="document.getElementById('LoginGoogle').submit();">
                                    <img width="20" style="margin-bottom: 3px; margin-right: 5px" alt="Google sign-in" src="assets/images/google-icon.png"/>
                                    Google Login
                                </a>
                            </div>
                        </form>

                        <br/>
                        Don't have account? <a href="registerpage">Sign up</a>	
                        <br/>
                        Search for Hotel only? <a href="Home">Home page</a>	
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
