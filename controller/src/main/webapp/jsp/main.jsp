<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <%@ include file="parts/meta.jsp" %>
        <title>Main page</title>

    </head>
    <body>
    <%@ include file="parts/header.jsp" %>

    <h3>${sessionScope.message}</h3>
    <c:out value ="${requestScope.reserveMessage}"/>
    <c:if test ="${sessionScope.role == 'admin'}">
        <a href="frontController?command=goToPage&address=newBook.jsp">New Book Creation</a>

    </c:if>
    <c:if test="${sessionScope.role != null}">
        <div>
            <h3>${sessionScope.deleteMessage}</h3>
        </div>
<!-----------MAIN------------------------->
<!-----------SHOW PRODUCTS ------------------------->
    <div>
    <form id="showProducts" method="GET" action="frontController">
    <input type="hidden" name="command" value="showProducts"/>
    <button form="showProducts" type="submit">${showProducts}</button>
    </form>
    </div>
            <div>
                <table>
<!--                    <thead>-->
<!--                    <tr>-->
<!--                        <td></td>-->
<!--                        <td><h4><c:out value="${title}"/></h4></td>-->
<!--                        <td><h4><c:out value="${author}"/></h4></td>-->
<!--                        <td><h4><c:out value="${publishingYear}"/></h4></td>-->
<!--                    </tr>-->
<!--                    </thead>-->

                    <tbody>
                    <c:forEach items="${requestScope.pageable.elements}" var="productRow">
                        <tr>
                            <td>
                                <form  method="GET" action="frontController" >
                                    <input type="hidden" name="command" value="productInfo" />
                                    <input type="hidden" name="id" value="${productRow.id}" />
                                    <button type="submit">${productInfo}</button><br/>
                                </form>
                            </td>
                            <td>${productRow.title}</td>
                            <td>${productRow.author}</td>
                            <td>${productRow.publisher}</td>
                            <td>${productRow.publishingYear}</td>
                            <td>
                               <!-- <form method = "POST" action="frontController">
                                    <input type = "hidden" name ="command" value ="createReserve"/>
                                    <input type = "hidden" name="productId" value="${productRow.id}"/>
                                    <input type="submit" value ="Reserve this Book">
                                    <br/>
                                    ${message}
                                    <br/>
                                </form>-->

                            </td>
                            <td>
                                <form  method="GET" action="frontController" >
                                    <input type="hidden" name="command" value="createReserve" />
                                    <input type="hidden" name="bookId" value="${productRow.id}" />
                                    <button type="submit">Create reserve</button><br/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="margin-left: center">
                    <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                        <c:if test="${i == pageable.pageNumber}">
                            <span>
                                <button style="color:red" form="showProducts" type="submit" name="currentPage" value="${i}">${i}</button>
                            </span>
                        </c:if>
                        <c:if test="${i != pageable.pageNumber}">
                            <span>
                                <button form="showProducts" type="submit" name="currentPage" value="${i}">${i}</button>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </div>

        <!-----------SHOW RESERVES ------------------------->

        <div>
            <form id ="showReserves" method="GET" action ="frontController">
                <input type="hidden" name = "command" value="showReserves" />
                <button form ="showReserves" type ="submit">${showReservedProducts}</button>
            </form>
        </div>
        <div>
            <c:if test="${not empty requestScope.pageableReserves.elements}">

            <table>
             <!--   <thead>
                <tr>
                    <td></td>
                    <td><h4><c:out value="${title}"/></h4></td>
                    <td><h4><c:out value="${author}"/></h4></td>
                    <td><h4><c:out value="${publisher}"/></h4></td>
                    <td><h4><c:out value="${publishingYear}"/></h4></td>
                </tr>
                </thead>-->

                <tbody>
                <c:forEach items="${requestScope.pageableReserves.elements}" var="reserveRow">
                    <tr>
                        <td>
                        </td>
                        <td>${reserveRow.title}</td>
                        <td>${reserveRow.author}</td>
                        <td>${reserveRow.publisher}</td>
                        <td>${reserveRow.publishingYear}</td>
                        <td>
                            <form  method="POST" action="frontController" >
                                <input type="hidden" name="command" value="deleteReserve" />
                                <input type="hidden" name="bookId" value="${reserveRow.id}" />
                                <button type="submit">Delete reserve</button><br/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
                <div>
                    <form id="createOrder" method="POST" action="frontController">
                        <input type="hidden" name="command" value="createOrder"/>
                        <button form ="createOrder" type="submit">${createOrder}</button>
                    </form>
                </div>

            <div style="margin-left: center">
                <c:forEach begin="1" end="${Math.ceil(pageableReserves.totalElements / pageableReserves.limit)}" var="j">
                    <c:if test="${j == pageableReserves.pageNumber}">
                            <span>
                                <button style="color:red" form="showReserves" type="submit" name="currentPageReserve" value="${j}">${j}</button>
                            </span>
                    </c:if>
                    <c:if test="${j != pageableReserves.pageNumber}">
                            <span>
                                <button form="showReserves" type="submit" name="currentPageReserve" value="${j}">${j}</button>
                            </span>
                    </c:if>
                </c:forEach>
            </div>
            </c:if>
            <c:if test="${empty requestScope.pageableReserves.elements}">
                <div>
                    <h3>${reservesMessage}</h3>
                </div>
            </c:if>
        </div>

        <!-----------SHOW ORDER INFO ------------------------->

        <form id ="showOrderInfo" method="GET" action ="frontController">
        <input type = "hidden" name ="command" value="orderInfo"/>
         <button form ="showOrderInfo" type="submit">${goToOrderList}</button>
        </form>

        <div>
            <table>
               <!-- <thead>
                <tr>
                    <td></td>
                    <td><h4><c:out value="${title}"/></h4></td>
                    <td><h4><c:out value="${author}"/></h4></td>
                    <td><h4><c:out value="${publisher}"/></h4></td>
                    <td><h4><c:out value="${publishingYear}"/></h4></td>
                </tr>
                </thead>-->

                <tbody>

                <c:forEach items="${requestScope.booksFromOrder}" var="bookFromOrder">
                    <c:if test="${not empty requestScope.booksFromOrder}">
                        <tr>
                            <td>
                                <!-- <form  method="GET" action="frontController" >
                                     <input type="hidden" name="command" value="productInfo" />
                                     <input type="hidden" name="id" value="${reserveRow.id}" />
                                     <button type="submit" >Product info</button><br/>
                                 </form>-->
                            </td>
                            <td>${bookFromOrder.title}</td>
                            <td>${bookFromOrder.author}</td>
                            <td>${bookFromOrder.publisher}</td>
                            <td>${bookFromOrder.publishingYear}</td>
                               <td>
                                    <form method = "POST" action="frontController">
                                        <input type = "hidden" name ="command" value ="deleteProductFromOrder"/>
                                        <input type = "hidden" name="bookId" value="${bookFromOrder.id}"/>
                                        <input type="submit" value ="Delete from Order">
                                        <br/>
                                        <br/>
                                    </form>

                               </td>
                        </tr>
                    </c:if>

                </c:forEach>
                <c:if test="${empty requestScope.booksFromOrder}">
                    <div>
                        <h3>${orderMessage}</h3>
                    </div>
                </c:if>

                </tbody>
            </table>
            <!--<div style="margin-left: center">
                <c:forEach begin="1" end="${Math.ceil(pageableReserves.totalElements / pageableReserves.limit)}" var="i">
                    <c:if test="${i == pageableReserves.pageNumber}">
                            <span>
                                <button style="color:red" form="showReserves" type="submit" name="currentPageReserve" value="${i}">${i}</button>
                            </span>
                    </c:if>
                    <c:if test="${i != pageableReserves.pageNumber}">
                            <span>
                                <button form="showReserves" type="submit" name="currentPageReserve" value="${i}">${i}</button>
                            </span>
                    </c:if>
                </c:forEach>
            </div>-->
        </div>
        </c:if>

    <!-----------------ADMIN SHOW USERS -------------------->


    <!-----------------ADMIN SHOW ORDERS -------------------->



    <!-----------------ADMIN SHOW RESERVES -------------------->

    </body>
</html>