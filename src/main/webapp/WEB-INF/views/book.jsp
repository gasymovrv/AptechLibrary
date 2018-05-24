<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box -->
<div class="col-sm-9">
    <div id="main-box">
        <div class="col-sm-4">
            <div class="product-image-large">
                <img class="img-rounded" src="${showImg}?${bookId}=${book.id}" alt="Item Name">
            </div>
        </div>
        <!-- End Product Image & Available Colors -->
        <!-- Product Summary & Options -->
        <div class="col-sm-7 product-details">
            <h4>${book.name}</h4>
            <div class="price">
                <span class="price-was">$959.99</span> $999.99
            </div>
            <h5>Краткое описание</h5>
            <p>
                ${book.descr}
            </p>
            <h5>Действия</h5>
            <!-- Operations -->
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group-lg bottom-indent" role="group" aria-label="First group">
                    <a href="${bookInfo}?${bookId}=${book.id}" class="btn"
                       role="button" title="В корзину">
                        <i class="glyphicon glyphicon-shopping-cart icon-white"></i></a>
                    <a href="${showPdf}?${bookId}=${book.id}" class="btn"
                       role="button" title="Читать">
                        <i class="glyphicon glyphicon-eye-open icon-white"></i></a>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="${editBook}?${bookId}=${book.id}" class="btn admin-button"
                           role="button" title="Изменить">
                            <i class="glyphicon glyphicon-pencil icon-white"></i></a>
                        <a href="#" id="${book.id}" class="btn admin-button"
                           onclick="confirmDeleteBook(${book.id}, '${book.name}')"
                           role="button" title="Удалить">
                            <i class="glyphicon glyphicon-trash icon-white"></i></a>
                    </security:authorize>
                </div>
            </div>
        </div>
        <!-- End Product Summary & Options -->

        <!-- Full Description & Specification -->
        <div class="col-sm-12">
            <div class="tabbable">
                <!-- Tabs -->
                <ul class="nav nav-tabs product-details-nav">
                    <li class="active"><a href="#tab1" data-toggle="tab">Описание</a></li>
                    <li><a href="#tab2" data-toggle="tab">Сведения</a></li>
                </ul>
                <!-- Tab Content (Full Description) -->
                <div class="tab-content product-detail-info">
                    <div class="tab-pane active" id="tab1">
                        <h4>Рецензия</h4>
                        <p>
                            ${book.descr}
                        </p>
                    </div>
                    <!-- Tab Content (Specification) -->
                    <div class="tab-pane" id="tab2">
                        <table>
                            <tr>
                                <td>Автор</td>
                                <td>${book.author.fio}</td>
                            </tr>
                            <tr>
                                <td>Жанр</td>
                                <td>${book.genre.name}</td>
                            </tr>
                            <tr>
                                <td>Издательство</td>
                                <td>${book.publisher.name}</td>
                            </tr>
                            <tr>
                                <td>Год издания</td>
                                <td>${book.publishYear}</td>
                            </tr>
                            <tr>
                                <td>Количество страниц</td>
                                <td>${book.pageCount}</td>
                            </tr>
                            <tr>
                                <td>ISBN</td>
                                <td>${book.isbn}</td>
                            </tr>
                            <tr>
                                <td>Рейтинг</td>
                                <td>${book.rating}</td>
                            </tr>
                            <tr>
                                <td>Количестов голосов</td>
                                <td>${book.voteCount}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End Full Description & Specification -->
    <div class="pagination-wrapper " hidden="hidden" style="display: none">
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