<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>
            Product Information Page
        </title>
    </head>
    <body>
    <c:if test="${requestScope.product == ''}">
       <h3><c:out value="${noSuchProduct}"/></h3>
    </c:if>


    <c:if test="${requestScope.product != ''}">


<!--        <h3><c:out value="${info}"/></h3>-->


                        <table  >
                            <tr style="text-align: center;">
                                <td><c:out value="${title}"/></td>
                                <td><c:out value="${author}"/></td>
                                <td><c:out value="${genre}"/></td>
                                <td><c:out value="${publisher}"/></td>
                                <td><c:out value="${pages}"/></td>
                            </tr>

                            <tbody>
                            <tr style="text-align: center">
                                <td>${requestScope.product.title}</td>
                                <td>${requestScope.product.author}</td>
                                <td>${requestScope.product.genre}</td>
                                <td>${requestScope.product.publisher}</td>
                                <td>
                                    <form action="frontController" method="post">
                                        <input type="hidden" name="command" value="createReserve" />
                                        <input type="hidden" name="id" value="${requestScope.product.id}" />
                                        <input type="submit" name="order" value="Add to Order list" /><br/>
                                        <br/>
                                        ${errorNoCreateOrder}
                                        <br/>
                                        ${productAddedToOrder}
                                        <br/>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                            <tr>

                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    </body>

</html>