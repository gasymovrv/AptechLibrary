<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Letters -->
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div id="letters-form">
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group-sm" role="group" aria-label="First group">
                        <c:forEach var="ch" items="${letters}">
                            <button id="${ch}" class="btn btn-sm">${ch}</button>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
