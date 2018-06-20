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

function addOrEditAuthorValidation() {
    $('#change-author-form').submit(function (event) {
        let result = true;
        $(".invalid-feedback").empty();
        $("div.form-group").removeClass("has-error");

        let requiredInputs = [$("#fio")];
        for(let i in requiredInputs) {
            if(!requiredInputs[i].val()){
                requiredInputs[i].parents("div.form-group").addClass("has-error");
                requiredInputs[i].after('<div class="invalid-feedback" style="color: red"><sup style="color: red">*</sup>Обязательное поле</div>');
                result = false;
            }
        }
        return result;
    });

}