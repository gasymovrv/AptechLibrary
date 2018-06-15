<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Navigation & Logo-->
<div class="mainmenu-wrapper">
    <div class="container">
        <div class="menuextras">
            <div class="extras">
                <ul>
                    <%--<li class="shopping-cart-items"><i class="glyphicon glyphicon-shopping-cart icon-white"></i> <a href="page-shopping-cart.html"><b>3 книги</b></a></li>--%>
                    <security:authorize access="isAnonymous()">
                        <li><i class="glyphicon glyphicon-user icon-white"></i> <a href="${contextPath}users/authorization">Авторизация</a></li>
                    </security:authorize>
                    <security:authorize access="isAuthenticated()">
                        <li><security:authentication property="principal.username" /></li>
                        <li><i class="glyphicon glyphicon-user icon-white"></i> <a href="${contextPath}users/account">Личный кабинет</a></li>
                        <li><i class="glyphicon glyphicon-log-out icon-white"></i> <a href="<c:url value='/logout' />">Выход</a></li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="${contextPath}users/registrationView">Добавить пользователя</a></li>
                    </security:authorize>
                </ul>
            </div>
        </div>
        <nav id="mainmenu" class="mainmenu">
            <ul>
                <li class="logo-wrapper"><a href="${home}"><img src="${contextPathToRes}img/BeSmart-logo.png" alt="Изображение не найдено"></a></li>
                <li>
                    <a href="${home}">Все книги</a>
                </li>
                <li>
                    <a href="${authorsPage}">Авторы</a>
                </li>
                <li>
                    <a href="${aboutUs}">О нас</a>
                </li>
            </ul>
        </nav>
    </div>
</div>