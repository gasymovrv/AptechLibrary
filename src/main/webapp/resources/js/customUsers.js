function isAdmin() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        url: getContextPath() + '/user/isAdmin',//url адрес обработчика
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