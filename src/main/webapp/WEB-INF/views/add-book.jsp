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
    <form:form id="addBookForm" method="post" modelAttribute="book" action="${contextPath}addBookAction"  enctype="multipart/form-data">
        <div class="form-group row">
            <label for="name" class="col-sm-2 col-form-label">Название</label>
            <div class="col-sm-10">
                <form:input id='name' name='name' path="name" type="text" class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="content" class="col-sm-2 col-form-label">Загрузите книгу (в формате .pdf)</label>
            <div class="col-sm-10">
                <%--загружаем файл, используя библиотеку commons-fileupload и бин multipartResolver--%>
                <input id="content" type="file" name="file1" class="form-control-file"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="image" class="col-sm-2 col-form-label">Загрузите изображение для обложки</label>
            <div class="col-sm-10">
                <%--загружаем файл, используя библиотеку commons-fileupload и бин multipartResolver--%>
                <input id="image" type="file" name="file2" class="form-control-file"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="pageCount" class="col-sm-2 col-form-label">Количество страниц</label>
            <div class="col-sm-10">
                <form:input id='pageCount' name='pageCount' path="pageCount" type="number" class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="isbn" class="col-sm-2 col-form-label">ISBN</label>
            <div class="col-sm-10">
                <form:input id='isbn' name='isbn' path="isbn" type="text" class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="publishYear" class="col-sm-2 col-form-label">Год издательства</label>
            <div class="col-sm-10">
                <form:input id='publishYear' name='publishYear' path="publishYear" type="number" class="form-control"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="genre" class="col-sm-2 col-form-label">Жанр</label>
            <div class="col-sm-10">
                <select id="genre" class="form-control" name="genre.id">
                    <c:forEach var="g" items="${genreList}">
                        <option value="${g.id}">${g.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="author" class="col-sm-2 col-form-label">Автор</label>
            <div class="col-sm-10">
                <select id="author" class="form-control" name="author.id">
                    <c:forEach var="a" items="${authorList}">
                        <option value="${a.id}">${a.fio}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="publisher" class="col-sm-2 col-form-label">Издательство</label>
            <div class="col-sm-10">
                <select id="publisher" class="form-control" name="publisher.id">
                    <c:forEach var="p" items="${publisherList}">
                        <option value="${p.id}">${p.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="descr" class="col-sm-2 col-form-label">Описание</label>
            <div class="col-sm-10">
                <form:input id='descr' name='descr' path="descr" type="text" class="form-control"/>
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
