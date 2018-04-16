<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Search -->
<div class="col-md-12">
    <form>
        <div class="form-row">
            <div class="form-group col-md-2">
                <h1>Поиск книг</h1>
            </div>
            <div class="form-group col-md-6">
                <input type="text" class="form-control" id="searchField"
                       placeholder="Введите текст для выполнения поиска">
            </div>
            <div class="form-group col-md-3">
                <select id="selectionField" class="form-control">
                    <option selected>${selectedSearchType.text}</option>
                    <c:forEach var="st" items="${searchTypeList}">
                        <option>${st.text}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-1">
                <button class="btn btn-md" type="button">Поиск</button>
            </div>
        </div>
    </form>
</div>