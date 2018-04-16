<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Sidebar -->
<div class="col-sm-3 blog-sidebar">
    <h4>Жанры</h4>
    <ul class="blog-categories">
        <c:forEach var="g" items="${genreList}">
            <li><a href="#">${g.name}</a></li>
        </c:forEach>
    </ul>
</div>
