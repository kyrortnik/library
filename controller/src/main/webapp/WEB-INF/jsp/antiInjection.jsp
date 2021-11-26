
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="parts/meta.jsp" %>
    <title>${antiInjection}</title>
</head>
<body style="">

<hr/>
<h1>
    ${errorOccupied}
</h1>
<hr/>
<h1>
    ${injectionDetected}
</h1>

<hr/>

<h3>
    <a href="FrontController?command=goToPage&address=index.jsp">
        ${toIndexPage}
    </a>
</h3>
<h3>
    <c:if test="${sessionScope.lastCommand != null}">
        <a href="${sessionScope.lastCommand}">
            ${toPreviousPage}
        </a>
    </c:if>
</h3>
</body>
</html>
