$(document).ready(function () {
    // testGetBooks();
    searchByText();
    searchByGenre();
    searchByLetter()
});

// function testGetBooks() {
//     $('#testButton').click(function () {
//         $.ajax({
//             type: 'GET',
//             contentType: 'application/json',
//             dataType: 'json',
//             url: "home/searchResult",
//             success: function (data) {
//                 createHtmlWithBookList(data, ": ");
//             },
//             error: function (response) { // Данные не отправлены
//                 alert('Ошибка. Данные не отправлены.');
//             }
//         });
//     });
// }

function searchByText() {
    $('#top-panel-form').submit(function (event) {
        event.preventDefault();
        let object = {'text': $('#top-panel-form-text').val(), 'searchType': $('#top-panel-form-select').val()};
        $.ajax({
            type: 'POST',//тип запроса
            contentType: 'application/json', //отправляемый тип
            dataType: 'json',//принимаемый тип (из контроллера)
            data: JSON.stringify(object),//отправляемое отсюда (Request)
            url: 'home/searchByCriteria',//url адрес обработчика
            success: function (data) {//принимаемое от сервера (Response)
                console.log("SUCCESS: ", data);
                createHtmlWithBookList(data, " по Вашему запросу: ");
            },
            error: function (e) { // Данные не отправлены
                console.log("ERROR: ", e);
                alert('Ошибка. Данные не отправлены.');
            },
            done: function () {
                console.log("DONE");
            }
        });
    });
}

function searchByGenre() {
    $('#sidebar-genres li a').click(function () {
        let object = {'genreId': $(this).prop('id')};
        let genreName = $(this).text();
        $.ajax({
            type: 'POST',//тип запроса
            contentType: 'application/json', //отправляемый тип
            dataType: 'json',//принимаемый тип (из контроллера)
            data: JSON.stringify(object),//отправляемое отсюда (Request)
            url: 'home/searchByCriteria',//url адрес обработчика
            success: function (data) {//принимаемое от сервера (Response)
                console.log("SUCCESS: ", data);
                createHtmlWithBookList(data, " жанра '" + genreName + "': ");
            },
            error: function (e) { // Данные не отправлены
                console.log("ERROR: ", e);
                alert('Ошибка. Данные не отправлены.');
            },
            done: function () {
                console.log("DONE");
            }
        });
    });
}

function searchByLetter() {
    $('#letters-form button').click(function () {
        let object = {'letter': $(this).prop('id')};
        $.ajax({
            type: 'POST',//тип запроса
            contentType: 'application/json', //отправляемый тип
            dataType: 'json',//принимаемый тип (из контроллера)
            data: JSON.stringify(object),//отправляемое отсюда (Request)
            url: 'home/searchByCriteria',//url адрес обработчика
            success: function (data) {//принимаемое от сервера (Response)
                console.log("SUCCESS: ", data);
                createHtmlWithBookList(data, ", начинающихся на '" + object['letter'] + "': ");
            },
            error: function (e) { // Данные не отправлены
                console.log("ERROR: ", e);
                alert('Ошибка. Данные не отправлены.');
            },
            done: function () {
                console.log("DONE");
            }
        });
    });
}


function createHtmlWithBookList(bookList, label) {
    let rowId = 'row-with-books_0';
    $('#box-with-rows-for-books').empty().append('<div id="' + rowId + '" class="row"></div>');
    $('#books-count').empty().append('<h3>Найдено книг' + label + bookList.length + '</h3>');
    let j = 0;
    for (let i = 0; i < bookList.length; i++) {
        let html =
            '<div class="col-sm-4">\n' +
            '    <div class="shop-item">\n' +
            '        <div class="shop-item-image">\n' +
            '            <a href="/info?bookId=' + bookList[i].id + '"><img\n' +
            '                    src="resources/img/product1.jpg"\n' +
            '                    alt="Item Name"></a>\n' +
            '        </div>\n' +
            '        <div class="title">\n' +
            '            <h3><a href="/info?bookId=' + bookList[i].id + '">' + bookList[i].name + '</a></h3>\n' +
            '        </div>\n' +
            '        <div class="title">\n' +
            '            <h3><a href="#">' + bookList[i].author.fio + '</a></h3>\n' +
            '        </div>\n' +
            '        <div class="title">\n' +
            '            <h3>Издательство: <a href="#">' + bookList[i].publisher.name + '</a></h3>\n' +
            '        </div>\n' +
            '        <div class="title">\n' +
            '            <h3>Год издания: ' + bookList[i].publishYear + '</h3>\n' +
            '        </div>\n' +
            '        <div class="price">\n' +
            '            $999.99\n' +
            '        </div>\n' +
            '        <div class="actions">\n' +
            '            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">\n' +
            '                <div class="btn-group-lg bottom-indent" role="group" aria-label="First group">\n' +
            '                    <a href="/info?bookId=' + bookList[i].id + '" class="btn" role="button"\n' +
            '                       data-toggle="tooltip"\n' +
            '                       data-placement="top" title="В корзину"><i\n' +
            '                            class="glyphicon glyphicon-shopping-cart icon-white"></i></a>\n' +
            '\n' +
            '                    <a href="/info?bookId=' + bookList[i].id + '" class="btn" role="button"\n' +
            '                       data-toggle="tooltip"\n' +
            '                       data-placement="top" title="Читать"><i\n' +
            '                            class="glyphicon glyphicon-eye-open icon-white"></i></a>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">\n' +
            '                <div class="btn-group-lg" role="group" aria-label="First group">\n' +
            '                    <a href="/info?bookId=' + bookList[i].id + '" class="btn" role="button"\n' +
            '                       data-toggle="tooltip"\n' +
            '                       data-placement="top" title="Изменить"><i\n' +
            '                            class="glyphicon glyphicon-pencil icon-white"></i></a>\n' +
            '                    <a href="/info?bookId=' + bookList[i].id + '" class="btn" role="button"\n' +
            '                       data-toggle="tooltip"\n' +
            '                       data-placement="top" title="Удалить"><i\n' +
            '                            class="glyphicon glyphicon-trash icon-white"></i></a>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>';
        $('#' + rowId).append(html);
        j++;
        if (j === 3) {
            rowId = 'row-with-books_' + i;
            $('#box-with-rows-for-books').append('<div id="' + rowId + '" class="row"></div>');
            j = 0;
        }
    }
}