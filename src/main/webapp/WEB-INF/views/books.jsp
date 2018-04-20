<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box -->
<div class="col-sm-9">
    <div class="row">
        <div class="col-sm-8" id="books-count"><h3>Найдено книг: ${bookList.size()}</h3></div>
    </div>
    <div id="box-with-rows-for-books">
        <c:set var="i" value="0" scope="page"/>
        <c:forEach begin="1" end="${bookList.size()}" step="3">
            <div class="row">
                <c:forEach var="j" begin="0" end="2">
                    <c:if test="${(i+j)<bookList.size()}">
                        <div class="col-sm-4">
                            <!-- Product -->
                            <div class="shop-item">
                                <!-- Product Image -->
                                <div class="shop-item-image">
                                    <a href="${info}?${bookId}=${bookList.get(i+j).id}"><img
                                            src="${contextPathToRes}img/product1.jpg"
                                            alt="Item Name"></a>
                                </div>
                                <!-- Product Title -->
                                <div class="title">
                                    <h3>
                                        <a href="${info}?${bookId}=${bookList.get(i+j).id}">${bookList.get(i+j).name}</a>
                                    </h3>
                                </div>
                                <!-- Product author -->
                                <div class="title">
                                    <h3><a href="#">${bookList.get(i+j).author.fio}</a></h3>
                                </div>
                                <div class="title">
                                    <h3>Издательство: <a href="#">${bookList.get(i+j).publisher.name}</a></h3>
                                </div>
                                <div class="title">
                                    <h3>Год издания: ${bookList.get(i+j).publishYear}</h3>
                                </div>
                                <!-- Product Price-->
                                <div class="price">
                                    $999.99
                                </div>
                                <!-- Operations -->
                                <div class="actions">
                                    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                        <div class="btn-group-lg bottom-indent" role="group" aria-label="First group">
                                            <a href="${info}?${bookId}=${bookList.get(i+j).id}" class="btn"
                                               role="button"
                                               data-toggle="tooltip"
                                               data-placement="top" title="В корзину"><i
                                                    class="glyphicon glyphicon-shopping-cart icon-white"></i></a>

                                            <a href="${info}?${bookId}=${bookList.get(i+j).id}" class="btn"
                                               role="button"
                                               data-toggle="tooltip"
                                               data-placement="top" title="Читать"><i
                                                    class="glyphicon glyphicon-eye-open icon-white"></i></a>
                                        </div>
                                    </div>
                                    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                                        <div class="btn-group-lg" role="group" aria-label="First group">
                                            <a href="${info}?${bookId}=${bookList.get(i+j).id}" class="btn"
                                               role="button"
                                               data-toggle="tooltip"
                                               data-placement="top" title="Изменить"><i
                                                    class="glyphicon glyphicon-pencil icon-white"></i></a>
                                            <a href="${info}?${bookId}=${bookList.get(i+j).id}" class="btn"
                                               role="button"
                                               data-toggle="tooltip"
                                               data-placement="top" title="Удалить"><i
                                                    class="glyphicon glyphicon-trash icon-white"></i></a>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <!-- End Product -->
                        </div>
                    </c:if>
                </c:forEach>
                <c:set var="i" value="${i+3}" scope="page"/>
            </div>
        </c:forEach>
    </div>
    <div class="pagination-wrapper ">
        <ul class="pagination pagination-md">
            <li class="disabled"><a href="#">Previous</a></li>
            <li class="active"><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">6</a></li>
            <li><a href="#">7</a></li>
            <li><a href="#">8</a></li>
            <li><a href="#">9</a></li>
            <li><a href="#">10</a></li>
            <li><a href="#">Next</a></li>
        </ul>
    </div>
</div>