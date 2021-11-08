<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local" var="loc" />
        <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
        <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
        <fmt:message bundle="${loc}" key="local.locbutton.name.pl" var="pl_button" />
        <fmt:message bundle="${loc}" key="local.login" var="login" />
        <fmt:message bundle="${loc}" key="local.password" var="password" />
        <fmt:message bundle="${loc}" key="local.registration" var="registration" />
        <fmt:message bundle="${loc}" key="local.registrationForm" var="registrationForm" />

<title>
<c:out value="${login}"/>
</title>
    </head>
    <!-------Language change----------->
    <div >
        <div>
            <form action="frontController" method="GET">
                <input type="hidden" name="command" value="changeLanguage"/>
                <input type="hidden" name="local" value="en"/>
                <button type="submit">
                    EN
                </button>
            </form>
        </div>
        <div>
            <form action="frontController" method="GET">
                <input type="hidden" name="command" value="changeLanguage"/>
                <input type="hidden" name="local" value="ru"/>
                <button type="submit">
                    РУ
                </button>
            </form>
        </div>
        <div>
            <form action="frontController" method="GET">
                <input type="hidden" name="command" value="changeLanguage"/>
                <input type="hidden" name="local" value="pl"/>
                <button type="submit">
                    PL
                </button>
            </form>
        </div>
    </div>

    <!-------Language change----------->





    <body>
        <form name="loginForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="login" />
        ${login}:<br/>
        <input type="text" name="login" value=""/>
        <br/>
        ${password}:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        ${errorLoginPassMessage}
        <br/>
        ${wrongAction}
        <br/>
        ${nullPage}
        <br/>
        <input type="submit" value="${login}"/>
        </form><hr/>

        <form name = "registrationForm" method="POST" action="frontController">
        <input type="hidden" name = "command" value="registration" />
        ${registrationForm}:<br/>
        <br/>${login}<br/>
        <input type = "text" name = "login" value=""/>
        <br/>${password}<br/>
        <input type ="text" name = "password" value=""/>
        <br/>${passwordAgain}<br/>
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
                <h3>${noSuchUser}</h3>
            </div>
        </c:if>


        <c:if test="${requestScope.message == 'registrationFail'}">
            <div>
                <h3>${failedRegistration}</h3>
            </div>
        </c:if>

    </body>
</html>