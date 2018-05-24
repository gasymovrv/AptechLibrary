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
            alert('Ошибка в getCurrentUserName');
        }
    });
    return result;
}

function userValidation() {
    $("#username, #password1, #password2").keyup(function () {
        let boxDanger = $('#box-danger');
        let username = $('#username').val();
        let pass1 = $('#password1').val();
        let pass2 = $('#password2').val();

        if (!pass1 || !pass2 || !username) {
            $('#submit-new-user button').prop('disabled', true);
            boxDanger.text('Необходимо заполнить все поля');
            boxDanger.show();
        } else if (pass1 !== pass2) {
            $('#submit-new-user button').prop('disabled', true);
            $('#submit-new-user').prop('title', 'Пароли должны совпадать');
            boxDanger.text('Пароли не совпадают');
            boxDanger.show();
        } else {
            $('#submit-new-user button').prop('disabled', false);
            $('#submit-new-user').prop('title', "");
            boxDanger.empty();
            boxDanger.hide();
        }
    });
}