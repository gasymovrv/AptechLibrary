<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="author" type="ru.aptech.library.entities.Author"--%>

<!-- Main box (non search) - edit author-->
<div class="center-block col-sm-9" style="float: none;">
    <c:if test="${not empty isEdited}">
        <c:choose>
            <c:when test="${isEdited}">
                <div class="alert alert-success" role="alert">
                    Информация об авторе успешно изменена!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    Произошла ошибка при попытке обновить информацию об авторе!
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <form:form method="post" modelAttribute="author" action="${contextPath}authors/editAuthorAction?authorId=${author.id}">
        <jsp:include page="add-or-edit-author.jsp"/>
    </form:form>
</div>
