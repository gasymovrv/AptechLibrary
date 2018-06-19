<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>

<!-- Main box (non search) - add book-->
<div class="center-block col-sm-9" style="float: none;">

    <c:if test="${not empty isAdded}">
        <c:choose>
            <c:when test="${isAdded}">
                <div class="alert alert-success info-message" role="alert" hidden>
                    Книга успешно добавлена!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger info-message" role="alert" hidden>
                    Произошла ошибка при добавлении! Возможно такая книга уже существует
                </div>
            </c:otherwise>
        </c:choose>
        <script>
            showInfoMessage();
        </script>
    </c:if>

    <form:form id="change-book-form" method="post" modelAttribute="book" action="${contextPath}books/addBookAction" enctype="multipart/form-data">
        <jsp:include page="add-or-edit-book.jsp"/>
    </form:form>
</div>
