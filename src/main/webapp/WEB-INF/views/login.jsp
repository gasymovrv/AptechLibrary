<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-sm-5">
    <div class="basic-login">
        <form name='form_login' role="form" role="form" action="<c:url value='/login' />" method='POST'>
            <div class="form-group">
                <c:if test="${not empty error}"><p class="alert alert-danger">${error}</p></c:if>
                <label for="login-username"><i class="icon-user"></i> <b>Имя или email</b></label>
                <input class="form-control" id="login-username" type="text" name='user_login'>
            </div>
            <div class="form-group">
                <label for="login-password"><i class="icon-lock"></i> <b>Пароль</b></label>
                <input class="form-control" id="login-password" name='password_login' type="password">
            </div>
            <div class="form-group">
                <label class="checkbox">
                    <input type="checkbox"> Запомнить меня
                </label>
                <a href="#" class="forgot-password">Забыли пароль?</a>
                <button type="submit" class="btn pull-right">Войти</button>
                <div class="clearfix"></div>
            </div>
        </form>
    </div>
</div>
<div class="col-sm-7 social-login">
    <p>Или войдите через социальные сети</p>
    <div class="social-login-buttons">
        <a href="#" class="btn-facebook-login">Войти через Facebook</a>
        <a href="#" class="btn-twitter-login">Войти через Twitter</a>
    </div>
    <div class="clearfix"></div>
    <div class="not-member">
        <p>Еще нет аккаунта? <a href="#">Зарегестрируйтесь здесь</a></p>
    </div>
</div>