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
        <fmt:message key="local.login" bundle="${rb}" var = "var"/>
        <input type="submit" value="${var}"/>
        </form><hr/>

        <form name = "registrationForm" method="POST" action="frontController">
        <input type="hidden" name = "command" value="registration" />
        Registration form:<br/>
        <br/>Login<br/>
        <input type = "text" name = "login" value=""/>
        <br/>Password<br/>
        <input type ="text" name = "password" value=""/>
        <br/>Password again<br/>
        <input type ="text" name = "secondPassword" value=""/>
        <br/>
        <br/>
            <c:if test="requestScope.message eq 'registrationFail'">
                <a>Registration failed</a>
            </c:if>
         <br/>
         <input type="submit" value="registration"/>
        </form>



        <c:if test="${requestScope.message == 'noSuchUser'}">
            <div>
                <h3>No Such User exists</h3>
            </div>
        </c:if>


        <c:if test="${requestScope.message == 'registrationFail'}">
            <div>
                <h3>Failed registration</h3>
            </div>
        </c:if>





    </body>
</html>