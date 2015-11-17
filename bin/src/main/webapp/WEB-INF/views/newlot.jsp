<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<title>Auction. New lot</title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div class="forms">
			<form method="post" action="lotController">
				<table>
					<tr>
						<td>Name</td>
						<td><input type="text" name='name' size="26"></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><input type="text" name='description' size="26"></td>
					</tr>
					<tr>
						<td>Start price</td>
						<td><input type="text" name='startprice' size="26"></td>
					</tr>
					<tr>
						<td colspan="2"><br />End date <br /> <select name="day"
							size="1">
								<%
									for (int i = 1; i <= 31; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
									}
								%>
						</select> <select name="month" size="1">
								<%
									for (int i = 1; i <= 12; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
									}
								%>
						</select> <select name="year" size="1">
								<%
									for (int i = 2013; i <= 2020; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
									}
								%>
						</select> <select name="hour" size="1">
								<c:forEach var="i" begin="1" end="24">
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
						</select></td>
					</tr>
				</table>
				<br /> <input type="submit" class="button" value="Create">
				<a href="index">Cancel</a>

			</form>
		</div>
	</div>
</body>
</html>