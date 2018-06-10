<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <title><tiles:getAsString name="title"/></title>
    <link rel="shortcut icon" href="${contextPathToRes}img/favicon.ico" type="image/x-icon"/>
    <%--bootstrap 3--%>
    <link rel="stylesheet" href="${contextPathToRes}css/bootstrap.min.css">
    <%--bootstrap 4--%>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="${contextPathToRes}css/icomoon-social.css">
    <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' type='text/css'>
    <link rel="stylesheet" href="${contextPathToRes}css/leaflet.css"/>
    <link rel="stylesheet" href="${contextPathToRes}css/main.css">
    <link rel="stylesheet" href="${contextPathToRes}css/aptech-lib-styles.css">
    <link rel="stylesheet" href="${contextPathToRes}css/datepicker.min.css">
    <script src="${contextPathToRes}js/jquery-3.3.1.min.js"></script>
    <%--bootstrap 3--%>
    <script src="${contextPathToRes}js/bootstrap.min.js"></script>
    <%--bootstrap 4--%>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>--%>
    <%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>--%>
    <script src="${contextPathToRes}js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src="${contextPathToRes}js/library-common.js"></script>
    <script src="${contextPathToRes}js/library-books.js"></script>
    <script src="${contextPathToRes}js/library-authors.js"></script>
    <script src="${contextPathToRes}js/library-users.js"></script>
    <script src="${contextPathToRes}js/jquery.simplePagination.js"></script>
    <script src="${contextPathToRes}js/datepicker.min.js"></script>
</head>

<body>
    <tiles:insertAttribute name="body"/>
    <!-- Javascripts -->
    <%--<script src="http://cdn.leafletjs.com/leaflet-0.5.1/leaflet.js"></script>--%>
    <%--<script src="${contextPathToRes}js/jquery.fitvids.js"></script>--%>
    <%--<script src="${contextPathToRes}js/jquery.sequence-min.js"></script>--%>
    <%--<script src="${contextPathToRes}js/jquery.bxslider.js"></script>--%>
    <%--<script src="${contextPathToRes}js/main-menu.js"></script>--%>
    <%--<script src="${contextPathToRes}js/template.js"></script>--%>

    <!-- Modal alert -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Предупреждение</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal confirm-->
    <div class="modal fade" id="exampleModalConfirm" tabindex="-1" role="dialog" aria-labelledby="exampleModalConfirmLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalConfirmLabel">Подтвердите</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" >Да</button>
                    <button type="button" class="btn btn-default" >Отмена</button>
                </div>
            </div>
        </div>
    </div>
</body>

</html>