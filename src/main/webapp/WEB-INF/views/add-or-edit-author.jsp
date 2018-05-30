<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="author" type="ru.aptech.library.entities.Author"--%>


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
        <div class="form-group">
            <label for="books">Выберите книги данного автора</label>
            <div>
                <select class="form-control" id="books" name="books" multiple="multiple" size="6">
                    <c:forEach var="b" items="${bookList}">

                        <c:choose>
                            <c:when test="${customFn:contains(author.books, b)}">
                                <option selected value="${b.id}">${b.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${b.id}">${b.name}</option>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </select>
            </div>
            <small class="form-text text-muted">
                <sup>*</sup> Если у книги уже был указан автор, то он заменится на текущего
            </small>
        </div>
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group-sm pull-right" role="group" aria-label="First group">
                <button type="submit" class="btn btn-sm">Сохранить</button>
                <button type="reset" class="btn btn-sm">Отмена</button>
            </div>
        </div>
