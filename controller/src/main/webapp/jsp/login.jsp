<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="en" />
<fmt:setBundle basename="localization.local" var="rb" />

<html>
    <head>
<title>
<c:out value="login"/>
</title>
    </head>
    <body>
        <form name="loginForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="login" />
        Login:<br/>
        <input type="text" name="login" value=""/>
        <br/>Password:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        ${errorLoginPassMessage}
        <br/>
        ${wrongAction}
        <br/>
        ${nullPage}
        <br/>
        <fmt:message key="local.login" bundle="${rb}" var = "var"/>
        <input type="submit" value="${var}"/>
        </form><hr/>
    </body>
</html>