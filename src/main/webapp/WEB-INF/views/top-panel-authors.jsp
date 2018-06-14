<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--критерии поиска отправляются через модель в MVC--%>

<!-- Top-panel - Search -->
<div class="section section-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">

                <div class="row">
                    <form:form modelAttribute="criteriaAuthors" method="get">
                        <div class="form-group col-xs-3">
                            <h1><tiles:getAsString name="top-panel-title"/></h1>
                        </div>
                        <div class="form-group col-xs-4">
                            <form:input path="text" class="form-control"
                                        placeholder="Введите текст для выполнения поиска"/>
                        </div>
                        <div class="form-group col-xs-1">
                            <button id="top-panel-search-button" type="submit" class="btn btn-md">Поиск</button>
                        </div>
                    </form:form>
                </div>

                <div class="row">
                    <div class="form-group col-xs-3">
                        <h1>Сортировка</h1>
                    </div>
                    <div class="form-group col-xs-3">
                        <select id="top-panel-form-select-sort" class="form-control">
                            <c:set var="sortType" value="${sessionScope.sortType}"/>
                            <c:forEach var="st" items="${sortTypeList}">
                                <c:choose>
                                    <c:when test="${sortType eq st}">
                                        <option selected value="${st}">${st.text}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${st}">${st.text}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
