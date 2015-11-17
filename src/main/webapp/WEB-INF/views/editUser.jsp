<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<link rel="stylesheet" type="text/css"
	href="styles/jquery-ui-1.10.2.custom.css" />
<script src="styles/jquery.js" type="text/javascript" ></script>
<script src="styles/jquery.validate.min.js" type="text/javascript" ></script>
<script src="styles/reg_rules.js" type="text/javascript" ></script>
<script src="styles/jquery-ui-1.10.2.custom.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>
</head>
<body>
<div id="main">
		<jsp:include page="header.jsp" />
		<div class="forms">
			<h2><spring:message code="useredit.h" /></h2>
			<div class="error">
				<c:out value="${errormsg}" />
			</div>
			<form id="registration" method="post" action="editUser/${user.userId}">
				<table>
					<tr>
						<td><spring:message code="reg.name" /></td>
						<td><input type="text" name='name' size="30" value="${user.name}"></td>
					</tr>
					<tr>
						<td><spring:message code="reg.email" /></td>
						<td><input type="text" name='email' size="30" value="${user.email}"></td>
					</tr>
					<tr>
						<td><spring:message code="reg.pass" /></td>
						<td><input type="password" name='oldpassword' size="30"></td>
					</tr>
					<tr>
						<td><spring:message code="useredit.newpass" />:</td>
						<td><input id="password" type="password" name='password' size="30"></td>
					</tr>
					<tr>
						<td><spring:message code="useredit.newpassconfirm" />:</td>
						<td><input type="password" name='confirmpass' size="30"></td>
					</tr>
					<tr>
						<td><spring:message code="reg.birth" />:</td>
						<td><input type="text" id="datepicker" name='birthday' value='<fmt:formatDate
						value="${user.birthday.getTime()}" pattern="MM/dd/yyyy" />'>
							<br /></td>
					</tr>

				</table>
				<br /> <input type="submit" class="button" value="<spring:message code="useredit.save" />">
				<a href="${pageContext.request.contextPath}/userDetail"><spring:message code="reg.cancel" /></a>
			</form>
		</div>
	</div>
</body>
</html>