<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<body>
<head>
    <title>All products</title>


    <style>
        table, th, td {
            border: 1px solid #000;
        }
    </style>

</head>
<form>

    <table>
        <thead>
        <tr>
            <th></th>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Publisher</th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.products}" var="product" >
            <tr>
                <td>${product.id}</td>
                <td>${product.title}</td>
                <td>${product.author}</td>
                <td>${product.publisher}</td>

                <td>
                    <form  method="POST" action="frontController" >
                        <input type="hidden" name="command" value="productInfo" />
                        <input type="hidden" name="id" value="${product.id}" />
                        <button type="submit" >Product info</button><br/>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>





<!--<c:forEach items="${requestScope.products}" var="product">
    <tr>
        <td>
           &lt;!&ndash; <form action="frontController" method="POST">
                <input type="hidden" name="command" value="productInfo" />
                <input type="hidden" name="id" value="${product.id}" />
                <button type="submit">${info}</button><br/>
            </form>&ndash;&gt;
        </td>
        <td>${product}</td>
&lt;!&ndash;        <td>${product.author}</td>&ndash;&gt;
&lt;!&ndash;        <td>${product.publisher}</td>&ndash;&gt;

    </tr>
</c:forEach>-->

</form>
</body>

</html>