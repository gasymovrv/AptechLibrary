<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>


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
            <label for="price" class="col-sm-2 col-form-label">Цена</label>
            <div class="col-sm-10">
                <form:input id='price' name='price' path="price" type="number" class="form-control"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="genre" class="col-sm-2 col-form-label">Жанр</label>
            <div class="col-sm-10">
                <select id="genre" class="form-control" name="genre.id">
                    <c:choose>
                        <c:when test="${not empty book.genre}">
                            <c:forEach var="g" items="${genreList}">
                                <c:choose>
                                    <c:when test="${g eq book.genre}">
                                        <option selected value="${g.id}">${g.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${g.id}">${g.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <%--TODO здесь добавить unknownAuthor--%>
                        </c:when>
                        <c:otherwise>
                            <%--TODO здесь добавить unknownAuthor + проверку выше <option value="${book.genre.id}">${book.genre.name}</option>--%>
                            <c:forEach var="g" items="${genreList}">
                                <option value="${g.id}">${g.name}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="author" class="col-sm-2 col-form-label">Автор</label>
            <div class="col-sm-10">
                <select id="author" class="form-control" name="author.id">
                    <c:choose>
                        <c:when test="${not empty book.author and book.author ne unknownAuthor}">
                            <c:forEach var="a" items="${authorList}">
                                <c:choose>
                                    <c:when test="${a eq book.author}">
                                        <option selected value="${a.id}">${a.fio}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${a.id}">${a.fio}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <option value="${unknownAuthor.id}">${unknownAuthor.fio}</option>
                        </c:when>
                        <c:otherwise>
                            <option selected value="${unknownAuthor.id}">${unknownAuthor.fio}</option>
                            <c:forEach var="a" items="${authorList}">
                                <option value="${a.id}">${a.fio}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label for="publisher" class="col-sm-2 col-form-label">Издательство</label>
            <div class="col-sm-10">
                <select id="publisher" class="form-control" name="publisher.id">
                    <c:choose>
                        <c:when test="${not empty book.publisher}">
                            <c:forEach var="p" items="${publisherList}">
                                <c:choose>
                                    <c:when test="${p eq book.publisher}">
                                        <option selected value="${p.id}">${p.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${p.id}">${p.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <%--TODO здесь добавить unknownAuthor--%>
                        </c:when>
                        <c:otherwise>
                            <%--TODO здесь добавить unknownAuthor + проверку выше <option value="${book.publisher.id}">${book.publisher.name}</option>--%>
                            <c:forEach var="p" items="${publisherList}">
                                <option value="${p.id}">${p.name}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
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
