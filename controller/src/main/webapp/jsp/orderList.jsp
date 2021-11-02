<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
<title>Your Current Reserves</title>
    </head>
    <body>
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
                   <!-- <td>
                        <form  method="GET" action="frontController" >
                            <input type="hidden" name="command" value="productInfo" />
                            <input type="hidden" name="id" value="${productRow.id}" />
                            <button type="submit" >Product info</button><br/>
                        </form>
                    </td>-->
                    <td>${productRow.title}</td>
                    <td>${productRow.author}</td>
                    <td>${productRow.publisher}</td>
                    <td>${productRow.genre}</td>
                    <!--<td>
                         <form method = "POST" action="frontController">
                             <input type = "hidden" name ="command" value ="createReserve"/>
                             <input type = "hidden" name="productId" value="${productRow.id}"/>
                             <input type="submit" value ="Reserve this Book">
                             <br/>
                             ${message}
                             <br/>
                         </form>

                    </td>-->
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

<!--    <c:if test="${not empty requestScope.products }">-->

<!--        <table>-->
<!--            <tr style="text-align: center;">-->
<!--                <td><c:out value="${title}"/></td>-->
<!--                <td><c:out value="${author}"/></td>-->
<!--                <td><c:out value="${genre}"/></td>-->
<!--                <td><c:out value="${publisher}"/></td>-->
<!--                <td><c:out value="${pages}"/></td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--                <th>Title</th>-->
<!--                <th>Author</th>-->
<!--                <th>Publisher</th>-->
<!--                <th>Publishing Year</th>-->
<!--                <th></th>-->
<!--            </tr>-->

<!--            <c:forEach items="${requestScope.products}" var="productRow">-->
<!--                <tr>-->
<!--                <td><c:out value="${productRow.title}"/></td>-->
<!--                <td><c:out value="${productRow.author}"/></td>-->
<!--                <td><c:out value="${productRow.publisher}"/></td>-->
<!--                <td><c:out value="${productRow.publishingYear}"/></td>-->
<!--                </tr>-->
<!--            </c:forEach>-->


<!--        </table>-->

<!--    </c:if>-->


    <form action="frontController" method="post">
        <div >
            <input  type="hidden" name="command" value="createOrder"/>
            <input  type="submit" name="finishOrder" value="Order products"/>
        </div>
    </form>

    </body>

</html>