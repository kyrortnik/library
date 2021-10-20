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
    <a href="frontController?command=logout">Logout</a>
    </body>


    <body>
    <br/>
    <a href ="/jsp/manageUsers.jsp"> Manage users </a>
    <br/>
    <a href ="/jsp/manageProducts.jsp"> Manage products</a>
    <br/>
    <a href ="/jsp/manageOrders.jsp"> Manage orders</a>



    </body>
</html>