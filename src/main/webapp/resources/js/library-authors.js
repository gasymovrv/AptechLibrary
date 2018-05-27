function confirmDeleteAuthor(authorId, authorName) {
    if (confirm("Уверены что хотите удалить автора '" + authorName + "'?")) {
        window.location = (getContextPath() + '/authors/deleteAuthor?authorId=' + authorId);
    }
}

function authorsPagination(quantityAuthors, authorsOnPage, selectedPage) {
    $('#authors-pag').pagination({
        items: quantityAuthors,
        itemsOnPage: authorsOnPage,
        currentPage: selectedPage,
        cssStyle: '',
        prevText: 'Назад',
        nextText: 'Вперед',
        onPageClick: function (page, evt) {
            window.location = (getContextPath() + '/authors/list?authorsOnPage=' + authorsOnPage + '&selectedPage=' + page);
        }
    });
}

function searchBooksByAuthor() {
    $(document).on('click', 'a.author-link', function () {//то же что и метод click, но работает всегда
        let authorId = $(this).prop('id');
        let authorName = getAuthorName(authorId);
        let text = "Найдено книг автора " + authorName + ": ";
        saveFoundResultText(text);
        window.location = (getContextPath()+'/home?authorId='+authorId);
    });
}

//получаем значение атрибута foundResultText из сессии
function getAuthorName(authorId) {
    let result;
    $.ajax({
        type: 'GET',//тип запроса
        url: getContextPath() + '/authors/getAuthorName?authorId='+authorId,//url адрес обработчика
        async: false,
        success: function (text) {//принимаемое от сервера (Response)
            result = text;
        },
        error: function () {
            alert('Ошибка в script getAuthorName');
        }
    });
    return result;
}