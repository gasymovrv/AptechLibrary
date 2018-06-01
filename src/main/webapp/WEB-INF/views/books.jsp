<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="criteria" type="ru.aptech.library.util.SearchCriteriaBooks"--%>
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
    <c:if test="${not empty criteriaBooks}">
    criteria = {
        authorId: '${criteriaBooks.authorId}',
        genreId: '${criteriaBooks.genreId}',
        publisherId: '${criteriaBooks.publisherId}',
        letter: '${criteriaBooks.letter}',
        text: '${criteriaBooks.text}',
        searchType: '${criteriaBooks.searchType}'
    };
    </c:if>
    //отобразить книги и пагинатор
    printItemsWithPagination(criteria);
</script>
