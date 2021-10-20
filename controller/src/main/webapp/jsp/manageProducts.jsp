<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

    <body>
        <head>
            <title>Manage products</title>
        </head>

        <form  name ="showAllProducts" action="frontController">
            <input type ="hidden" name="command" value="showProductsCommand">
            <input type="submit" value="Show all products"/>

        </form>
    </body>

</html>