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
    To logout click link below
    <hr/>
<!--    <a href="frontController?command=logout">Logout</a>-->
    <form name="logoutForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="logout" />
        <input type = "submit" value ="logout">
    </form>

    <br/>
    <a href ="/jsp/manageUsers.jsp"> Manage users </a>
    <br/>
    <a href ="/jsp/manageProducts.jsp"> Manage products</a>
    <br/>
    <a href ="/jsp/manageOrders.jsp"> Manage orders</a>

    <form name="testForm" method="POST" action="frontController">
        <input type="hidden" name="command" value="showProducts" />
        <input type = "submit" value ="show products testing">
    </form>

    <form name = "showOrderList" method="POST" action ="frontController">
        <input type="hidden" name = "command" value="showReserves" />
        <input type ="submit" value="Go to Reserved products"/>

    </form>



    </body>
</html>