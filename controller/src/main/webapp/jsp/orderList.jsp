<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
<title>Orders list page</title>
    </head>
    <body>

    <c:if test="${requestScope.products != '[]'}">

        <table>
            <thead>
            <tr>
                <th></th>
                <th>Title</th>
                <th>Author</th>
                <th>Publisher</th>
                <th>Publishing Year</th>
                <th></th>
            </tr>
            <thead>

            <c:forEach items="${requestScope.products}" var="product">
                <tr>
                <td><c:out value="${product.title}"/></td>
                <td><c:out value="${product.author}"/></td>
                <td><c:out value="${product.publisher}"/></td>
                <td><c:out value="${product.publishingYear}"/></td>
                </tr>
            </c:forEach>


        </table>

    </c:if>


    <form action="FrontController" method="post">
        <div >
            <input  type="hidden" name="command" value="createOrder"/>
            <input  type="submit" name="finishOrder" value="Order products"/>
        </div>
    </form>

    </body>

</html>