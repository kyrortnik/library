<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>
        {bookInformationPage}
    </title>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<c:if test="${ empty requestScope.book}">
    <p><a href="frontController?command=show_Products">Back to Products</a></p>
</c:if>
<!----------   SHOW BOOK INFO  ---------->
<c:if test="${not empty requestScope.book}">
    <div>

        <table>
            <tr style="text-align: center;">
                <td><c:out value="${title}"/></td>
                <td><c:out value="${author}"/></td>
                <td><c:out value="${genre}"/></td>
                <td><c:out value="${publisher}"/></td>
                <td><c:out value="${publishingYear}"/></td>
                <td><c:out value="${pages}"/></td>
                <td><c:out value="${description}"/></td>
            </tr>

            <tbody>
            <tr style="text-align: center">
                <td>${requestScope.book.title}</td>
                <td>${requestScope.book.author}</td>
                <td>${requestScope.book.genre}</td>
                <td>${requestScope.book.publisher}</td>
                <td>${requestScope.book.publishingYear}</td>
                <td>${requestScope.book.numberOfPages}</td>
                <td>${requestScope.book.description}</td>
                <td>
                    <form method = "POST" action="frontController">
                        <input type = "hidden" name ="command" value ="create_Reserve"/>
                        <input type = "hidden" name="bookId" value="${requestScope.book.id}"/>
                        <input type="submit" value ="Reserve this Book">
                        <br/>
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
    <div>
        <form id="deleteBook" method="POST" action="frontController" >
            <input type="hidden" name="command" value="delete_Book"/>
            <input type = "hidden" name="bookId" value="${requestScope.book.id}"/>
            <button form="deleteBook" type="submit">${deleteBook}</button>
        </form>
    </div>
<!----------   UPDATE BOOK (ADMIN)  ---------->
    <div>
    <form id="editBook" method="POST" action="frontController" >
    <input type="hidden" name="bookId" value="${requestScope.book.id}"/>
    <input type="hidden" name="command" value="update_Book"/>
    <input type="text" name="title" placeholder="${bookTitle}"/>
    <input type="text" name="author" placeholder="${author}"/>
    <input type="text" name="publisher" placeholder="${publisher}"/>
    <input type="text" name="publishingYear" placeholder="${publishingYear}"/>
    <input type="text" name="isHardCover" placeholder="${isHardCover}"/>
    <input type="text" name="numberOfPages" placeholder="${numberOfPages}"/>
    <input type="text" name="genre" placeholder="${genre}"/>
    <input type="text" name="description" placeholder="${description}"/>
    <button form="editBook" type="submit">Update Book</button>
        </form>
    </div>
</c:if>
    </body>

</html>