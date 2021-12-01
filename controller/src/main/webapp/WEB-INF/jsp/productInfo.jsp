<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html xmlns="http://www.w3.org/1999/html">
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>
        ${bookInformationPage}
    </title>
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
<c:if test="${ empty requestScope.book}">
    <p><a href="frontController?command=show_Products">Back to Products</a></p>
</c:if>
<!----------   SHOW BOOK INFO  ---------->
<c:if test="${not empty requestScope.book}">
    <div>

        <table class="table">
            <thead class="light">
            <tr>
                <th>${title}</th>
                <th>${author}</th>
                <th>${genre}</th>
                <th>${publisher}</th>
                <th>${publishingYear}</th>
                <th>${pages}</th>
                <th>${description}</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${requestScope.book.title}</td>
                <td>${requestScope.book.author}</td>
                <td>${requestScope.book.genre}</td>
                <td>${requestScope.book.publisher}</td>
                <td>${requestScope.book.publishingYear}</td>
                <td>${requestScope.book.numberOfPages}</td>
                <td>${requestScope.book.description}</td>
                <td>
                    <form action="frontController" id="reserveBook" method="POST">
                        <input name="command" type="hidden" value="create_Reserve"/>
                        <input name="bookId" type="hidden" value="${requestScope.book.id}"/>
                        <button class="btn btn-primary" form="reserveBook" type="submit">${reserveBook}</button>
                        <br/>
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</c:if>
<!----------   DELETE BOOK (ADMIN) ---------->
<c:if test="${sessionScope.role == 'admin'}">
    <div style="text-align : center">
        <form action="frontController" id="deleteBook" method="POST">
            <input name="command" type="hidden" value="delete_Book"/>
            <input name="bookId" type="hidden" value="${requestScope.book.id}"/>
            <button class="btn btn-primary" form="deleteBook" type="submit">${deleteBook}</button>
        </form>
    </div>
    <!----------   UPDATE BOOK (ADMIN)  ---------->
    <div class="main-block">
        <form action="frontController" class="form-group" id="editBook" method="POST">
            <input name="bookId" type="hidden" value="${requestScope.book.id}"/>
            <input name="command" type="hidden" value="update_Book"/>
            <div class="form-group col-md-6">
                <input class="form-control" name="title" placeholder="${bookTitle}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="author" placeholder="${author}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="publisher" placeholder="${publisher}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="publishingYear" placeholder="${publishingYear}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="isHardCover" placeholder="${isHardCover}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="numberOfPages" placeholder="${numberOfPages}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="genre" placeholder="${genre}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="description" placeholder="${description}" type="text"/>
            </div>
            <div class="form-group col-md-6">
                <input class="form-control" name="description" placeholder="${description}" type="text"/>
            </div>
            <div style="text-align : center">
                <button class="btn btn-primary" form="editBook" type="submit">${updateBook}</button>
            </div>
        </form>
    </div>
</c:if>
</body>
</html>