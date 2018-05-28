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

function searchBooksByAuthor(authorId, authorName) {
    let text = "Найдено книг автора " + authorName + ": ";
    saveFoundResultText(text);
    window.location = (getContextPath() + '/home?authorId=' + authorId);
}