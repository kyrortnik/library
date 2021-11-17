<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        Error page
    </title>
</head>
<body>
<h3>${message}</h3>


<h3>
    <a href="frontController?command=go_To_Page&address=login.jsp">
        toIndexPage
    </a>
</h3>


</body>

</html>