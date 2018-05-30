<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>

<!-- Main box (non search) - add book-->
<div class="center-block col-sm-9" style="float: none;">
    <c:if test="${not empty isAdded}">
        <c:choose>
            <c:when test="${isAdded}">
                <div class="alert alert-success" role="alert">
                    Книга успешно добавлена!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    Произошла ошибка при добавлении! Возможно такая книга уже существует
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <form:form method="post" modelAttribute="book" action="${contextPath}addBookAction" enctype="multipart/form-data">
        <jsp:include page="add-or-edit-book.jsp"/>
    </form:form>
</div>
