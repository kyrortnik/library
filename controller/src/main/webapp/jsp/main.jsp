<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<html>
    <head>
        <title>Main page</title>
    </head>
    <body>
    <h3>Welcome</h3>
    <hr/>
    ${user}, hello!
    <br/>
    To logout click ling below
    <hr/>
    <a href="frontController?command=logout">Logout</a>
    </body>


    <body>
    <form name = "usersForm" method = "POST" action="frontController">
    <input type="hidden" name="command" value="show_users" />
    <input type="submit" value="Show users"/>

    </form>
<!--    <a href="/jsp/productsCatalogue.jsp">Books Catalogue</a>-->
<!--    <br/>-->
<!--    <a href="/jsp/ordersCatalogue.jsp">Orders Catalogue </a>-->
<!--    <br/>-->
<!--    <a href="/jsp/ordersCatalogue.jsp">Users Catalogue </a>-->
<!--    <br/>-->



    </body>
</html>