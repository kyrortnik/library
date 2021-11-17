<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>New Book Form</title>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<div class="main-block">
    <h3>${message}</h3>
    <h1>New Book</h1>
    <form id="createBook" action="frontController" method="POST">
        <div class="info">
            <input type="hidden" name="command" value="create_Book"/>
            <input type="text" name="title" placeholder="Book title"/>
            <input type="text" name="author" placeholder="Author"/>
            <input type="text" name="publisher" placeholder="publisher"/>
            <input type="text" name="publishingYear" placeholder="publishing Year"/>
            <input type="text" name="isHardCover" placeholder="is hard cover"/>
            <input type="text" name="numberOfPages" placeholder="number of pages"/>
            <input type="text" name="genre" placeholder="Genre"/>
            <input type="text" name="description" placeholder="Description"/>
        </div>
        <button form="createBook" class="button">Submit</button>
    </form>
</div>
</body>
</html>