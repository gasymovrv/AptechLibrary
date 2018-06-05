<%@include file="../../include.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Main box (non search) - authors-->
<div class="center-block col-sm-9" style="float: none;">
    <div id="row-info" class="row">
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <div class="col-sm-2">
                <a href="${contextPath}authors/addAuthorView" type="button" role="button" class="btn btn-md admin-button">Добавить автора</a>
            </div>
        </security:authorize>
    </div>
    <div id="box-with-rows-for-authors" class="row">
        <c:set var="i" value="0" scope="page"/>
        <c:forEach begin="1" end="${authorList.size()}" step="3">
            <div class="row">
                <c:forEach var="j" begin="0" end="2">
                    <c:if test="${(i+j)<authorList.size()}">
                        <c:set var="author" value="${authorList[i+j]}"/>
                        <div class="col-sm-4">
                            <!-- Product -->
                            <div class="shop-item">
                                <!-- Product Title -->
                                <div class="title">
                                    <h3>
                                        <a href="#" onclick="searchBooksByAuthor(${author.id}, '${author.fio}')">${author.fio}</a>
                                    </h3>
                                </div>
                                <div class="title">
                                    <h3>Дата рождения:
                                        <c:choose>
                                            <c:when test="${not empty author.birthday}">
                                                ${author.birthday}
                                            </c:when>
                                            <c:otherwise>
                                                Неизвестна
                                            </c:otherwise>
                                        </c:choose>
                                    </h3>
                                </div>
                                <div class="title">
                                    <h3>Количество книг: ${author.books.size()}</h3>
                                </div>
                                <div class="title">
                                    <h3>Читатели: ${author.views}</h3>
                                </div>
                                <!-- Operations -->
                                <div class="actions">
                                    <security:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="${editAuthor}?authorId=${author.id}" class="btn admin-button item-actions"
                                           role="button" data-placement="top" data-toggle="popover" data-content="Изменить">
                                            <i class="glyphicon glyphicon-pencil icon-white"></i></a>
                                        <a href="#" class="btn admin-button item-actions"
                                           onclick="confirmDeleteAuthor(${author.id}, '${author.fio}')"
                                           role="button" data-placement="top" data-toggle="popover" data-content="Удалить">
                                            <i class="glyphicon glyphicon-trash icon-white"></i></a>
                                    </security:authorize>
                                </div>
                            </div>
                            <!-- End Product -->
                        </div>
                    </c:if>
                </c:forEach>
                <c:set var="i" value="${i+3}" scope="page"/>
            </div>
        </c:forEach>
    </div>

    <tiles:insertAttribute name="pagination"/>

</div>
<script>

    <c:if test="${not empty sessionScope.isDeleted}">
    if (${sessionScope.isDeleted}) {
        alert("Автор успешно удален!");
    } else {
        alert("Произошла ошибка при удалении!");
    }
    <%session.setAttribute("isDeleted", null);%>
    </c:if>

    popovers();
</script>