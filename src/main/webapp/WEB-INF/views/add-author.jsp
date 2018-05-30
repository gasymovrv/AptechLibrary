<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="author" type="ru.aptech.library.entities.Author"--%>

<!-- Main box (non search) - add author-->
<div class="center-block col-sm-9" style="float: none;">
    <c:if test="${not empty isAdded}">
        <c:choose>
            <c:when test="${isAdded}">
                <div class="alert alert-success" role="alert">
                    Автор успешно добавлен!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    Произошла ошибка при добавлении! Возможно такой автор уже существует
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <form:form method="post" modelAttribute="author" action="${contextPath}authors/addAuthorAction">
        <jsp:include page="add-or-edit-author.jsp"/>
    </form:form>
</div>
