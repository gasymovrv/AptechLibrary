<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--критерии поиска отправляются через модель в MVC--%>

<!-- Top-panel - Search -->
<div class="section section-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-12">

                <form:form modelAttribute="criteriaAuthors" method="get" >
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <h1><tiles:getAsString name="top-panel-title"/></h1>
                        </div>
                        <div class="form-group col-md-6">
                            <form:input path="text" class="form-control" placeholder="Введите текст для выполнения поиска"/>
                        </div>
                        <div class="form-group col-md-1">
                            <button id="top-panel-search-button" type="submit" class="btn btn-md">Поиск</button>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <h1>Сортировка</h1>
                        </div>
                        <div class="form-group col-md-3">
                            <select class="form-control" name="sortType">
                                <c:forEach var="st" items="${sortTypeList}">
                                    <c:choose>
                                        <c:when test="${not empty criteriaAuthors.sortType and criteriaAuthors.sortType eq st}">
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
                </form:form>

            </div>
        </div>
    </div>
</div>
