<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Sidebar -->
<div class="col-sm-4 blog-sidebar">
    <h4>Поиск книг</h4>
    <form>
        <div class="input-group">
            <input class="form-control input-md" id="appendedInputButtons" type="text">
            <span class="input-group-btn">
				<button class="btn btn-md" type="button">Search</button>
			</span>
        </div>
    </form>
    <h4>Жанры</h4>
    <ul class="blog-categories">
        <%--<c:forEach var="g" items="${genreDAOImpl.genres}">--%>
        <c:forEach var="g" items="${genreList}">
            <li><a href="#">${g.name}</a></li>
            <%--<li><a href="#">Sed sit amet metus sit</a></li>--%>
            <%--<li><a href="#">Nunc et diam volutpat tellus ultrices</a></li>--%>
            <%--<li><a href="#">Quisque sollicitudin cursus felis</a></li>--%>
            <%--<li><a href="#">Lorem ipsum</a></li>--%>
            <%--<li><a href="#">Sed sit amet metus</a></li>--%>
            <%--<li><a href="#">Nunc et diam </a></li>--%>
            <%--<li><a href="#">Quisque</a></li>--%>
            <%--<li><a href="#">Lorem ipsum</a></li>--%>
            <%--<li><a href="#">Sed sit amet metus</a></li>--%>
            <%--<li><a href="#">Nunc et diam </a></li>--%>
            <%--<li><a href="#">Quisque</a></li>--%>
            <%--<li><a href="#">Lorem ipsum</a></li>--%>
            <%--<li><a href="#">Sed sit amet metus</a></li>--%>
            <%--<li><a href="#">Nunc et diam </a></li>--%>
            <%--<li><a href="#">Quisque</a></li>--%>
            <%--<li><a href="#">Lorem ipsum</a></li>--%>
            <%--<li><a href="#">Sed sit amet metus</a></li>--%>
            <%--<li><a href="#">Nunc et diam </a></li>--%>
            <%--<li><a href="#">Quisque</a></li>--%>
        </c:forEach>
    </ul>
</div>
