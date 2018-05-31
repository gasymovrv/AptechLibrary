<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Pagination--%>
<div class="pagination-wrapper" hidden="hidden">
    <ul id="books-pag" class="pagination pagination-md"></ul>
    <div class="form-inline">
        <div class="form-group mb-2">
            Отображать на странице:
            <input id="books-on-page-input" type="number" name="booksOnPage" value="${sessionScope.booksOnPage}"
                   class="input-micro">
        </div>
        <button id="books-on-page-button" class="btn">Подтвердить</button>
    </div>
</div>
<%--скрипт для пагинации встроен в скрипт отображения книг printItemsWithPagination() и вызывается на books.jsp--%>