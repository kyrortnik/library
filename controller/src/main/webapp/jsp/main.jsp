<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Main page</title>
    </head>
    <body>
    <h3>Welcome</h3>
    <hr/>
    ${user}, hello!
    <br/>
    To logout click link below
    <hr/>
<!--    <a href="frontController?command=logout">Logout</a>-->

    <c:if test="${sessionScope.role != null}">
    <form name="logoutForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="logout" />
        <input type = "submit" value ="logout">
    </form>

    <br/>
    <a href ="/jsp/manageUsers.jsp"> Manage users </a>
    <br/>
    <a href ="/jsp/manageProducts.jsp"> Manage products</a>
    <br/>
    <a href ="/jsp/manageOrders.jsp"> Manage orders</a>




   <!-- <form name="testForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="showProducts" />
        <input type = "submit" value ="show products testing">
    </form>-->
    <div>
    <form id="showProducts" method="GET" action="frontController">
    <input type="hidden" name="command" value="showProducts"/>
    <button form="showProducts" type="submit">Show products</button>
    </form>
    </div>
            <div>
                <table>
                    <thead>
                    <tr>
                        <td></td>
                        <td><h4><c:out value="title"/></h4></td>
                        <td><h4><c:out value="author"/></h4></td>
                        <td><h4><c:out value="publishing Year"/></h4></td>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${requestScope.pageable.elements}" var="productRow">
                        <tr>
                            <td>
                                <form  method="POST" action="frontController" >
                                    <input type="hidden" name="command" value="productInfo" />
                                    <input type="hidden" name="id" value="${productRow.id}" />
                                    <button type="submit" >Product info</button><br/>
                                </form>
                            </td>
                            <td>${productRow.title}</td>
                            <td>${productRow.author}</td>
                            <td>${productRow.publisher}</td>
                            <td>${productRow.publishingYear}</td>


                            <td>
                                <button type="button">Add To Basket Button</button><br/>
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

    <form name = "showReserveList" method="POST" action ="frontController">
        <input type="hidden" name = "command" value="showReserves" />
        <input type ="submit" value="Go to Reserved products"/>

    </form>

    <form name ="showOrderInfo" method="POST" action ="frontController">
        <input type = "hidden" name ="command" value="orderInfo"/>
        <input type ="submit" value="Go to Products ordered"/>
    </form>


        </c:if>
    </body>
</html>