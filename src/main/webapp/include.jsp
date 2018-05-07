<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}/" scope="request"/>
<c:set var="contextPathToRes" value="${contextPath}resources/" scope="request"/>
<c:set var="serverHost" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}" scope="request"/>
<c:set var="home" value="${contextPath}home" scope="request"/>
<c:set var="info" value="${contextPath}info" scope="request"/>
<c:set var="showImg" value="${contextPath}showBookImage" scope="request"/>
<c:set var="showPdf" value="${contextPath}showBookContent" scope="request"/>
<c:set var="bookId" value="bookId" scope="request"/>