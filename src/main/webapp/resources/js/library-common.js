$(document).ready(function () {
    itemsOnPageValidation();
});

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
    $.ajax({
        type: 'POST',//тип запроса
        contentType: 'application/json', //отправляемый тип
        url: getContextPath() + '/saveFoundResultText?foundResultText=' + foundResultText,//url адрес обработчика
        async: false
    });
}

function itemsOnPageValidation() {
    $(".pagination-wrapper input").bind('keyup mouseup', function () {
        if ($(this).val() < 1) {
            $(this).val(1);
        }
    });
}

//метод для получения контекстного пути '/aptech-library'
//!!! работает только с корневым именем, не содержащим '/'
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}