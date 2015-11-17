<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<div id="header">
	<div id="logo">
		<a href="${pageContext.request.contextPath}/index"><img
			src="<c:url value="/resources/img/logo.png" />" alt="logo"></a>
	</div>
	<ul id="navigation">
		<li><a href="${pageContext.request.contextPath}/lots"><spring:message code="head.lots" /></a></li>
		<li><a href="${pageContext.request.contextPath}/newlot"><spring:message code="head.addlot" /></a></li>
	</ul>
	
	<div id="login">
		<span style="float: right"> 
		<a href="?lang=en"><img src="<c:url value="/resources/img/flag_en.png" />" 
		width="19" height="12"	alt="en"></a> 
		<a href="?lang=ua"><img	src="<c:url value="/resources/img/flag_ua.png" />" 
		width="19" height="12" alt="ua"></a>
		</span>
		
		<c:choose>
			<c:when test="${user!=null}">
				<a href="${pageContext.request.contextPath}/userDetail"><c:out
						value="${user.name}" /> </a> | <a
					href="${pageContext.request.contextPath}/<c:url value="j_spring_security_logout" />">
					<spring:message code = "head.logout" /></a>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/login"><spring:message code = "head.login" /></a> | <a
					href="${pageContext.request.contextPath}/registration"><spring:message code = "head.register" /></a>
			</c:otherwise>
		</c:choose>

	</div>
</div>