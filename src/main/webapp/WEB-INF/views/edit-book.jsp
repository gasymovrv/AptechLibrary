<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>

<!-- Main box (non search) - edit book-->
<div class="center-block col-sm-9" style="float: none;">
    <c:if test="${not empty isEdited}">
        <c:choose>
            <c:when test="${isEdited}">
                <div class="alert alert-success info-message" role="alert" hidden>
                    Информация о книге успешно изменена!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger info-message" role="alert" hidden>
                    Произошла ошибка при попытке обновить книгу!
                </div>
            </c:otherwise>
        </c:choose>
        <script>
            showInfoMessage();
        </script>
    </c:if>
    <form:form id="change-book-form" method="post" modelAttribute="book" action="${contextPath}books/editBookAction?bookId=${book.id}"  enctype="multipart/form-data">
        <jsp:include page="add-or-edit-book.jsp"/>
    </form:form>
</div>
