<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (non search) - Login-->
<div class="center-block col-sm-5" style="float: none;">
    <div class="basic-login">
        <form name='form_login' role="form" role="form" action="<c:url value='/login' />" method='POST'>
            <c:if test="${not empty isAdded and isAdded}"><p class="alert alert-success">Пользователь ${username} успешно зарегистрирован!</p></c:if>
            <c:if test="${not empty error}"><p class="alert alert-danger">${error}</p></c:if>
            <div class="form-group">
                <label for="login-username"><i class="icon-user"></i> <b>Имя или email</b></label>
                <input class="form-control" id="login-username" type="text" name='user_login' <c:if test="${not empty username}">value="${username}"</c:if>>
            </div>
            <div class="form-group">
                <label for="login-password"><i class="icon-lock"></i> <b>Пароль</b></label>
                <input class="form-control" id="login-password" name='password_login' type="password">
            </div>
            <div class="form-group">
                <label class="checkbox">
                    <input type="checkbox" name="remember-me"> Запомнить меня
                </label>
                <a href="#" class="forgot-password">Забыли пароль?</a>
                <button type="submit" class="btn pull-right">Войти</button>
                <div class="clearfix"></div>
            </div>
        </form>
        <div class="not-member">
            <p>Еще нет аккаунта? <a href="${contextPath}users/registrationView">Зарегистрируйтесь здесь</a></p>
        </div>
    </div>
</div>
<%--<div class="col-sm-7 social-login">--%>
    <%--<p>Или войдите через социальные сети</p>--%>
    <%--<div class="social-login-buttons">--%>
        <%--<a href="#" class="btn-facebook-login">Войти через Facebook</a>--%>
        <%--<a href="#" class="btn-twitter-login">Войти через Twitter</a>--%>
    <%--</div>--%>
    <%--<div class="clearfix"></div>--%>
    <%--<div class="not-member">--%>
        <%--<p>Еще нет аккаунта? <a href="#">Зарегистрируйтесь здесь</a></p>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<script>--%>
    <%--$(window).resize(fixedFooter);--%>
    <%--$(document).ready(fixedFooter);--%>
<%--</script>--%>