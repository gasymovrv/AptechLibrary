<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box -->
<div class="col-sm-9">
    <div id="books-box">
        <div id="box-with-rows-for-books" class="row">
            <%--template--%>
        </div>
    </div>
    <div class="pagination-wrapper ">
        <ul id="books-pag" class="pagination pagination-md"></ul>
        <div class="form-inline">
            <div class="form-group mb-2">
                Книг на странице:
                <input id="books-on-page-input" type="number" name="booksOnPage" value="${sessionScope.booksOnPage}" class="input-micro">
            </div>
            <button id="books-on-page-button" type="button" class="btn">Подтвердить</button>
        </div>
    </div>
</div>

<script>
    $(document).on('click', '#books-on-page-button', function () {
        let booksOnPage = $('#books-on-page-input').val();
        printItemsWithPagination(getCriteria(), booksOnPage);
    });
    //начальный экран - при загрузке home
    printItemsWithPagination();
    //экраны поиска
    searchByAuthor();
    searchByPublisher();
    searchByGenre();
    searchByLetter();
    searchByText();
</script>