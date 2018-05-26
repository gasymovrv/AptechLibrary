<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <title><tiles:getAsString name="title"/></title>
    <link href="${contextPathToRes}img/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <link rel="stylesheet" href="${contextPathToRes}css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPathToRes}css/icomoon-social.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${contextPathToRes}css/leaflet.css"/>
    <link rel="stylesheet" href="${contextPathToRes}css/main.css">
    <link rel="stylesheet" href="${contextPathToRes}css/aptech-lib-styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${contextPathToRes}js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="${contextPathToRes}js/library-common.js"></script>
    <script src="${contextPathToRes}js/library-books.js"></script>
    <script src="${contextPathToRes}js/library-authors.js"></script>
    <script src="${contextPathToRes}js/library-users.js"></script>
    <script src="${contextPathToRes}js/jquery.simplePagination.js"></script>
</head>

<body>
    <tiles:insertAttribute name="body"/>
    <!-- Javascripts -->
    <script src="${contextPathToRes}js/bootstrap.min.js"></script>
    <script src="http://cdn.leafletjs.com/leaflet-0.5.1/leaflet.js"></script>
    <script src="${contextPathToRes}js/jquery.fitvids.js"></script>
    <script src="${contextPathToRes}js/jquery.sequence-min.js"></script>
    <script src="${contextPathToRes}js/jquery.bxslider.js"></script>
    <script src="${contextPathToRes}js/main-menu.js"></script>
    <script src="${contextPathToRes}js/template.js"></script>
</body>

</html>