<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/main.css" />
<script type="text/JavaScript"
	src="<%=request.getContextPath()%>/styles/jquery-1.9.1.min.js"></script>
<script type="text/JavaScript"
	src="<%=request.getContextPath()%>/styles/jquery.magnifier.js"></script>
<title><spring:message code="lotdet.title" /></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div id="detail">
			<div id="info">
				<h3>
					<c:out value="${lot.name}" />
				</h3>
				<table>
					<tr>
						<th><spring:message code="lotdet.desc" />:</th>
						<td><c:out value="${lot.description}" /></td>
					</tr>
					<tr>
						<th><spring:message code="lotdet.price" />:</th>
						<td><c:out value="${lot.startPrice}" />$</td>
					</tr>
					<tr>
						<th><spring:message code="lotdet.bet" />:</th>
						<td><c:out value="${lot.getCurrentBet().betPrice}" /> $</td>
					</tr>
					<tr>
						<th><spring:message code="lotdet.owner" />:</th>
						<td><c:out value="${lot.getUser().name}" /></td>
					</tr>
					<tr>
						<th><spring:message code="lotdet.state" />:</th>
						<td><c:out value="${lot.lotState}" /></td>
					</tr>

				</table>
			</div>
			<div id="lotbigimg">
				<img src="<c:url value="${lot.getImgPath()}" />" alt="LotImg" class="magnify"/>
			</div>
			<div style="width: 100%; height: 1px; clear: both;"></div>
			<br /> <span> <c:if
					test="${lot.getUser().email != user.email}">
					<a href="${pageContext.request.contextPath}/addBet/${lot.lotId}">
					<spring:message code="lotdet.addbet" /></a>
				</c:if> <c:choose>
					<c:when test="${lot.getUser().email == user.email}">
						<a href="${pageContext.request.contextPath}/editlot/${lot.lotId}"><spring:message code="lotdet.edit" /></a>
						<a
							href="${pageContext.request.contextPath}/deleteLot/${lot.lotId}"><spring:message code="lotdet.del" /></a>
					</c:when>
				</c:choose>
			</span>

		</div>
	</div>
</body>
</html>