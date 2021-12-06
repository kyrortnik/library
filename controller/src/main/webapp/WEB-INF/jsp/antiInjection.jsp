<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>Injection attempt detected </title>
</head>
<body>
<div class="container">
    <div>
        <form style="text-align : center">
            <h style="font-weight: bold ; font-size: 200%">
                ${sessionScope.message}
            </h>
        </form>
    </div>
    <div>
        <form style="text-align : center">
            <a href="frontController?command=go_To_Page&address=login.jsp" style="font-weight: bold ; font-size: 200%">
                ${toIndexPage}
            </a>
        </form>
    </div>
    <c:if test="${sessionScope.lastCommand != null}">
        <div>
            <form style="text-align : center">
                <a href="${sessionScope.lastCommand}" style="font-weight: bold ; font-size: 200%">
                    ${toPreviousPage}
                </a>
            </form>
        </div>
    </c:if>
    </h3>
</div>

</body>
</html>
