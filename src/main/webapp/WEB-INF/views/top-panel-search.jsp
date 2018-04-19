<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Search -->
<div class="col-md-12">
    <form:form modelAttribute="criteria" action="${searchResult}" method="get">
        <div class="form-row">
            <div class="form-group col-md-2">
                <h1>Поиск книг</h1>
            </div>
            <div class="form-group col-md-6">
                <form:input id="searchField"
                            path="text"
                            type="text"
                            class="form-control"
                            placeholder="Введите текст для выполнения поиска"/>
            </div>
            <div class="form-group col-md-3">
                <form:select id="selectionField"
                             path="searchType"
                             class="form-control"
                >
                    <form:options items="${searchTypeList}" itemLabel="text"/>
                </form:select>
            </div>
            <div class="form-group col-md-1">
                <button type="submit" class="btn btn-md">Поиск</button>
            </div>
        </div>
    </form:form>
</div>