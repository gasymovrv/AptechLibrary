<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="${contextPathToRes}css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPathToRes}css/icomoon-social.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${contextPathToRes}css/leaflet.css"/>
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${contextPathToRes}css/leaflet.ie.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${contextPathToRes}css/main.css">
    <link rel="stylesheet" href="${contextPathToRes}css/aptech-lib-styles.css">
    <%--<link rel="stylesheet" href="${contextPathToRes}css/simplePagination.css">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${contextPathToRes}js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="${contextPathToRes}js/ajax.js"></script>
    <script type="text/javascript" src="${contextPathToRes}js/jquery.simplePagination.js"></script>
</head>

<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to
    improve your experience.</p>
<![endif]-->

<tiles:insertAttribute name="header"/>
<div class="section section-breadcrumbs">
    <div class="container">
        <div class="row">
            <tiles:insertAttribute name="top-panel"/>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <tiles:insertAttribute name="letters"/>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <tiles:insertAttribute name="sidebar"/>
            <tiles:insertAttribute name="main"/>
        </div>
    </div>
</div>
<tiles:insertAttribute name="footer"/>

<!-- Javascripts -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="${contextPathToRes}js/jquery-1.9.1.min.js"><\/script>')</script>
<script src="${contextPathToRes}js/bootstrap.min.js"></script>
<script src="http://cdn.leafletjs.com/leaflet-0.5.1/leaflet.js"></script>
<script src="${contextPathToRes}js/jquery.fitvids.js"></script>
<script src="${contextPathToRes}js/jquery.sequence-min.js"></script>
<script src="${contextPathToRes}js/jquery.bxslider.js"></script>
<script src="${contextPathToRes}js/main-menu.js"></script>
<script src="${contextPathToRes}js/template.js"></script>

</body>
</html>