<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Sidebar -->
<h4>Жанры</h4>
<ul class="blog-categories">
    <c:forEach var="g" items="${genreList}">
        <li><a class="genre-link" id="${g.id}" href="#">${g.name}</a></li>
    </c:forEach>
</ul>
