<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Search -->
<div class="col-md-12">
    <form id="top-panel-form">
        <div class="form-row">
            <div class="form-group col-md-2">
                <h1>Поиск книг</h1>
            </div>
            <div class="form-group col-md-6">
                <input type="text" id="top-panel-form-text" class="form-control" placeholder="Введите текст для выполнения поиска">
            </div>
            <div class="form-group col-md-3">
                <select id="top-panel-form-select" class="form-control" name="searchType">
                    <c:forEach var="st" items="${searchTypeList}">
                        <option value="${st}">${st.text}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-1">
                <button id="top-panel-search-button" type="submit" class="btn btn-md">Поиск</button>
            </div>
        </div>
    </form>
</div>