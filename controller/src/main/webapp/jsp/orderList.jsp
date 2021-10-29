<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
<title>Orders list page</title>
    </head>
    <body>

    <c:if test="${requestScope.products != '[]'}">

        <table>
            <tr style="text-align: center;">
                <td><c:out value="${title}"/></td>
                <td><c:out value="${author}"/></td>
                <td><c:out value="${genre}"/></td>
                <td><c:out value="${publisher}"/></td>
                <td><c:out value="${pages}"/></td>
            </tr>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publishing Year</th>
                <th></th>
            </tr>

            <c:forEach items="${requestScope.products}" var="productRow">
                <tr>
                <td><c:out value="${productRow.title}"/></td>
                <td><c:out value="${productRow.author}"/></td>
                <td><c:out value="${productRow.publisher}"/></td>
                <td><c:out value="${productRow.publishingYear}"/></td>
                </tr>
            </c:forEach>


        </table>

    </c:if>


    <form action="frontController" method="post">
        <div >
            <input  type="hidden" name="command" value="createOrder"/>
            <input  type="submit" name="finishOrder" value="Order products"/>
        </div>
    </form>

    </body>

</html>