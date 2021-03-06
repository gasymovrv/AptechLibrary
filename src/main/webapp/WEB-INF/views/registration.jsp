<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="ru.aptech.library.entities.User"--%>

<!-- Main box (non search) - Registration-->
<div class="center-block col-sm-5" style="float: none;">
    <div class="basic-login">
        <form:form name='form_login' action="${contextPath}users/registrationAction" method='POST' modelAttribute="user">
            <c:if test="${not empty isAdded and not isAdded}"><p class="alert alert-danger">Ошибка при добавлении пользователя ${username}! Попробуйте другое имя</p></c:if>
            <div class="form-group">
                <p hidden="hidden" id="box-danger" class="alert alert-danger"></p>
                <label for="username"><b title="Обязательно для заполнения">Имя или email<sup style="color: red">*</sup></b></label>
                <form:input class="form-control user-validation" id="username" path='username'/>
            </div>
            <div class="form-group">
                <label for="password1"><b title="Обязательно для заполнения">Пароль<sup style="color: red">*</sup></b></label>
                <input id="password1" class="form-control user-validation" type="password">
            </div>
            <div class="form-group">
                <label for="password2"><b title="Обязательно для заполнения">Подтвердите пароль<sup style="color: red">*</sup></b></label>
                <form:password class="form-control user-validation" id="password2" path='password'/>
            </div>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <div class="form-group">
                    <label for="role">Привелегии</label>
                    <select class="form-control" id="role" class="form-control" name="userRole" multiple="multiple">
                        <c:forEach var="ur" items="${userRoles}">
                            <option value="${ur}">${ur.text}</option>
                        </c:forEach>
                    </select>
                </div>
            </security:authorize>
            <div class="form-group">
                <span id="submit-new-user" class="d-inline-block pull-right" data-placement="top" data-toggle="popover">
                    <button disabled="disabled" type="submit" class="btn">
                        Зарегистрироваться
                    </button>
                </span>
                <div class="clearfix"></div>
            </div>
        </form:form>
    </div>
</div>
<script>
    userValidation();
</script>
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