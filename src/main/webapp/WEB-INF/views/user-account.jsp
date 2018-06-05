<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (non search) - user-account-->
<div id="main-box">

    <!-- Full Description & Specification -->
    <div class="col-sm-12">
        <div class="tabbable">
            <!-- Tabs -->
            <ul class="nav nav-tabs product-details-nav">
                <li <c:if test="${activeTab eq 'info'}">class="active"</c:if>><a href="#tab1" data-toggle="tab">Личные данные</a></li>
                <li <c:if test="${activeTab eq 'cart'}">class="active"</c:if>><a href="#tab2" data-toggle="tab">Корзина</a></li>
            </ul>
            <!-- Tab Content (Full Description) -->
            <div class="tab-content product-detail-info">
                <div class="tab-pane <c:if test="${activeTab eq 'info'}">active</c:if>" id="tab1">
                    <h4>Личная информация</h4>
                    <table>
                        <tr>
                            <td>Ваш логин</td>
                            <td>${user.username}</td>
                        </tr>
                    </table>
                    <h4>Просмотры книг</h4>
                    <table>
                        <c:forEach var="uv" items="${usersViews}">
                            <tr>
                                <td>${uv.book.name}</td>
                                <td>${uv.views}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <!-- Tab Content (Specification) -->
                <div class="tab-pane <c:if test="${activeTab eq 'cart'}">active</c:if>" id="tab2">
                    <c:choose>
                        <c:when test="${empty book}">
                            <h4>Корзина пуста</h4>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <td>Название</td>
                                    <td>${book.name}</td>
                                </tr>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>