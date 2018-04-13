<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<body>

<table border="1px">
    <tr>
        <th>#</th>
        <th>Значение</th>
    </tr>
    <c:forEach var="s" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${s}</td>
        </tr>
    </c:forEach>
</table>

</body>

</html>
