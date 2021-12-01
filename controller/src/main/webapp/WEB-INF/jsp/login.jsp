<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>
        <c:out value="${login}"/>
    </title>

</head>
<!-------Language change----------->

<body class="text-center">
<div style="display: flex; justify-content : center">
    <form action="frontController" method="GET">
        <input type="hidden" name="command" value="change_Language"/>
        <input type="hidden" name="local" value="en"/>
        <button class="btn btn-lg btn-primary" type="submit">
            EN
        </button>
    </form>
    <form action="frontController" method="GET">
        <input type="hidden" name="command" value="change_Language"/>
        <input type="hidden" name="local" value="ru"/>
        <button class="btn btn-lg btn-primary" type="submit">
            РУ
        </button>
    </form>
</div>


<!----------  LOGIN FORM  ---------->

<div class="container">

    <form class="form-signin" name="loginForm" method="POST" action="frontController">
        <h2 class="form-signin-heading">${signIn}</h2>
        <input type="hidden" name="command" value="login"/>
        <label for="inputLogin" class="sr-only"></label>
        <input id="inputLogin" class="form-control" type="text" name="login" value="" placeholder="${login}"/>
        <label for="inputPassword" class="sr-only"></label>
        <input id="inputPassword" class="form-control" type="password" name="password" value=""
               placeholder="${password}"/>
        <h3>${sessionScope.message}</h3>
        <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">${login}</button>
    </form>
    <!----------   REGISTRATION FORM FORM  ---------->

    <form class="form-signin" name="registrationForm" method="POST" action="frontController">
        <h2 class="form-signin-heading">${registrationForm}</h2>
        <br/>
        <input type="hidden" name="command" value="registration"/>
        <label for="inputLoginReg" class="sr-only"></label>
        <input id="inputLoginReg" class="form-control" type="text" name="login" value="" placeholder="${login}"/>
        <label for="inputPasswordReg" class="sr-only"></label>
        <input id="inputPasswordReg" class="form-control" type="password" name="password" value=""
               placeholder="${password}"/>
        <label for="inputSecondPassword" class="sr-only"></label>
        <input id="inputSecondPassword" class="form-control" type="password" name="secondPassword" value=""
               placeholder="${passwordAgain}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">${registration}</button>
    </form>
</div>
</body>
</html>