$(document).ready(function () {
    itemsOnPageValidation();
    changeSortType();
});

//duration - длительность показывания инф.блока в секундах
function showInfoMessage(duration) {
    $(".info-message").show('slow');
    if(duration){
        setTimeout(function() { $(".info-message").hide('slow'); }, duration*1000);
    }
}

function getAlert(dialog) {
    $("#exampleModal .modal-body").text(dialog);
    $("#exampleModal").modal('show');
}

function getConfirm (dialog, callback) {
    $('#exampleModalConfirm').modal({show:true,
        backdrop:false,
        keyboard: false,
    });
    $("#exampleModalConfirm .modal-body").html(dialog);

    $("#exampleModalConfirm .btn-default").on('click', function () {
        $('#exampleModalConfirm').modal('hide');
        if(callback){callback(false);}
    });
    $("#exampleModalConfirm .btn-primary").on('click', function () {
        $('#exampleModalConfirm').modal('hide');
        if(callback){callback(true);}
    });
}

function  popovers() {
    let actions = $('.item-actions');
    actions.hover(
        function () {
            $(this).popover('show');
        },
        function () {
            $(this).popover('hide');
        }
    );
}

//сохраняем значение атрибута foundResultText в сессии
function saveFoundResultText(foundResultText) {
    let url = getContextPath() + '/saveFoundResultText';
    if(foundResultText){
        url = getContextPath() + '/saveFoundResultText?foundResultText=' + foundResultText;
    }
    $.ajax({
        type: 'POST',//тип запроса
        url: url,//url адрес обработчика
        async: false
    });
}

//получаем значение атрибута foundResultText из сессии
function getFoundResultText() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса)
        url: getContextPath() + '/getFoundResultText',//url адрес обработчика
        async: false,
        success: function (text) {//принимаемое от сервера (Response)
            result = text;
        },
        error: function () {
            getAlert('Ошибка в script getFoundResultText');
        }
    });
    return result;
}


//сохраняем значение атрибута sortType в сессии
function saveSortType(sortType) {
    $.ajax({
        type: 'POST',//тип запроса
        contentType: 'application/json', //отправляемый тип
        url: getContextPath() + '/saveSortType?sortType=' + sortType,//url адрес обработчика
        async: false
    });
}


function changeSortType() {
    $(document).on('change', '#top-panel-form-select-sort', function () {//то же что и метод click, но работает всегда
        let sortType = $(this).val();
        saveSortType(sortType);
    });
}

function itemsOnPageValidation() {
    $(".pagination-wrapper input").bind('keyup mouseup', function () {
        if ($(this).val() < 1) {
            $(this).val(1);
        }
    });
}

//заглушка для tomcat
function getContextPath() {
    // return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    return ""
}

function fixedFooter() {
    if ($(document).height() > $(window).height()){
        $("div.footer").addClass("navbar-fixed-bottom");
    } else {
        $("div.footer").removeClass("navbar-fixed-bottom");
    }
}