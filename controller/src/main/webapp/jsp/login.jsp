<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <head>
        <%@ include file="parts/meta.jsp" %>
        <title>
        <c:out value="${login}"/>
        </title>
    </head>
    <!-------Language change----------->
    <div >
        <div>
            <form action="frontController" method="GET">
                <input type="hidden" name="command" value="change_Language"/>
                <input type="hidden" name="local" value="en"/>
                <button type="submit">
                    EN
                </button>
            </form>
        </div>
        <div>
            <form action="frontController" method="GET">
                <input type="hidden" name="command" value="change_Language"/>
                <input type="hidden" name="local" value="ru"/>
                <button type="submit">
                    РУ
                </button>
            </form>
        </div>
    </div>

<!--   LOGIN FORM -->

    <body>
        <form name="loginForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="login" />
        ${login}:<br/>
        <input type="text" name="login" value=""/>
        <br/>
        ${password}:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        <h3>${loginErrorMessage}</h3>
        <br/>
        <input type="submit" value="${login}"/>
        </form><hr/>

        <!--   REGISTRATION FORM FORM -->

        <form name = "registrationForm" method="POST" action="frontController">
        <input type="hidden" name = "command" value="registration" />
        ${registrationForm}:<br/>
        <br/>${login}<br/>
        <input type = "text" name = "login" value=""/>
        <br/>${password}<br/>
        <input type ="password" name = "password" value=""/>
        <br/>${passwordAgain}<br/>
        <input type ="password" name = "secondPassword" value=""/>
        <br/>
        <br/>
        <input type="submit" value="${registration}"/>
        <br/>
        <h3>${registrationErrorMessage}</h3>
        <br/>
        </form>



    </body>
</html>