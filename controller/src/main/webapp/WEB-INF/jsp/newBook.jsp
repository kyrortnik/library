<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>${newBookForm}</title>
</head>
<body>
<%@ include file="parts/header.jsp" %>
</br>
<div style="display: flex; justify-content :start ">
    <form action="frontController" id="toMainPage" method="POST">
        <input name="command" type="hidden" value="go_to_Page"/>
        <input name="address" type="hidden" value="main.jsp"/>
        <button class="btn btn-primary" form="toMainPage" type="submit">${toMain}</button>
    </form>
</div>
<div class="main-block">
    <div>
        <form style="text-align : center">
            <a style="font-weight: bold ; font-size: 200%">
                ${sessionScope.message}
            </a>
        </form>
    </div>
    <div class="main-block">
        <div>
            <form style="text-align : center">
                <h1 style="font-weight: bold ; font-size: 200%">
                    ${newBook}
                </h1>
            </form>
        </div>

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
            <input class="form-control" type="text" name="numberOfPages" placeholder="${numberOfPages}"/>
        </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="genre" placeholder="${genre}"/>
        </div>
        <div class="form-group col-md-6">
            <input class="form-control" type="text" name="description" placeholder="${description}"/>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" name ="isHardCover" value="true" id="isHard">
            <label class="form-check-label" for="isHard">
                ${isHardCover}
            </label>
        </div>
    </form>
</div>
<button class="btn btn-primary" form="createBook">${submit}</button>
</body>
</html>