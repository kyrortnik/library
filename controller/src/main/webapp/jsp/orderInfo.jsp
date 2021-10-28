<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Order Info Page</title>

</head>

<body>

<c:if test="${requestScope.order == ''}">
    <h3><c:out value="${noOrderCreated}"/></h3>
</c:if>


<c:if test="${requestScope.order != ''}">

    <table>
        <tbody>
        <tr style="text-align: center">
            <td>${requestScope.order.id}</td>
            <td>${requestScope.order.productIds}</td>
            <td>${requestScope.order.userId}</td>
        </tr>
        </tbody>
    </table>
</c:if>

</body>

</html>