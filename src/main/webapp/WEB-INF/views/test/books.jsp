<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books</title>
</head>
<body>
<table border="1px">
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>имя</th>
        <th>жанр</th>
        <th>автор</th>
        <th>год издания</th>
        <th>издательство</th>
        <th>описание</th>
        <th>рейтинг</th>
        <th>кол-во голосов</th>
        <th>кол-во страниц</th>
    </tr>

    <c:forEach var="b" items="${bookList}" varStatus="varStat">
        <tr>
            <td>${varStat.count}</td>
            <td>${b.id}</td>
            <td>${b.name}</td>
            <td>${b.genre.name}</td>
            <td>${b.author.fio}</td>
            <td>${b.publishYear}</td>
            <td>${b.publisher.name}</td>
            <td>${b.descr}</td>
            <td>${b.rating}</td>
            <td>${b.voteCount}</td>
            <td>${b.pageCount}</td>
        </tr>
    </c:forEach>

</table>

<c:forEach var="b" items="${bookListFromParam}" varStatus="varStat">
    <div style="margin: 50px; border: 1px beige">
        <ul>
            <li>${b.id}</li>
            <li>${b.name}</li>
            <li>${b.genre.name}</li>
            <li>${b.author.fio}</li>
            <li>${b.publishYear}</li>
            <li>${b.publisher.name}</li>
            <li>${b.descr}</li>
            <li>${b.rating}</li>
            <li>${b.voteCount}</li>
            <li>${b.pageCount}</li>
        </ul>
    </div>
</c:forEach>

</body>
</html>
