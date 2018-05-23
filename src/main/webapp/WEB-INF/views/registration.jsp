<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="ru.aptech.library.entities.User"--%>
<div class="center-block col-sm-5" style="float: none;">
    <div class="basic-login">
        <form:form name='form_login' action="${contextPath}users/registrationAction" method='POST' modelAttribute="user">
            <div class="form-group">
                <c:if test="${not empty error}"><p class="alert alert-danger">${error}</p></c:if>
                <label for="username"><i class="icon-user"></i> <b>Имя или email</b></label>
                <form:input class="form-control" id="username" path='username'/>
            </div>
            <div class="form-group">
                <label for="password"><i class="icon-lock"></i> <b>Пароль</b></label>
                <form:password class="form-control" id="password" path='password'/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn pull-right">Зарегистрироваться</button>
                <div class="clearfix"></div>
            </div>
        </form:form>
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