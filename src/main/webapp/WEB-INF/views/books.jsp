<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box -->
<div class="col-sm-9">
    <div id="books-box">
        <div id="box-with-rows-for-books" class="row">
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
    printItemsWithPagination({}, getBooksOnPage());
    //экраны поиска
    searchByAuthor(printItemsWithPagination);
    searchByPublisher(printItemsWithPagination);
    searchByGenre(printItemsWithPagination);
    searchByLetter(printItemsWithPagination);
    searchByText(printItemsWithPagination);
</script>