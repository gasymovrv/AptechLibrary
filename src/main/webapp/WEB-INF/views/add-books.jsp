<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="book" type="ru.aptech.library.entities.Book"--%>

<%--private long id;--%>
<%--private String name;--%>
<%--private byte[] content;--%>
<%--private int pageCount;--%>
<%--private String isbn;--%>
<%--private Genre genre;--%>
<%--private Author author;--%>
<%--private int publishYear;--%>
<%--private Publisher publisher;--%>
<%--private byte[] image;--%>
<%--private String descr;--%>
<%--private String bookcol;--%>
<%--private Integer rating;--%>
<%--private Long voteCount;--%>

<!-- Main box -->
<div class="col-sm-10 mx-auto">
    <c:if test="${not empty successAdded}">
        <c:choose>
            <c:when test="${successAdded}">
                <div class="alert alert-success" role="alert">
                    Книга успешно добавлена!
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger" role="alert">
                    Произошла ошибка при добавлении!
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <form:form id="addBookForm" method="post" modelAttribute="book" action="${contextPath}addBookAction">
        <div class="form-group row">
            <label for="name" class="col-sm-2 col-form-label">Название</label>
            <div class="col-sm-10">
                <form:input id='name' name='name' path="name" type="text" class="form-control"/>
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
        <button type="submit" class="btn pull-right">Добавить</button>
        <%--<div class="form-group row">--%>
        <%--<label for="pageCount" class="col-sm-2 col-form-label">Количество страниц</label>--%>
        <%--<div class="col-sm-10">--%>
        <%--<form:input id='pageCount' name='pageCount' path="pageCount" type="text" class="form-control" />--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="form-group row">--%>
        <%--<label for="pageCount" class="col-sm-2 col-form-label">Количество страниц</label>--%>
        <%--<div class="col-sm-10">--%>
        <%--<form:input id='pageCount' name='pageCount' path="pageCount" type="text" class="form-control" />--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="form-group row">--%>
        <%--<label for="pageCount" class="col-sm-2 col-form-label">Количество страниц</label>--%>
        <%--<div class="col-sm-10">--%>
        <%--<form:input id='pageCount' name='pageCount' path="pageCount" type="text" class="form-control" />--%>
        <%--</div>--%>
        <%--</div>--%>
    </form:form>
</div>
