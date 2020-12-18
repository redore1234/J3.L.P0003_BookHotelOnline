<%-- 
    Document   : trackorder
    Created on : Dec 16, 2020, 11:32:50 PM
    Author     : phamt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Track Order</title>
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
                            <c:param name="btnAction" value="View History"/>
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
            <c:set var="orderHistory" value="${requestScope.ORDER_HISTORY}"/>
            <!-- Display order history -->
            <div class="text-center" style="font-weight: bold">

            </div>

            <c:if test="${not empty orderHistory}">
                <table class="table table-hover">
                    <h3 class="text-center my-5" style="font-weight: bold"></h3>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Order ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Total Price</th>
                            <th scope="col">Discount Price</th>
                            <th scope="col">Date</th>
                            <th scope="col">View</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="orderList" items="${orderHistory}" varStatus="counter">
                            <tr>
                                <th scope="row">${counter.count}</th>
                                <td>${orderList.orderId}</td>
                                <td>${orderList.name}</td>
                                <td>${orderList.totalPrice}</td>
                                <td>${orderList.discountPrice}</td>
                                <td>${orderList.bookDate}</td>
                                <td>
                                    <form action="TrackOrder">
                                        <c:url var="urlViewDetail" value="TrackOrder">
                                            <c:param name="btnAction" value="View Detail"/>
                                            <c:param name="orderId" value="${orderList.orderId}"/>
                                        </c:url>
                                        <a href="${urlViewDetail}" class="btn btn-outline-danger">View Detail</a>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>


            <c:set var="orderInfo" value="${requestScope.ORDER_INFO}" />
            <c:set var="orderDetailInfo" value="${requestScope.ORDER_DETAIL_INFO}" />
            <!-- TRACKING ORDER DETAIL AREA -->


            <c:choose>
                <c:when test="${not empty orderInfo}">
                    <div class="text-center" style="font-weight: bold">
                        <h3 class="my-3">TRACK ORDER DETAIL</h3>
                    </div>
                    <div class="card">
                        <h5 class="card-header text-center" style="font-weight: bold">Order ID: ${orderInfo.orderId}</h5>
                        <div class="card-body">
                            <div>
                                <span style="font-weight: bold">Name: </span> ${orderInfo.name}
                            </div>
                            <div>
                                <span style="font-weight: bold">Address: </span> ${orderInfo.address}
                            </div>
                            <div>
                                <span style="font-weight: bold">Phone: </span> ${orderInfo.phone}
                            </div>
                            <div>
                                <span style="font-weight: bold">Booking Date: </span> ${orderInfo.bookDate}
                            </div>
                            <div>
                                <span style="font-weight: bold">Discount Price: </span> ${orderInfo.discountPrice} VND
                            </div>
                        </div>
                    </div>

                    <table class="table table-hover">
                        <caption class="text-right" style="font-weight: bold">Total: ${orderInfo.totalPrice} VND</caption>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Room Type</th>
                                <th scope="col">Price</th>
                                <th scope="col">Check in Date</th>
                                <th scope="col">Check out Date</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="orderDetailInfo" items="${orderDetailInfo}" varStatus="counter">
                                <tr>
                                    <th scope="row">${counter.count}</th>
                                    <td>${orderDetailInfo.value}</td>
                                    <td>${orderDetailInfo.key.totalPrice} VND</td>
                                    <td>${orderDetailInfo.key.checkinDate}</td>
                                    <td>${orderDetailInfo.key.checkoutDate}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
            </c:choose>
        </div>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
