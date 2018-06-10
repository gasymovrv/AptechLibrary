<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="criteria" type="ru.aptech.library.util.SearchCriteriaBooks"--%>
<!-- Main box (search) - books-->
<div id="main-box">

    <div class="row">
        <c:if test="${not empty sessionScope.isDeleted}">
            <c:choose>
                <c:when test="${sessionScope.isDeleted}">
                    <div class="alert alert-success info-message" role="alert" hidden>
                        Книга успешно удалена!
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger info-message" role="alert" hidden>
                        Произошла ошибка при удалении!
                    </div>
                </c:otherwise>
            </c:choose>
            <script>
                showInfoMessage(10);
            </script>
            <%session.setAttribute("isDeleted", null);%>
        </c:if>
    </div>

    <div id="row-info" class="row">
        <%--template for ajax--%>
    </div>

    <div id="box-with-rows-for-books" class="row">
        <%--template for ajax--%>
    </div>

</div>

<tiles:insertAttribute name="pagination"/>

<script>
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
