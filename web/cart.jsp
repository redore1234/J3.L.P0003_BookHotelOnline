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
                    <c:set var="compartment" value="${cart.compartment}"/>
                    <c:if test="${not empty compartment}">
                        <!-- Display cart -->
                        <table class="table table-hover my-5">
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
                                <form action="DeleteRoomFromCart">
                                    <input type="hidden" name="roomId" value="${room.value.roomId}" />
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${room.value.roomId}</td>
                                        <td>${room.value.typeId}</td>
                                        <td>${room.value.price}</td>

                                        <c:choose>
                                            <c:when test="${account.roleId ne 'admin'}">
                                                <td>
                                                    <a href="DeleteRoomFromCart?btnAction=Delete Cart&txtRoomId=${room.value.roomId}" class="btn btn-outline-danger" 
                                                       onclick="return confirm('Do you want to delete ${room.value.typeId}?');">
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
                            <tr class="alert-primary" style="font-weight: bold">
                                <td scope="row" colspan="2">Code:</td>
                                <td scope="row">${cart.discountID}</td>
                                <td scope="row" colspan="1">Discount:</td>
                                <td scope="row">${cart.discountPer} %</td>
                            </tr>
                            <tr class="alert-primary" style="font-weight: bold">
                                <td scope="row" colspan="4">Discount Price:</td>
                                <td scope="row">${cart.priceDiscount} VND</td>
                            </tr>
                            <tr class="alert-primary" style="font-weight: bold">
                                <td scope="row" colspan="4">Total Pay:</td>
                                <td scope="row">${cart.priceAfterDiscount} VND</td>
                            </tr>
                            </tbody>
                        </table>

                        <!-- CHECK OUT FORM -->
                        <div class="my-5">
                            <div class="card">
                                <h4 class="card-header text-center" style="font-weight: bold">CHECKOUT</h4>
                                <div class="m-3">
                                    <div class="form-group">
                                        <div class="text-danger text-center" style="font-weight: bold">
                                            Please check your Cart before click Check out to ensure everything is correct!
                                        </div>
                                        <form action="ApplyCode">
                                            <input type="text" name="txtDiscount" value="${param.txtDiscount}" onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" 
                                                   title="Please input number" class="form-control" placeholder="Enter discount code"/>
                                            <button type="submit" name="btAction" value="Apply Code" class="btn btn-primary mt-2">Add discount</button>
                                        </form>
                                        <c:if test="${not empty requestScope.USED_DISCOUNT}">
                                            <p class="text-danger text-bold">${requestScope.USED_DISCOUNT}</p>
                                        </c:if>
                                    </div>
                                    <div class="form-group ">
                                        <form action="ChooseDate" class="form-inline">
                                            <label>Check in</label>
                                            <input type="date" name="dtCheckin" value="${param.dtCheckin}" class="form-control ml-2" required/> 
                                            <input type="hidden" name="dtCheckin" value="${param.dtCheckin}" />
                                            <label class="ml-2">Check out</label>
                                            <input type="date" name="dtCheckout" value="${param.dtCheckout}" class="form-control ml-2" required/> 
                                            <input type="hidden" name="dtCheckout" value="${param.dtCheckout}" />
                                            <input type="submit" value="Choose Date" name="btnAction" class="btn btn-primary ml-2"/>
                                        </form>
                                    </div>

                                    <!-- CHECK OUT ERROR -->
                                    <c:set var="checkOutError" value="${requestScope.CHECKOUT_ERROR}"/>
                                    <c:if test="${not empty checkOutError.checkInCheckOutIsEmpty}">
                                        <p class="text-danger">  ${checkOutError.checkInCheckOutIsEmpty} </p>
                                    </c:if>
                                    <c:if test="${not empty checkOutError.checkInAfterCheckOut}">
                                        <p class="text-danger">  ${checkOutError.checkInAfterCheckOut} </p>
                                    </c:if>
                                    <c:if test="${not empty checkOutError.checkInCheckOutBeforeCurDate}">
                                        <p class="text-danger">  ${checkOutError.checkInCheckOutBeforeCurDate} </p>
                                    </c:if>
                                    <c:if test="${not empty checkOutError.roomIdBooked}">
                                        <p class="text-danger">  ${checkOutError.roomIdBooked} </p>
                                    </c:if>

                                    <form action="SendVerifyCode">
                                        <div class="form-group">
                                            <label>Name</label>
                                            <c:if test="${not empty account}"> 
                                                <input type="text" name="txtCustomerName" value="${account.fullName}" 
                                                       onkeypress='return ((event.charCode >= 65 && event.charCode <= 90) || (event.charCode >= 97 && event.charCode <= 122) || (event.charCode == 32))'
                                                       maxlength="50" class="form-control" required/>
                                            </c:if>
                                        </div>
                                        <div class="form-group">
                                            <label>Address</label>
                                            <input type="text" name="txtAddress" value="${param.txtAddress}" maxlength="200" class="form-control" required/>
                                        </div>

                                        <div class="form-group">
                                            <label>Phone</label>
                                            <input type="tel" name="txtPhone" pattern="[0-9]{10}" maxlength="10" value="${param.txtPhone}"
                                                   onkeypress=" return (event.charCode == 8 || event.charCode == 0) ? null : event.charCode >= 48 && event.charCode <= 57" 
                                                   class="form-control" required>
                                        </div>

                                        <c:choose>
                                            <c:when test="${account.roleId ne 'admin'}">
                                                <div class="text-right">
                                                    <input type="submit" value="Check out" name="btnAction"  class="btn btn-primary" />
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:redirect url="Home"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </div>
                            </div>
                        </div>
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
