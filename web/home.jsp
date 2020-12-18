<%-- 
    Document   : home
    Created on : Dec 14, 2020, 6:38:05 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>		
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

        <div class="container">
            <!-- Search form -->
            <div class="my-4">
                <div class="my-4">
                    <form action="SearchDate" class="form-group form-inline">
                        <input type="date" name="dtCheckin" value="${param.dtCheckin}" class="form-control ml-2"/> 
                        <input type="date" name="dtCheckout" value="${param.dtCheckout}" class="form-control ml-2"/> 
                        <div class="text-right">
                            <input type="submit" value="Search Date" class="btn btn-success my-3 ml-2" /> 
                        </div>
                        <div class="ml-2 text-danger text-bold">
                            <c:if test="${not empty requestScope.SEARCH_ERROR}">
                                ${requestScope.SEARCH_ERROR}
                            </c:if>
                        </div>
                    </form>

                    <!-- Display result -->
                    <c:set var="roomList" value="${requestScope.LIST_ROOM}"/>
                    <c:choose>
                        <c:when test="${not empty roomList}">
                            <h3 class="text-center">ROOMS</h3>
                            <!-- Display rooms -->
                            <div class="row justify-content-center">
                                <c:forEach var="room" items="${roomList}">
                                    <div class="col-12 col-md-6 col-lg-4 my-3">
                                        <div class="card h-100">
                                            <h5 class="card-header">
                                                <span  style="font-weight: bold">Type: </span>
                                                ${room.typeId}
                                                <input type="hidden" name="roomType" value="${room.typeId}" />
                                            </h5>
                                            <img src="LoadImage?file=${room.image}" class="card-img-top"/>
                                            <div class="card-body">
                                                <span  style="font-weight: bold">Price: </span>
                                                ${room.price} VND
                                                <input type="hidden" name="roomPrice" value="${room.price}" />
                                            </div>
                                            <div class="card-footer">
                                                <c:if test="${not empty requestScope.UNAVAILABLE_ROOM}">
                                                    <div class="h3 text-danger text-bold"> ${requestScope.UNAVAILABLE_ROOM} </div>
                                                </c:if>
                                                <c:if test="${account.roleId eq 'user'}">
                                                    <!-- Add to Cart form-->
                                                    <c:url var="urlBook" value="BookRoom">
                                                        <c:param name="btnAction" value="Book"/>
                                                        <c:param name="roomId" value="${room.roomId}"/>
                                                        <c:param name="typeId" value="${room.typeId}"/>
                                                        <c:param name="roomPrice" value="${room.price}"/>
                                                    </c:url>
                                                    <a class="nav-link badge badge-primary" href="${urlBook}">Book</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="container h-100">
                                <div class="row h-100  justify-content-center">
                                    <div class="col-6 text-center">
                                        <h2 class="text-danger">No room to search</h2>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
