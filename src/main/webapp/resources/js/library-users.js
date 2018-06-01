function isAdmin() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        url: getContextPath() + '/users/isAdmin',//url адрес обработчика
        async: false,
        success: function (data) {//принимаемое от сервера (Response)
            result = data;
        },
        error: function () {
            alert('Ошибка в isAdmin');
        }
    });
    return result;
}

function isUser() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        url: getContextPath() + '/users/isUser',//url адрес обработчика
        async: false,
        success: function (data) {//принимаемое от сервера (Response)
            result = data;
        },
        error: function () {
            alert('Ошибка в isUser');
        }
    });
    return result;
}

function userValidation() {
    let spanButton = $('#submit-new-user');
    spanButton.attr('data-content', "Сначала заполните обязательные поля");

    spanButton.hover(
        function () {
            $(this).popover('show');
        },
        function () {
            $(this).popover('hide');
        }
    );

    $("#username, #password1, #password2").each(function () {
        $(this).keyup(function () {
            let boxDanger = $('#box-danger');
            let username = $('#username').val();
            let pass1 = $('#password1').val();
            let pass2 = $('#password2').val();

            if (!pass1 || !pass2 || !username) {
                $('#submit-new-user button').prop('disabled', true);
                $(spanButton).popover('enable');
                spanButton.attr('data-content', 'Необходимо заполнить обязательные поля');
                boxDanger.empty();
                boxDanger.hide();
            } else if ((pass1 && pass2) && (pass1 !== pass2)) {
                $('#submit-new-user button').prop('disabled', true);
                $(spanButton).popover('enable');
                spanButton.attr('data-content', 'Пароли должны совпадать');
                boxDanger.text('Пароли не совпадают');
                boxDanger.show();
            } else {
                $('#submit-new-user button').prop('disabled', false);
                $(spanButton).popover('disable');
                boxDanger.empty();
                boxDanger.hide();
            }
        });
    });
}