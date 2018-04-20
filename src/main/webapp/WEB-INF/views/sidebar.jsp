<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- Sidebar -->
<div class="col-sm-3 blog-sidebar">
    <h4>Жанры</h4>
    <ul id="sidebar-genres" class="blog-categories">
        <c:forEach var="g" items="${genreList}">
            <li><a id="${g.id}" href="#">${g.name}</a></li>
        </c:forEach>
    </ul>
</div>
