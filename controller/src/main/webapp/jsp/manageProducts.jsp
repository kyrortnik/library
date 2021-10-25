<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html>


        <head>
            <title>Manage products</title>
        </head>
<body>
        <form name ="showProducts" method ="POST" action="frontController" >
            <input type ="hidden" name="command" value="showProducts"/>
            <input type="submit" value="Show all products"/>

        </form>

</body>


</html>