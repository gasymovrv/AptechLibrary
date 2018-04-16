<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Letters -->
<div class="col-sm-12">
    <form>
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group-sm" role="group" aria-label="First group">
                <c:forEach var="char" items="${letters}">
                    <button type="button" class="btn btn-sm">${char}</button>
                </c:forEach>
            </div>
        </div>
    </form>
</div>
