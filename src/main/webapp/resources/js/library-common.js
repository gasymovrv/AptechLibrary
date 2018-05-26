$(document).ready(function () {
    //изменение количества книг на странице
    $(document).on('click', '#books-on-page-button', function () {
        let booksOnPage = $('#books-on-page-input').val();
        printItemsWithPagination(getCriteria(), booksOnPage);
    });

    //обновление списка книг при поиске
    searchByAuthor();
    searchByPublisher();
    searchByGenre();
    searchByLetter();
    searchByText();
});

function  popovers() {
    let actions = $('.element-actions');
    actions.hover(
        function () {
            $(this).popover('show');
        },
        function () {
            $(this).popover('hide');
        }
    );
}

//метод для получения контекстного пути '/aptech-library'
//!!! работает только с корневым именем, не содержащим '/'
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}