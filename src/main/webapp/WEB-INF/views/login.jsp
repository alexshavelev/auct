<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<title>Log in</title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div class="forms">
			<form method="post"
				action="<c:url value='j_spring_security_check' />">
				<table>
					<tr>
						<td><spring:message code = "login.email"/></td>
						<td><input type="text" name='j_username' size="30"></td>
					</tr>
					<tr>
						<td><spring:message code = "login.pass"/></td>
						<td><input type="password" name='j_password' size="30"></td>
					</tr>
				</table>
				<br /> <input type="submit" class="button" value="<spring:message code = "login.login"/>">
				<a href="index"><spring:message code = "login.cancel"/></a>
			</form>
		</div>
	</div>
</body>
</html>