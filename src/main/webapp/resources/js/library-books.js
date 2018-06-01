
function searchByText() {
    $(document).on('submit', '#top-panel-form', function (event) {
        event.preventDefault();
        let criteria = {
            'text': $('#top-panel-form-text').val(),
            'searchType': $('#top-panel-form-select').val(),
            'sortType': $('#top-panel-form-select-sort').val()
        };
        let text = "Найдено книг по Вашему запросу: ";
        saveFoundResultText(text);
        printItemsWithPagination(criteria);
    });
}

function searchByLetter() {
    $(document).on('click', '#letters-form button', function () {//то же что и метод click, но работает всегда
        let criteria = {
            'letter': $(this).prop('id'),
            'sortType': $('#top-panel-form-select-sort').val()};
        let text = "Найдено книг, начинающихся на '" + criteria['letter'] + "': ";
        saveFoundResultText(text);
        printItemsWithPagination(criteria);
    });
}

function searchByGenre() {
    $(document).on('click', 'a.genre-link', function () {//то же что и метод click, но работает всегда
        let criteria = {
            'genreId': $(this).prop('id'),
            'sortType': $('#top-panel-form-select-sort').val()
        };
        let genreName = $(this).text();
        let text = "Найдено книг жанра '" + genreName + "': ";
        saveFoundResultText(text);
        printItemsWithPagination(criteria);
    });
}

function searchByPublisher() {
    $(document).on('click', 'a.publisher-link', function () {//то же что и метод click, но работает всегда
        let criteria = {
            'publisherId': $(this).prop('id'),
            'sortType': $('#top-panel-form-select-sort').val()
        };
        let publisherName = $(this).text();
        let text = "Найдено книг издательства '" + publisherName + "': ";
        saveFoundResultText(text);
        printItemsWithPagination(criteria);

    });
}

function searchByAuthor() {
    $(document).on('click', 'a.author-link', function () {//то же что и метод click, но работает всегда
        let criteria = {
            'authorId': $(this).prop('id'),
            'sortType': $('#top-panel-form-select-sort').val()
        };
        let authorName = $(this).text();
        let text = "Найдено книг автора " + authorName + ": ";
        if(authorName === "Неизвестный автор"){
            text = "Найдено книг без автора: ";
        }
        saveFoundResultText(text);
        printItemsWithPagination(criteria);
    });
}


function changeBooksOnPage() {
    $(document).on('click', '#books-on-page-button', function () {
        let booksOnPage = $('#books-on-page-input').val();
        printItemsWithPagination(getCriteria(), booksOnPage);
    });
}

function printItemsWithPagination(criteria, itemsOnPage) {
    let $elem = $('.pagination-wrapper');
    if(!$elem.is(':visible')){
        //если пагинатор был скрыт (как например на странице bookInfo)
        //то показываем
        $elem.show();
    }
    if(!itemsOnPage){
        //если аргумент не передан, то берем значение из сессии
        itemsOnPage = getBooksOnPage();
    }
    if (!criteria) {criteria = {};}
    $.ajax({//получаем количество книг по данным критериям
        type: 'POST',//тип запроса
        contentType: 'application/json', //отправляемый тип
        dataType: 'json',//принимаемый тип (из контроллера)
        url: getContextPath() + '/books/getQuantityBooks',//url адрес обработчика
        data: JSON.stringify(criteria),//отправляемое отсюда (Request)
        async: false,
        success: function (items) {//принимаемое от сервера (Response)
            itemsPagination(items, itemsOnPage, criteria);
        },
        error: function () {
            alert('Ошибка в printItemsWithPagination');
        }
    });
}

function itemsPagination(items, itemsOnPage, criteria) {
    $('#books-pag').pagination({
        items: items,
        itemsOnPage: itemsOnPage,
        cssStyle: '',
        prevText: 'Назад',
        nextText: 'Вперед',
        onInit: function () {
            // fire first page loading
            getItemsByAjax(1, itemsOnPage, criteria, false, items);
        },
        onPageClick: function (page, evt) {
            getItemsByAjax(page, itemsOnPage, criteria, true, items);
        }
    });
}

function getItemsByAjax(page, itemsOnPage, criteria, async, items) {
    if (!criteria) {criteria = {};}
    $.ajax({
        type: 'POST',//тип запроса
        contentType: 'application/json', //отправляемый тип
        dataType: 'json',//принимаемый тип (из контроллера)
        url: getContextPath() + '/books/searchByCriteria?selectedPage=' + page + '&booksOnPage=' + itemsOnPage,//url адрес обработчика
        data: JSON.stringify(criteria),//отправляемое отсюда (Request)
        async: async,
        success: function (data) {//принимаемое от сервера (Response)
            createHtmlItemsList(data, items);
        },
        error: function () {
            alert('Ошибка в getItemsByAjax');
        }
    });
}

