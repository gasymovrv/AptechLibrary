<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}/" scope="request"/>
<c:set var="contextPathToRes" value="${contextPath}resources/" scope="request"/>
<c:set var="serverHost" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}" scope="request"/>
<c:set var="home" value="${contextPath}home" scope="request"/>
<c:set var="bookInfo" value="${contextPath}books/bookInfo" scope="request"/>
<c:set var="editBook" value="${contextPath}books/editBookView" scope="request"/>
<c:set var="showImg" value="${contextPath}books/showBookImage" scope="request"/>
<c:set var="authorsPage" value="${contextPath}authors/list" scope="request"/>
<c:set var="editAuthor" value="${contextPath}authors/editAuthorView" scope="request"/>
<c:set var="accountPage" value="${contextPath}users/account" scope="request"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="customFn" uri = "/WEB-INF/tld/customFn.tld" %>