<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (search) - book info-->
<div id="main-box">

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