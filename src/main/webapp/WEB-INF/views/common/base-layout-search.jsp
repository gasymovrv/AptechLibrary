<%@include file="../../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    //глобальная переменная - наличие прав админа
    let currentUserIsAdmin = isAdmin();
    //глобальная переменная - наличие прав юзера
    let currentUserIsUser = isUser();
    //глобальная переменная - список id книг из всех заказов текущего юзера
    let booksIdInOrdersCurrentUser = getBooksInOrders();
</script>

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="top-panel"/>
<tiles:insertAttribute name="letters"/>
<div class="section">
    <div class="container">
        <div class="row">

            <!-- Sidebar -->
            <div class="col-sm-3 blog-sidebar">
                <tiles:insertAttribute name="sidebar"/>
            </div>

            <!-- Main box -->
            <div class="col-sm-9">
                <tiles:insertAttribute name="main" ignore="true"/>
                <script>
                    //изменение количества книг на странице
                    changeBooksOnPage();
                    //обновление списка книг при поиске
                    searchByAuthor();
                    searchByPublisher();
                    searchByGenre();
                    searchByLetter();
                    searchByText();
                </script>
            </div>

        </div>
    </div>
</div>
<tiles:insertAttribute name="footer"/>