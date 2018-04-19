<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Letters -->
<div class="col-sm-12">
    <form:form modelAttribute="criteria" action="${searchResult}" method="get">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group-sm" role="group" aria-label="First group">
                <c:forEach var="ch" items="${letters}">
                    <input value="${ch}" name="letter" type="submit" role="button" class="btn btn-sm"/>
                </c:forEach>
            </div>
        </div>
    </form:form>
</div>
