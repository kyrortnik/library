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
    <form class="form-group" id="createBook" action="frontController" method="POST">
            <input type="hidden" name="command" value="create_Book"/>
            <input type="hidden" name="bookId" value="${id}">
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="title" placeholder="${bookTitle}"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="author" placeholder="${author}"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="publisher" placeholder="${publisher}"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" type="text" name="publishingYear" placeholder="${publishingYear}"/>
            </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="isHardCover" placeholder="${isHardCover}"/>
        </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="numberOfPages" placeholder="${numberOfPages}"/>
        </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="genre" placeholder="${genre}"/>
        </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="description" placeholder="${description}"/>
        </div>
    </form>
</div>
<button form="createBook" class="button">${submit}</button>
</body>
</html>