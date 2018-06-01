<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Pagination--%>
<div class="pagination-wrapper ">
    <ul id="authors-pag" class="pagination pagination-md"></ul>
    <form class="form-inline">
        <div class="form-group mb-2">
            Отображать на странице:
            <input type="number" name="authorsOnPage" value="${sessionScope.authorsOnPage}"
                   class="input-micro">
        </div>
        <input type="hidden" name="isPagination" value="true">
        <button type="submit" class="btn">Подтвердить</button>
    </form>
</div>
<script>
    authorsPagination(${quantityAuthors}, ${sessionScope.authorsOnPage}, ${selectedPage});
</script>