<%-- 
    Document   : register
    Created on : Sep 15, 2020, 1:53:46 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <div class="container-fluid h-100">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="card col-10 col-md-8 col-lg-6 p-5">
                    <div class="text-center pb-3">
                        <h1>Register Page</h1>
                    </div>
                    
                    <form action="RegisterNewAccount" method="POST">
                        <c:set var="errors" value="${requestScope.ERRORS}"/>
                        
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" class="form-control" placeholder="" name="txtUsername" 
                                   value="${param.txtUsername}" minlength="4" maxlength="50" required/>
                            <small  class="text-muted">Must be 4-50 chars</small>
                            <c:if test="${not empty errors.usernameIsExisted}">
                                <p class="text-danger"> ${errors.usernameIsExisted} </p>
                            </c:if>
                        </div>
                        
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" placeholder="" name="txtName" 
                                   onkeypress='return ((event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32))'
                                   value="${param.txtName}"  minlength="4" maxlength="50" required />
                            <small  class="text-muted">Must be 4-50 chars</small>
                        </div>
                            
                        <div class="form-group">
                            <label>Address</label>
                            <input type="text" class="form-control" placeholder="" name="txtAddress" 
                                   value="${param.txtAddress}" minlength="8" maxlength="200"  required />
                            <small  class="text-muted">Must be 8-200 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="tel" class="form-control" placeholder="" name="txtPhone" 
                                   onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57"
                                   value="${param.txtPhone}" pattern="[0-9]{10}" maxlength="10" required />
                            <small  class="text-muted">Must be 10 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="txtPassword" 
                                   value="" minlength="8" maxlength="50" required/>
                            <small  class="text-muted">Must be 8-50 chars</small>
                        </div>
                        
                        <div class="form-group">
                            <label>Confirm Password</label>
                            <input type="password" class="form-control" name="txtConfirmPassword" 
                                   value="" required/>
                            <small  class="text-muted">Match to Password</small>
                            <c:if test="${not empty errors.passwordIsNotMatch}">
                                <p class="text-danger">  ${errors.passwordIsNotMatch} </p>
                            </c:if>
                        </div>
                        
                        <div class="form-group text-right">
                            <input type="submit" class="btn btn-primary" value="Register" name="btnAction" />
                            <input type="reset" class="btn btn-primary" value="Reset" />
                        </div>
                    </form>
                        
                    <div class="text-center">
                        Want to login again?<a href="login"> Try again</a> <br/>
                        Back to the <a href="Home">Home page?</a>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
