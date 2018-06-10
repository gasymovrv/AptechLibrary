function confirmDeleteAuthor(authorId, authorName) {
    getConfirm("Уверены что хотите удалить автора '" + authorName + "'?", function(choose) {
        if(choose){
            window.location = (getContextPath() + '/authors/deleteAuthor?authorId=' + authorId);
        }
    });
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
            window.location = (getContextPath() + '/authors/list?authorsOnPage=' + authorsOnPage + '&selectedPage=' + page + '&isPagination=true');
        }
    });
}

function searchBooksByAuthor(authorId, authorName) {
    let text = "Найдено книг автора " + authorName + ": ";
    if(authorName === "Неизвестный автор"){
        text = "Найдено книг без автора: ";
    }
    saveFoundResultText(text);
    window.location = (getContextPath() + '/home?authorId=' + authorId);
}