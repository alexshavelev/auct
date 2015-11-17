<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />

<script src="styles/jquery.js" type="text/javascript"></script>
<script src="styles/jquery.validate.min.js" type="text/javascript"></script>
<script src="styles/newlot_rules.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="styles/jquery-ui-1.10.2.custom.css" />
<script src="styles/jquery-ui-1.10.2.custom.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$("#datepicker").datepicker();
	});
</script>

<title><spring:message code = "alot.title"/></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div class="forms">
			<div class="error">
				<c:out value="${errormsg}" />
			</div>
			<form:form id="newLot" method="post" action="lotController"
				modelAttribute="uploadFile" enctype="multipart/form-data">
				<table>
					<tr>
						<td><spring:message code = "alot.name"/></td>
						<td><input type="text" name='name' size="26" value="${name}"></td>
					</tr>
					<tr>
						<td><spring:message code = "alot.desc"/></td>
						<td><input type="text" name='description' size="26" value="${description}"></td>
					</tr>
					<tr>
						<td><spring:message code = "alot.price"/></td>
						<td><input type="text" name='startprice' size="26" value="${startprice}"></td>
					</tr>
					<tr>

						<td><spring:message code = "alot.img"/></td>
						<td><input type="file" name="file" value="Upload" /></td>
					</tr>
					<tr>
						<form:errors path="file" cssClass="error" />
					</tr>
					<tr>
						<td colspan="2"><spring:message code = "alot.date"/>: <input type="text" id="datepicker"
							name="enddate" size="10"> <select name="hour" size="1">
								<c:forEach var="i" begin="0" end="23">
									<option value="<c:out value="${i}" />">
										<c:out value="${i}" />
									</option>
								</c:forEach>
						</select> : <select name="min" size="1">
								<c:forEach var="i" begin="0" end="59">
									<option value="<c:out value="${i}" />">
										<c:out value="${i}" />
									</option>
								</c:forEach>
						</select>
						</td>
					</tr>
				</table>
				<br />
				<input type="submit" class="button" value="<spring:message code = "alot.create"/>">
				<a href="index"><spring:message code = "alot.cancel"/></a>

			</form:form>
		</div>
	</div>
</body>
</html>
