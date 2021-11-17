<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>
        Product Information Page
    </title>
</head>
<body>
<%@ include file="parts/header.jsp" %>
<c:if test="${ empty requestScope.book}">
    <h3>${noSuchProduct}</h3>
    <h3>${reserveErrorMessage}</h3>
    <!--        <c:set var="backURL" value="${requestScope.backURL}"/>-->
    <!--        <p> <a href="${backURL}">Go back to product</a></p>-->
    <p><a href="frontController?command=show_Products">Back to Products</a></p>
</c:if>

<c:if test="${not empty requestScope.book}">
    <div>

        <table>
            <tr style="text-align: center;">
                <td><c:out value="${title}"/></td>
                <td><c:out value="${author}"/></td>
                <td><c:out value="${genre}"/></td>
                <td><c:out value="${publisher}"/></td>
                <td><c:out value="${pages}"/></td>
                <td><c:out value="${description}"/></td>
            </tr>

            <tbody>
            <tr style="text-align: center">
                <td>${requestScope.book.title}</td>
                <td>${requestScope.book.author}</td>
                <td>${requestScope.book.genre}</td>
                <td>${requestScope.book.publisher}</td>
                <td>${requestScope.book.numberOfPages}</td>
                <td>${requestScope.book.description}</td>
                <td>
                    <!-- <form action="frontController" method="post">
                         <input type="hidden" name="command" value="createReserve" />
                         <input type="hidden" name="productId" value="${requestScope.product.id}" />
                         <input type="submit" name="order" value="Add to Order list" /><br/>
                         <br/>
                         ${errorNoCreateOrder}
                         <br/>
                         ${productAddedToOrder}
                         <br/>
                     </form>-->
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

            <h3>${reserveErrorMessage}</h3>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.role == 'admin'}">
    <div>
        <form id="deleteBook" action="frontController" method="POST">
            <input type="hidden" name="command" value="delete_Book"/>
            <input type = "hidden" name="bookId" value="${requestScope.book.id}"/>
            <button form="deleteBook" type="submit">Delete Book</button>

        </form>
    </div>

    <div>
    <form id="editBook" action="frontController" method="POST">

    <input type = "hidden" name="bookId" value="${requestScope.book.id}"/>
    <input type="hidden" name="command" value="update_Book"/>
    <input type="text" name="title" placeholder="Book title"/>
    <input type="text" name="author" placeholder="Author"/>
    <input type="text" name="publisher" placeholder="publisher"/>
    <input type="text" name="publishingYear" placeholder="publishing Year"/>
    <input type="text" name="isHardCover" placeholder="is hard cover"/>
    <input type="text" name="numberOfPages" placeholder="number of pages"/>
    <input type="text" name="genre" placeholder="Genre"/>
    <input type="text" name="description" placeholder="Description"/>
<!--    <input type="submit" value ="Reserve this Book"/>-->
    <button form="editBook" type="submit">Update Book</button>
        </form>
    </div>

</c:if>
    </body>

</html>