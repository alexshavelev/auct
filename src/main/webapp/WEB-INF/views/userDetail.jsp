<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<title><spring:message code = "userdet.title"/></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div id="detail">
			<h3>
				<c:out value="${user.name}" />
			</h3>
			<table>
			<tr>
					<th><spring:message code = "userdet.mail"/>:</th>
					<td><c:out value="${user.email}" /></td>
				</tr>
				<tr>
					<th><spring:message code = "userdet.balance"/>:</th>
					<td><c:out value="${user.balance}" />$</td>
				</tr>
				<tr>
					<th><spring:message code = "userdet.disc"/>:</th>
					<td><c:out value="${user.discount*100.0}" />%</td>
				</tr>
				<tr>
					<th><spring:message code = "userdet.points"/>:</th>
					<td><c:out value="${user.points}" /></td>
				</tr>
				<tr>
					<th><spring:message code = "userdet.exp"/>:</th>
					<td><c:out value="${user.experience}" /> month(s)</td>
				</tr>
				<tr>
					<th><spring:message code = "userdet.status"/>:</th>
					<td><c:out value="${user.userRole}" /> </td>
				</tr>
			</table>
			<br/>
			<a href="userlots/${user.userId}"><spring:message code = "userdet.lots"/></a>
			<a href="userbets/${user.userId}"><spring:message code = "userdet.bets"/></a>
			<a href="edituser"><spring:message code = "userdet.edit"/></a>
		</div>
	</div>
</body>
</html>