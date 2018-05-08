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
    //начальный экран - при загрузке home
    printItemsWithPagination({}, ${sessionScope.booksOnPage});

    //экраны поиска
    searchByAuthor(printItemsWithPagination, ${sessionScope.booksOnPage});
    searchByPublisher(printItemsWithPagination, ${sessionScope.booksOnPage});
    searchByGenre(printItemsWithPagination, ${sessionScope.booksOnPage});
    searchByLetter(printItemsWithPagination, ${sessionScope.booksOnPage});
    searchByText(printItemsWithPagination, ${sessionScope.booksOnPage});


    <%--$(document).on('click', '#books-on-page-button', function (event) {--%>
        <%--let booksOnPage = $('#books-on-page-input').val();--%>
        <%--saveSearchCriterion(booksOnPage);--%>
        <%--printItemsWithPagination(${sessionScope.criteria}, ${sessionScope.booksOnPage}, ${sessionScope.foundResultText})--%>
    <%--});--%>
</script>