<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/html">
    <head>
    <title>{crush}</title>
    </head>
    <body>
    <h1>${errorOccurred}</h1>
    </br>
    <h1><a href="frontController?command=go_To_Page&address=login.jsp">${toIndexPage}</a></h1>
    </body>
</html>