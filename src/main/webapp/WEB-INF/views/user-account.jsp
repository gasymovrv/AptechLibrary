<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (non search) - user-account-->
<div id="main-box">

    <!-- Full Description & Specification -->
    <div class="col-sm-12">
        <c:if test="${not empty enoughMoney and not enoughMoney}">
            <div class="alert alert-danger" role="alert">
                Недостаточно средств для покупки!
            </div>
        </c:if>
        <c:if test="${not empty successBuy}">
            <c:choose>
                <c:when test="${successBuy}">
                    <div class="alert alert-success" role="alert">
                        Покупка успешно совершена!
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" role="alert">
                        Произошла ошибка во время покупки!
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
        <div class="tabbable">
            <!-- Tabs -->
            <ul class="nav nav-tabs product-details-nav">
                <li <c:if test="${activeTab eq 'info'}">class="active"</c:if>><a href="#tab1" data-toggle="tab">Личные данные</a></li>
                <li><a href="#tab2" data-toggle="tab">Заказы</a></li>
                <li <c:if test="${activeTab eq 'cart'}">class="active"</c:if>><a href="#tab3" data-toggle="tab">Корзина</a></li>
            </ul>

            <div class="tab-content product-detail-info orders-table">

                <!-- Tab Content (person) -->
                <div class="tab-pane <c:if test="${activeTab eq 'info'}">active</c:if>" id="tab1">
                    <h4>Личная информация</h4>
                    <table>
                        <colgroup>
                            <col style="width: 50%"/>
                            <col style="width: 30%"/>
                            <col style="width: 20%"/>
                        </colgroup>
                        <tr>
                            <td>Ваш логин</td>
                            <td>${user.username}</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Средств на счету</td>
                            <td id="user-money">${user.money} р.</td>
                            <td>
                                <a href="${accountPage}?addMoney=true" id="get-money" class="btn item-actions" role="button">
                                    Пополнить
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Количество заказов</td>
                            <td>${user.orders.size()}</td>
                            <td></td>
                        </tr>
                    </table>
                    <h4>Просмотры книг</h4>
                    <table>
                        <colgroup>
                            <col style="width: 80%"/>
                            <col style="width: 20%"/>
                        </colgroup>
                        <c:forEach var="uv" items="${usersViews}">
                            <tr>
                                <td><a href="${bookInfo}?bookId=${uv.book.id}">${uv.book.name}</a></td>
                                <td>${uv.views}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <!-- Tab Content (orders) -->
                <div class="tab-pane" id="tab2">
                    <c:choose>
                        <c:when test="${empty user.orders}">
                            <h4>Заказы отсутствуют</h4>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <c:set var="sumOrder" value="0"/>
                                <c:forEach var="order" items="${user.sortedOrders}" varStatus="loop">
                                    <tr>
                                        <td>
                                            <h4>№${(user.orders.size()+1)-loop.count}</h4>
                                            <table>
                                                <colgroup>
                                                    <col style="width: 10%"/>
                                                    <col style="width: 70%"/>
                                                    <col style="width: 20%"/>
                                                </colgroup>
                                                <c:forEach var="b" items="${order.books}" varStatus="loop">
                                                    <tr>
                                                        <td>${loop.count}</td>
                                                        <td><a href="${bookInfo}?bookId=${b.id}">${b.name}</a></td>
                                                        <td>${b.price} р.</td>
                                                    </tr>
                                                    <c:set var="sumOrder" value="${sumOrder+b.price}"/>
                                                </c:forEach>
                                                <tr>
                                                    <td>Дата заказа</td>
                                                    <td></td>
                                                    <td>${order.formattedCreated}</td>
                                                </tr>
                                                <tr>
                                                    <td>Сумма</td>
                                                    <td></td>
                                                    <td>${sumOrder}</td>
                                                </tr>
                                                <c:set var="sumOrder" value="0"/>
                                            </table>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Tab Content (cart) -->
                <div class="tab-pane <c:if test="${activeTab eq 'cart'}">active</c:if>" id="tab3">
                    <c:choose>
                        <c:when test="${empty user.cart.books}">
                            <h4>Корзина пуста</h4>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <colgroup>
                                    <col style="width: 10%">
                                    <col style="width: 70%">
                                    <col style="width: 10%">
                                    <col style="width: 10%">
                                </colgroup>
                                <tr>
                                    <th>№</th>
                                    <th>Название</th>
                                    <th>Цена</th>
                                    <th></th>
                                </tr>
                                <c:set var="sumCart" value="0"/>
                                <c:forEach var="b" items="${user.cart.books}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}</td>
                                        <td><a href="${bookInfo}?bookId=${b.id}">${b.name}</a></td>
                                        <td>${b.price} р.</td>
                                        <td>
                                            <div class="btn-group-sm bottom-indent" role="group">
                                                <a href="${accountPage}?delBookFromCart=${b.id}&tab=cart" class="btn item-actions"
                                                   role="button" data-placement="top" data-toggle="popover"
                                                   data-content="Удалить">
                                                    <i class="glyphicon glyphicon-trash icon-white"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <c:set var="sumCart" value="${sumCart+b.price}"/>
                                </c:forEach>
                                <tr>
                                    <td>Сумма</td>
                                    <td></td>
                                    <td>${sumCart} р.</td>
                                    <td></td>
                                </tr>
                            </table>
                                <div class="btn-group-sm bottom-indent" role="group">
                                    <a href="${accountPage}?buy=true&tab=cart" class="btn item-actions"
                                       role="button">Купить</a>
                                    <a href="${accountPage}?clearCart=true&tab=cart" class="btn item-actions"
                                       role="button">Очистить корзину</a>
                                </div>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
    </div>
</div>
<script>
    popovers();
</script>