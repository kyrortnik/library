<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>New Book Form</title>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="main-block">
    <h3>${message}</h3>
    <h1>${newBook}</h1>
    <form id="createBook" action="frontController" method="POST">
        <div class="info">
            <input type="hidden" name="command" value="create_Book"/>
            <input type="text" name="title" placeholder="${bookTitle}"/>
            <input type="text" name="author" placeholder="${author}"/>
            <input type="text" name="publisher" placeholder="${publisher}"/>
            <input type="text" name="publishingYear" placeholder="${publishingYear}"/>
            <input type="text" name="isHardCover" placeholder="${isHardCover}"/>
            <input type="text" name="numberOfPages" placeholder="${numberOfPages}"/>
            <input type="text" name="genre" placeholder="${genre}"/>
            <input type="text" name="description" placeholder="${description}"/>
        </div>
        <button form="createBook" class="button">${submit}</button>
    </form>
</div>
</body>
</html>