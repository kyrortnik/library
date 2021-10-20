<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

    <body>
        <head>
            <title>Catalogue page</title>
        </head>

        <form method="get" action="FrontController">
            <input type="hidden" name="command" value="showBooks"/>
            <button style="min-width:100px;height:75px;white-space:pre-line;" class="btn btn-default" type="submit">
                <c:out value="show books"/>
            </button>
        </form>
    </body>

</html>