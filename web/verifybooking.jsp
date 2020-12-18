<%-- 
    Document   : verifybooking
    Created on : Dec 17, 2020, 8:39:51 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Verify</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>
        <!-- Nav bar -->
    <c:set var="account" value="${sessionScope.ACCOUNT}"/>

    <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
        <c:if test="${not empty account}">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="Home">Home</a>
                </li>
                <c:if test="${account.roleId ne 'admin'}">  
                    <!-- View Cart form -->
                    <c:url var="urlViewCart" value="ViewCart">
                        <c:param name="btnAction" value="View Cart"/>
                    </c:url>
                    <li class="nav-item">
                        <a href="${urlViewCart}" class="nav-link active">View Cart</a>
                    </li>

                    <!-- Load Order Form -->
                    <c:url var="urlViewHistory" value="LoadOrder">
                        <c:param name="btnAction" value="Load Order"/>
                    </c:url>
                    <li class="nav-item">
                        <a href="${urlViewHistory}" class="nav-link active">Book History</a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav ml-auto text-center">
                <li class="nav-item">
                    <div class="welcome-user"> Welcome ${account.fullName}</div>
                </li>
                <!-- Logout form -->
                <form action="Logout">
                    <li class="nav-item">
                        <input type="submit" value="Logout" name="btnAction"  class="btn btn-danger btn-sm my-2 my-sm-0 mx-3"/>
                    </li>
                </form>
            </ul>
        </c:if>

        <c:if test="${empty account}">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="Home">Home</a>
                </li>
            </ul>

            <ul class="navbar-nav ml-auto text-center">
                <!-- Login form -->
                <form action="Login" method="POST">
                    <li class="nav-item"> 
                        <input type="submit" value="Login" name="btnAction"  class="btn btn-success btn-sm my-2 my-sm-0 mx-3"/>
                    </li>
                </form>
            </ul>
        </c:if>
    </nav>

    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="col-8 col-md-6 col-lg-4 text-center">
                <h2>Booking Verify</h2>
                <!-- Confirm booking form -->
                <form action="VerifyBooking" method="POST" class="form-group">
                    
                    <label>Enter the 4 digits code sent to your email (${account.username})!</label>
                    <input type="text" name="txtActivationCode" value="" class="form-control"/><br/>
                    <input type="submit" value="Verify Booking" name="btnAction" class="btn btn-primary"/> <br/>
                    <c:set var="error" value="${requestScope.ACTIVATE_ERROR}"/>
                    <c:if test="${not empty error}">
                        <p class="text-danger">${error} </p>
                    </c:if>
                </form>

                <!-- Resend code -->
                <a href="SendVerifyCode?btnAction=Send Activation&txtCustomerName=${sessionScope.NAME_ACCOUNT}&txtAddress=${sessionScope.ADDRESS}&txtPhone=${sessionScope.PHONE}">Resend activation code</a> <br/>
                <a href="cartpage">Back to cart</a>
            </div>
        </div>
    </div>
    <script src="assets/js/bootstrap.min.js"></script>
</body>
</html>
