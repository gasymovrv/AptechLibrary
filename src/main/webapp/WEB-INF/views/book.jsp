<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty errorContent and errorContent}">
    <div class="row">
        <div class="alert alert-warning info-message" role="alert" hidden>
            <c:choose>
                <c:when test="${empty book.contentType}">
                    Для данной книги не загружен файл
                </c:when>
                <c:otherwise>
                    Содержимое файла нельзя отобразить в браузере. Но вы можете скачать его
                </c:otherwise>
            </c:choose>
        </div>
        <script>
            showInfoMessage(10);
        </script>
    </div>
</c:if>

<!-- Main box (search) - book info-->
<div id="main-box">
    <div class="col-sm-4">
        <div class="product-image-large">
            <img class="img-rounded" src="${showImg}?bookId=${book.id}" alt="Item Name">
        </div>
    </div>
    <!-- End Product Image & Available Colors -->
    <!-- Product Summary & Options -->
    <div class="col-sm-7 product-details">
        <h4>${book.name}</h4>
        <div class="price">
            <c:choose>
                <c:when test="${book.price==0.0}">
                    БЕСПЛАТНО
                </c:when>
                <c:otherwise>
                    ${book.price} р.
                </c:otherwise>
            </c:choose>
        </div>
        <h5>Краткое описание</h5>
        <p>
            ${book.descr}
        </p>
        <h5>Действия</h5>
        <!-- Operations -->
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group-lg bottom-indent" role="group" aria-label="First group">
                <c:if test="${book.price!=0.0}">
                    <a href="#" onclick="confirmAddToCart(${book.id}, '${book.name}')"
                       class="btn item-actions"
                       role="button" data-placement="top" data-toggle="popover" data-content="В корзину">
                        <i class="glyphicon glyphicon-shopping-cart icon-white"></i></a>
                </c:if>
                <a href="#" onclick="confirmShowBookContent(${book.id}, '${book.name}', ${book.price})"
                   class="btn item-actions"
                   role="button" data-placement="top" data-toggle="popover" data-content="Читать">
                    <i class="glyphicon glyphicon-eye-open icon-white"></i></a>
                <a href="#" onclick="confirmDownloadBookContent(${book.id}, '${book.name}', ${book.price})"
                   class="btn item-actions"
                   role="button" data-placement="top" data-toggle="popover" data-content="Скачать">
                    <i class="glyphicon glyphicon-download icon-white"></i></a>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <a href="${editBook}?bookId=${book.id}" class="btn admin-button item-actions"
                       role="button" data-placement="top" data-toggle="popover" data-content="Изменить">
                        <i class="glyphicon glyphicon-pencil icon-white"></i></a>
                    <a href="#" class="btn admin-button item-actions"
                       onclick="confirmDeleteBook(${book.id}, '${book.name}')"
                       role="button" data-placement="top" data-toggle="popover" data-content="Удалить">
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
                        <c:if test="${not empty book.contentType}">
                            <tr>
                                <td>Расширение файла</td>
                                <td>${book.fileExtension}</td>
                            </tr>
                            <tr>
                                <td>Размер файла</td>
                                <td>${book.fileSize}</td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>Рейтинг</td>
                            <td>${book.rating}</td>
                        </tr>
                        <tr>
                            <td>Количестов голосов</td>
                            <td>${book.voteCount}</td>
                        </tr>
                        <tr>
                            <td>Количестов просмотров</td>
                            <td>${book.views}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<tiles:insertAttribute name="pagination"/>

<script>
    popovers();
</script>