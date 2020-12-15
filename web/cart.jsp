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
                    <c:if test="${role eq 'user'}">  
                        <!-- View Cart form -->
                        <c:url var="urlViewCart" value="ViewCart">
                            <c:param name="btnAction" value="View Cart"/>
                        </c:url>
                        <li class="nav-item">
                            <a href="${urlViewCart}" class="nav-link active">View Cart</a>
                        </li>

                        <!-- Load Order Form -->
                        <c:url var="urlViewHistory" value="ViewHistory">
                            <c:param name="btnAction" value="View History"/>
                        </c:url>
                        <li class="nav-item">
                            <a href="${urlViewHistory}" class="nav-link active">Purchase History</a>
                        </li>
                    </c:if>
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
            <c:set var="cart" value="${sessionScope.CART}"/>
            <!-- CART FORM -->
            <c:choose>
                <c:when test="${not empty cart}">
                    <h4 class="card-header text-center" style="font-weight: bold">YOUR CART</h4>
                    <c:set var="compartment" value="${cart.compartment}"/>
                    <c:if test="${not empty compartment}">
                        <!-- Display cart -->
                        <table class="table table-hover">
                            <thead class="thead-light">
                                <tr>
                                    <th>#</th>
                                    <th>Room ID</th>
                                    <th>Type ID</th>
                                    <th>Price</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="room" items="${compartment}" varStatus="counter">
                                    <c:set var="roomItem" value="${room.value}"/>
                                <form action="CheckOut">
                                    <input type="hidden" name="roomId" value="${roomItem.roomId}" />
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${roomItem.roomId}</td>
                                        <td>${roomItem.typeId}</td>
                                        <td>${roomItem.price}</td>

                                        <c:choose>
                                            <c:when test="${role eq 'user'}">
                                                <!--<td>
                                                    <input type="submit" value="Update quantity" class="btn btn-warning" name="btnAction"/>
                                                </td>-->
                                                <td>
                                                    <a href="DeleteRoomFromCart?btnAction=Delete Cart&txtRoomId=${room.value.roomId}" class="btn btn-outline-danger" 
                                                       onclick="return confirm('Do you want to delete this?');">
                                                        Delete
                                                    </a>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <c:redirect url="Home"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </form>
                            </c:forEach>
                            <c:set var="totalPrice" value="${sessionScope.TOTAL_PRICE}"/>
                            <tr class="alert-primary" style="font-weight: bold">
                                <td scope="row" colspan="4">Total Price:</td>
                                <td scope="row">${totalPrice} VND</td>
                            </tr>
                            </tbody>
                        </table>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <div class="container h-100">
                        <div class="row h-100  justify-content-center">
                            <div class="col-6 text-center">
                                <h2 class="text-danger">Your cart is empty</h2>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
