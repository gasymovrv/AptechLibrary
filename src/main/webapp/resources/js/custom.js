$(document).ready(function () {
    // testGetBooks();
    searchByText();
    searchByLetter();
    searchByGenre();
    searchByPublisher();
    searchByAuthor();
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
    $(document).on('submit', '#top-panel-form', function (event) {
        event.preventDefault();
        let criteria = {'text': $('#top-panel-form-text').val(), 'searchType': $('#top-panel-form-select').val()};
        getBooksByAjax(criteria, "Найдено книг по Вашему запросу: ");
    });
}


function searchByLetter() {
    $(document).on('click', '#letters-form button', function () {//то же что и метод click, но работает всегда
        let criteria = {'letter': $(this).prop('id')};
        getBooksByAjax(criteria, "Найдено книг, начинающихся на '" + criteria['letter'] + "': ");
    });
}


function searchByGenre() {
    $(document).on('click', 'a.genre-link', function () {//то же что и метод click, но работает всегда
        let criteria = {'genreId': $(this).prop('id')};
        let genreName = $(this).text();
        getBooksByAjax(criteria, "Найдено книг жанра '" + genreName + "': ");
    });
}

function searchByPublisher() {
    $(document).on('click', 'a.publisher-link', function () {//то же что и метод click, но работает всегда
        let criteria = {'publisherId': $(this).prop('id')};
        let publisherName = $(this).text();
        getBooksByAjax(criteria, "Найдено книг издательства '" + publisherName + "': ");
    });
}

function searchByAuthor() {
    $(document).on('click', 'a.author-link', function () {//то же что и метод click, но работает всегда
        let criteria = {'authorId': $(this).prop('id')};
        let authorName = $(this).text();
        getBooksByAjax(criteria, "Найдено книг автора " + authorName + ": ");
    });
}
//todo попробуй перенести в books.jsp
function getBooksByAjax(criteria, foundResultText) {
    booksSearchPagination(10, 5, foundResultText);

    // let quantityBooks = 0;
    // $.ajax({//получить количество книг
    //     type: 'POST',//тип запроса
    //     contentType: 'application/json', //отправляемый тип
    //     dataType: 'json',//принимаемый тип (из контроллера)
    //     data: JSON.stringify(criteria),//отправляемое отсюда (Request)
    //     url: getContextPath() + '/getQuantityBooks',//url адрес обработчика
    //     async: false,
    //     success: function (data) {
    //         $('.pagination').empty();
    //
    //     },
    //     error: function () {
    //         alert('Ошибка в getBooksByAjax()/ajax1');
    //     }
    // });
    // $.ajax({
    //     type: 'POST',//тип запроса
    //     contentType: 'application/json', //отправляемый тип
    //     dataType: 'json',//принимаемый тип (из контроллера)
    //     data: JSON.stringify(criteria),//отправляемое отсюда (Request)
    //     url: getContextPath() + '/home/searchByCriteria',//url адрес обработчика
    //     success: function (data) {//принимаемое от сервера (Response)
    //         destroyPaginator();
    //         createHtmlWithBookList(data, foundResultText);
    //         window.history.pushState(null, null, getContextPath() + '/home');
    //     },
    //     error: function (e) { // Данные не отправлены
    //         console.log("ERROR: ", e);
    //         alert('Ошибка. Данные не отправлены.');
    //     }
    // });
}

function createHtmlWithBookList(bookList, foundResultText) {
    let showPdf = getContextPath() + '/showBookContent?bookId=';
    let showImg = getContextPath() + '/showBookImage?bookId=';
    let info = getContextPath() + '/info?bookId=';

    let rowId = 'row-with-books_0';
    if (foundResultText) {//если параметр foundResultText передали
        $('#books-box').html(
            '   <div class="row">\n' +
            '       <div class="col-sm-8" id="books-count"><h3>' + foundResultText + bookList.length + '</h3></div>\n' +
            '   </div>\n' +
            '   <div id="box-with-rows-for-books" class="row">' +
            '       <div id="' + rowId + '" class="row"></div>' +
            '   </div>\n');
    } else {
        $('#books-box').html(
            '   <div id="box-with-rows-for-books" class="row">' +
            '       <div id="' + rowId + '" class="row"></div>' +
            '   </div>\n');
    }

    let j = 0;
    for (let i = 0; i < bookList.length; i++) {
        //вложенный аджакс для того чтобы заранее подготовить все обложки книг
        $.ajax({
            url: getContextPath() + "/showBookImage",
            type: "GET",
            data: {bookId: bookList[i].id},
            async: false
        }).always(function () {
            let html =
                '<div class="col-sm-4">\n' +
                '    <div class="shop-item">\n' +
                '        <div class="image">\n' +
                '            <a href="' + info + bookList[i].id + '"><img class="img-rounded"\n' +
                // '                    src="data:image/jpeg;base64,' + bookList[i].image + '"\n' + //вариант без вложенного аджакса и без контроллера
                '                    src="' + showImg + bookList[i].id + '"\n' +
                '                     alt="Изображение отсутствует"></a>\n' +
                '        </div>\n' +
                '        <div class="title">\n' +
                '            <h3><a href="' + info + bookList[i].id + '">' + bookList[i].name + '</a></h3>\n' +
                '        </div>\n' +
                '        <div class="title">\n' +
                '            <h3><a class="author-link" id="' + bookList[i].author.id + '" href="#">' + bookList[i].author.fio + '</a></h3>\n' +
                '        </div>\n' +
                '        <div class="title">\n' +
                '            <h3>Жанр: <a class="genre-link" id="' + bookList[i].genre.id + '" href="#">' + bookList[i].genre.name + '</a></h3>\n' +
                '            <h3>Издательство: <a class="publisher-link" id="' + bookList[i].publisher.id + '"  href="#">' + bookList[i].publisher.name + '</a></h3>\n' +
                '            <h3>Год издания: ' + bookList[i].publishYear + '</h3>\n' +
                '        </div>\n' +
                '        <div class="price">\n' +
                '            $999.99\n' +
                '        </div>\n' +
                '        <div class="actions">\n' +
                '            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">\n' +
                '                <div class="btn-group-lg bottom-indent" role="group" aria-label="First group">\n' +
                '                    <a href="' + info + bookList[i].id + '" class="btn" role="button"\n' +
                '                       data-toggle="tooltip"\n' +
                '                       data-placement="top" title="В корзину"><i\n' +
                '                            class="glyphicon glyphicon-shopping-cart icon-white"></i></a>\n' +
                '\n' +
                '                    <a href="' + showPdf + bookList[i].id + '" class="btn" role="button"\n' +
                '                       data-toggle="tooltip"\n' +
                '                       data-placement="top" title="Читать"><i\n' +
                '                            class="glyphicon glyphicon-eye-open icon-white"></i></a>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">\n' +
                '                <div class="btn-group-lg" role="group" aria-label="First group">\n' +
                '                    <a href="' + info + bookList[i].id + '" class="btn" role="button"\n' +
                '                       data-toggle="tooltip"\n' +
                '                       data-placement="top" title="Изменить"><i\n' +
                '                            class="glyphicon glyphicon-pencil icon-white"></i></a>\n' +
                '                    <a href="' + info + bookList[i].id + '" class="btn" role="button"\n' +
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
        });
    }
}


function booksHomePagination(itemsCount, booksOnPage) {
    $('.pagination').pagination({
        items: itemsCount,
        prevText: "Предыдущая",
        nextText: "Следующая",
        itemsOnPage: booksOnPage,
        onPageClick: function (pageNumber, event) {
            $.ajax({
                type: 'POST',//тип запроса
                contentType: 'application/json', //отправляемый тип
                dataType: 'json',//принимаемый тип (из контроллера)
                url: getContextPath() + '/home/pages?selectedPage=' + pageNumber + '&booksOnPage=' + booksOnPage,//url адрес обработчика
                success: function (data) {//принимаемое от сервера (Response)
                    createHtmlWithBookList(data);
                },
                error: function () {
                    alert('Ошибка в booksHomePagination()/pagination()/onPageClick()/ajax');
                }
            });
            redrawPaginator();
        }
    });
}

function booksSearchPagination(itemsCount, booksOnPage, foundResultText) {
    $('.pagination').pagination({
        items: itemsCount,
        prevText: "Предыдущая",
        nextText: "Следующая",
        itemsOnPage: booksOnPage,
        onPageClick: function (pageNumber, event) {
            $.ajax({
                type: 'POST',//тип запроса
                contentType: 'application/json', //отправляемый тип
                dataType: 'json',//принимаемый тип (из контроллера)
                url: getContextPath() + '/home/searchByCriteria?selectedPage=' + pageNumber + '&booksOnPage=' + booksOnPage,//url адрес обработчика
                success: function (data) {//принимаемое от сервера (Response)
                    createHtmlWithBookList(data, foundResultText);
                },
                error: function () {
                    alert('Ошибка в booksSearchPagination()/pagination()/onPageClick()/ajax');
                }
            });
            redrawPaginator();
        }
    });
}

function destroyPaginator() {
    $('.pagination').pagination('destroy');
}

function redrawPaginator() {
    $('.pagination').pagination('redraw');
}

//метод для получения контекстного пути '/aptech-library'
//!!! работает только с корневым именем, не содержащим '/'
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}

