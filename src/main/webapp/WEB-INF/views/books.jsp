<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (search) - books-->
<div id="main-box">
    <div id="row-info" class="row">
        <%--template for ajax--%>
    </div>
    <div id="box-with-rows-for-books" class="row">
        <%--template for ajax--%>
    </div>
</div>

<tiles:insertAttribute name="pagination"/>

<script>

    <c:if test="${not empty sessionScope.isDeleted}">
    if (${sessionScope.isDeleted}) {
        alert("Книга успешно удалена!");
    } else {
        alert("Произошла ошибка при удалении!");
    }
    <%session.setAttribute("isDeleted", null);%>
    </c:if>

    let criteria = null;
    //используем критерию для поиска по автору со страницы "Авторы"
    <c:if test="${not empty authorId}">
    criteria = {authorId: ${authorId}};
    </c:if>
    //отобразить книги и пагинатор
    printItemsWithPagination(criteria);
</script>
