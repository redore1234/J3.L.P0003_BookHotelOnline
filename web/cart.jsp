<%-- 
    Document   : cart
    Created on : Dec 10, 2020, 7:30:08 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/customize.css">
    </head>
    <body>

        <!-- Nav bar -->
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:set var="role" value="${sessionScope.ACCOUNT_ROLE}"/>

        <nav class="navbar navbar-dark navbar-expand-sm bg-primary">
            <c:if test="${not empty account}">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="Home">Home</a>
                    </li>
                    <!-- Reload Page -->
                    <li class="nav-item">
                        <c:url var="urlReload" value="Home">
                            <c:param name="btnAction" value="Reload"/>
                        </c:url>
                        <a class="nav-link active" href="${urlReload}">Reload</a>
                    </li>
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
                    <!-- Reload Page -->
                    <li class="nav-item">
                        <c:url var="urlReload" value="Home">
                            <c:param name="btnAction" value="Reload"/>
                        </c:url>
                        <a class="nav-link active" href="${urlReload}">Reload</a>
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

        <div class="container">
            
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
