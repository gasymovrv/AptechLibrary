<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>

<!-- Main box (non search) - edit book-->
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
        <div class="form-group row">
            <label for="fio" class="col-sm-2 col-form-label">Полное имя</label>
            <div class="col-sm-10">
                <form:input id='fio' name='fio' path="fio" type="text" class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="birthday" class="col-sm-2 col-form-label">Дата рождения</label>
            <div class="col-sm-10">
                <input id="birthday" type="text" name="date" class="datepicker-here"
                       data-position="right top"
                       data-date-format='dd.mm.yyyy'
                       value="${date}"
                />
            </div>
        </div>
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group-sm pull-right" role="group" aria-label="First group">
                <button type="submit" class="btn btn-sm">Сохранить</button>
                <button type="reset" class="btn btn-sm">Отмена</button>
            </div>
        </div>
    </form:form>
</div>
