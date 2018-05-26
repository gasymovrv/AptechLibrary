
function confirmDeleteAuthor(authorId, authorName) {
    if (confirm("Уверены что хотите удалить автора '" + authorName + "'?")) {
        window.location = (getContextPath() + '/authors/deleteAuthor?authorId=' + authorId);
    }
}