function createHtmlItemsList(bookList, items) {
    //костыль для того чтобы при обновлении после аджакс-поиска всегда
    window.history.pushState(null, null, getContextPath() + '/home');

    let showImgLink = getContextPath() + '/books/showBookImage?bookId=';
    let bookInfoLink = getContextPath() + '/books/bookInfo?bookId=';
    let editBookLink = getContextPath() + '/books/editBookView?bookId=';
    let addBookLink = getContextPath() + '/books/addBookView';
    let foundResultText = getFoundResultText();
    saveFoundResultText("");//обнуляем, иначе отображается старый текст при нажатии кнопки назад
    let rowId = 'row-with-books_0';
    let admin = isAdmin();
    $('#main-box').html(
        '<div id="row-info" class="row"></div>\n' +
        '<div id="box-with-rows-for-books" class="row">' +
        '    <div id="' + rowId + '" class="row"></div>' +
        '</div>\n');

    if (foundResultText) {//если атрибут foundResultText не пустой
        $('#row-info').html('<div class="col-sm-8" id="books-count"><h3>' + foundResultText + ' ' + items + '</h3></div>');
    }
    if(admin){
        $('#row-info').append(
            '<div class="col-sm-2">\n' +
            '    <a href="' + addBookLink + '" type="button" role="button" class="btn btn-md admin-button">Добавить книгу</a>\n' +
            '</div>');
    }


    let j = 0;
    for (let i = 0; i < bookList.length; i++) {
        //вложенный аджакс для того чтобы заранее подготовить все обложки книг
        $.ajax({
            url: getContextPath() + "/books/showBookImage",
            type: "GET",
            data: {bookId: bookList[i].id},
            async: false
        }).always(function () {
            let html =
                '<div class="col-sm-4">\n' +
                '    <div class="shop-item">\n' +
                '        <div class="image">\n' +
                '            <a href="' + bookInfoLink + bookList[i].id + '"><img class="img-rounded"\n' +
                // '                    src="data:image/jpeg;base64,' + bookList[i].image + '"\n' + //вариант без вложенного аджакса и без контроллера
                '                    src="' + showImgLink + bookList[i].id + '"\n' +
                '                     alt="Изображение отсутствует"></a>\n' +
                '        </div>\n' +
                '        <div class="title">\n' +
                '            <h3><a href="' + bookInfoLink + bookList[i].id + '">' + bookList[i].name + '</a></h3>\n' +
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
                '                    <a href="#" onclick="confirmAddToCart(' + bookList[i].id + ', \'' + bookList[i].name + '\')" ' +
                '                        class="btn item-actions" role="button"\n' +
                '                        data-placement="top" data-toggle="popover" data-content="В корзину">' +
                '                       <i class="glyphicon glyphicon-shopping-cart icon-white"></i></a>\n' +
                '                    <a href="#" onclick="confirmShowBookContent(' + bookList[i].id + ', \'' + bookList[i].name + '\')" ' +
                '                        class="btn item-actions" role="button"\n' +
                '                        data-placement="top" data-toggle="popover" data-content="Читать">' +
                '                       <i class="glyphicon glyphicon-eye-open icon-white"></i></a>\n' +
                '                </div>\n' +
                '            </div>\n';
            if (admin) {
                html +=
                    '            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">\n' +
                    '                <div class="btn-group-lg" role="group" aria-label="First group">\n' +
                    '                    <a href="' + editBookLink + bookList[i].id + '" class="btn admin-button item-actions" role="button"\n' +
                    '                        data-placement="top" data-toggle="popover" data-content="Изменить">' +
                    '                       <i class="glyphicon glyphicon-pencil icon-white"></i></a>\n' +
                    '                    <a href="#" onclick="confirmDeleteBook(' + bookList[i].id + ', \'' + bookList[i].name + '\')"' +
                    '                        class="btn admin-button item-actions" role="button"\n' +
                    '                        data-placement="top" data-toggle="popover" data-content="Удалить">' +
                    '                       <i class="glyphicon glyphicon-trash icon-white"></i></a>\n' +
                    '                </div>\n' +
                    '            </div>\n';
            }
            html +=
                '        </div>\n' +
                '    </div>\n' +
                '</div>\n';
            $('#' + rowId).append(html);
            j++;
            if (j === 3) {
                rowId = 'row-with-books_' + i;
                $('#box-with-rows-for-books').append('<div id="' + rowId + '" class="row"></div>');
                j = 0;
            }
        });
    }
    popovers();
}


//получаем значение атрибута booksOnPage из сессии
function getBooksOnPage() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        dataType: 'json',//принимаемый тип (из контроллера)
        url: getContextPath() + '/books/getBooksOnPage',//url адрес обработчика
        async: false,
        success: function (itemsOnPage) {//принимаемое от сервера (Response)
            result = itemsOnPage;
        },
        error: function () {
            alert('Ошибка в script getBooksOnPage');
        }
    });
    return result;
}

//получаем значение атрибута criteria из сессии
function getCriteria() {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        dataType: 'json',//принимаемый тип (из контроллера)
        url: getContextPath() + '/books/getCriteria',//url адрес обработчика
        async: false,
        success: function (criteria) {//принимаемое от сервера (Response)
            result = criteria;
        },
        error: function () {
            alert('Ошибка в getCriteria');
        }
    });
    return result;
}

function confirmDeleteBook(bookId, bookName) {
    if (confirm("Уверены что хотите удалить книгу '" + bookName + "'?")) {
        window.location = (getContextPath() + '/books/deleteBook?bookId=' + bookId);
    }
}

function confirmShowBookContent(bookId, bookName) {
    let showBookContentLink = getContextPath() + '/books/showBookContent?bookId=';
    if (!isUser()) {
        if (confirm("Чтобы читать книгу '" + bookName + "' вам необходимо авторизоваться")) {
            window.location = (showBookContentLink + bookId);
        }
    } else {
        window.location = (showBookContentLink + bookId);
    }
}

function confirmAddToCart(bookId, bookName) {
    let addToCartLink = getContextPath() + '/books/addToCart?bookId=';
    if (!isUser()) {
        if (confirm("Чтобы купить книгу '" + bookName + "' вам необходимо авторизоваться")) {
            window.location = (addToCartLink + bookId);
        }
    } else {
        window.location = (addToCartLink + bookId);
    }
}
