<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="top-panel"/>
<div class="section">
    <div class="container">
        <div class="row">
            <tiles:insertAttribute name="main" ignore="true"/>
        </div>
    </div>
</div>
<tiles:insertAttribute name="footer"/>