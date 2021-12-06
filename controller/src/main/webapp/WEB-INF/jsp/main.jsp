<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>${mainPage}</title>
</head>
<body style="padding-top : 0px ; text-align : start">
<%@ include file="parts/header.jsp" %>
<br/>
<!----------   ADMIN - BOOK CREATION  ---------->
<div>
    <c:if test="${sessionScope.role == 'admin'}">
        <div>
            <form action="frontController" id="goToCreateBook" method="GET">
                <input name="command" type="hidden" value="go_To_Page"/>
                <input name="address" type="hidden" value="newBook.jsp"/>
                <button class="btn btn-primary" form="goToCreateBook" type="submit">${createBook}</button>
            </form>
        </div>
    </c:if>

    <form id="sessionMessageForm" style="text-align : center">
        <a style="font-weight: bold ; font-size: 200%">
            ${sessionScope.message}
        </a>
    </form>

    <!----------   MAIN  ---------->
    <!----------   SHOW PRODUCTS  ---------->
    <c:if test="${sessionScope.role != null}">
        <div>
            <form action="frontController" id="showProducts" method="GET">
                <input name="command" type="hidden" value="show_Products"/>
                <button class="btn btn-primary " form="showProducts">${showProducts}</button>
            </form>
        </div>
        <div>
            <c:if test="${not empty requestScope.pageable.elements}">
                <table class="table">
                    <thead class="light">
                    <tr>
                        <th></th>
                        <th>${title}</th>
                        <th>${author}</th>
                        <th>${publisher}</th>
                        <th>${publishingYear}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.pageable.elements}" var="bookRow">
                        <tr>
                            <td>
                                <form action="frontController" method="GET">
                                    <input name="command" type="hidden" value="book_Info"/>
                                    <input name="id" type="hidden" value="${bookRow.id}"/>
                                    <button class="btn btn-primary" type="submit">${productInfo}</button>
                                    <br/>
                                </form>
                            </td>
                            <td>${bookRow.title}</td>
                            <td>${bookRow.author}</td>
                            <td>${bookRow.publisher}</td>
                            <td>${bookRow.publishingYear}</td>
                            <td>
                            </td>
                            <td>
                                <form action="frontController" method="POST">
                                    <input name="command" type="hidden" value="create_Reserve"/>
                                    <input name="bookId" type="hidden" value="${bookRow.id}"/>
                                    <button class="btn btn-primary">${createReserve}</button>
                                    <br/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: center">
                    <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                        <c:if test="${i == pageable.pageNumber}">
                            <span>
                                <button class="counter" form="showProducts" name="currentPage" style="color:red"
                                        type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                        <c:if test="${i != pageable.pageNumber}">
                            <span>
                                <button class="counter" form="showProducts" name="currentPage" type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <!----------   SHOW RESERVES  ---------->

        <div>
            <form action="frontController" id="showReserves" method="GET">
                <input name="command" type="hidden" value="show_Reserves"/>
                <button class="btn btn-primary" form="showReserves">${showReservedProducts}</button>
            </form>
        </div>
        <div>
            <c:if test="${not empty requestScope.pageableReserves.elements}">
                <table class="table">
                    <thead class="light shown-thread-reserves">
                    <tr>
                        <th></th>
                        <th>${title}</th>
                        <th>${author}</th>
                        <th>${publisher}</th>
                        <th>${publishingYear}</th>
                    </tr>
                    </thead>
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
                                <form action="frontController" method="POST">
                                    <input name="command" type="hidden" value="delete_Reserve"/>
                                    <input name="bookId" type="hidden" value="${reserveRow.id}"/>
                                    <button class="btn btn-primary" type="submit">${deleteReserve}</button>
                                    <br/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="text-align: center">
                    <c:forEach begin="1" end="${Math.ceil(pageableReserves.totalElements / pageableReserves.limit)}"
                               var="i">
                        <c:if test="${i == pageableReserves.pageNumber}">
                            <span>
                                <button class="counter" form="showReserves" name="currentPageReserve" style="color:red"
                                        type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                        <c:if test="${i != pageableReserves.pageNumber}">
                            <span>
                                <button class="counter" form="showReserves" name="currentPageReserve" type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
                <div style="text-align: center">
                    <form action="frontController" id="createOrder" method="POST" style="">
                        <input name="command" type="hidden" value="create_Order"/>
                        <input name="reservedBooks" type="hidden" value="${requestScope.pageableReserves.elements}"/>
                        <button class="btn btn-primary" form="createOrder" type="submit">${createOrder}</button>
                    </form>
                </div>
            </c:if>
            <c:if test="${empty requestScope.pageableReserves.elements}">
                <div>
                    <h3>${reservesMessage}</h3>
                </div>
            </c:if>
        </div>

        <!----------   SHOW ORDER INFO  ---------->

        <div>
            <form action="frontController" id="showOrderInfo" method="GET">
                <input name="command" type="hidden" value="order_Info"/>
                <button class="btn btn-primary" form="showOrderInfo" type="submit">${goToOrderList}</button>
            </form>
        </div>
        <div>
            <c:if test="${not empty requestScope.pageableOrders.elements}">
                <table class="table">
                    <thead class="light shown-thread-orders">
                    <tr>
                        <th></th>
                        <th>${title}</th>
                        <th>${author}</th>
                        <th>${publisher}</th>
                        <th>${publishingYear}</th>
                        <th>${genre}</th>
                        <th>${description}</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.pageableOrders.elements}" var="bookFromOrderRow">
                        <tr>
                            <td>
                            </td>
                            <td>${bookFromOrderRow.title}</td>
                            <td>${bookFromOrderRow.author}</td>
                            <td>${bookFromOrderRow.publisher}</td>
                            <td>${bookFromOrderRow.publishingYear}</td>
                            <td>${bookFromOrderRow.genre}</td>
                            <td>${bookFromOrderRow.description}</td>
                            <td>
                                <form action="frontController" method="POST">
                                    <input name="command" type="hidden" value="delete_Book_From_Order"/>
                                    <input name="bookId" type="hidden" value="${bookFromOrderRow.id}"/>
                                    <button class="btn btn-primary">${deleteFromOrder}</button>
                                    <br/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="text-align: center">
                    <c:forEach begin="1" end="${Math.ceil(pageableOrders.totalElements / pageableOrders.limit)}"
                               var="i">
                        <c:if test="${i == pageableOrders.pageNumber}">
                            <span>
                                <button class="counter" form="showOrderInfo" name="currentPageOrder" style="color:red"
                                        type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                        <c:if test="${i != pageableOrders.pageNumber}">
                            <span>
                                <button class="counter" form="showOrderInfo" name="currentPageUser" type="submit"
                                        value="${i}">${i}</button>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <!----------   ADMIN SHOW USERS  ---------->

        <c:if test="${sessionScope.role == 'admin'}">
            <div>
                <form action="frontController" id="showUsers" method="GET">
                    <input name="command" type="hidden" value="show_Users"/>
                    <button class="btn btn-primary" form="showUsers" type="submit">${showUsers}</button>
                </form>
            </div>
            <div>
                <c:if test="${not empty requestScope.pageableUsers.elements}">
                    <table class="table">
                        <thead class="light shown-thread-users">
                        <tr>
                            <th></th>
                            <th>id</th>
                            <th>${login}</th>
                            <th>${role}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.pageableUsers.elements}" var="userRow">
                            <tr>
                                <td>
                                </td>
                                <td>${userRow.id}</td>
                                <td>${userRow.login}</td>
                                <td>${userRow.role}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div style="text-align: center">
                        <c:forEach begin="1" end="${Math.ceil(pageableUsers.totalElements / pageableUsers.limit)}"
                                   var="i">
                            <c:if test="${i == pageableUsers.pageNumber}">
                            <span>
                                <button class="counter" form="showUsers" name="currentPageUser" style="color:red"
                                        type="submit"
                                        value="${i}">${i}</button>
                            </span>
                            </c:if>
                            <c:if test="${i != pageableUsers.pageNumber}">
                            <span>
                                <button class="counter" form="showUsers" name="currentPageUser" type="submit"
                                        value="${i}">${i}</button>
                            </span>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </c:if>
    </c:if>
</div>
</body>
</html>