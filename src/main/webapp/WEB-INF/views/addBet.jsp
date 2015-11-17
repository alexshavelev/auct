<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/main.css" />
<title>Add bet</title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<div class="forms">
			<div class="error">
				<c:out value="${errormsg}" />
			</div>
			<h2>
				<spring:message code = "abet.for"/>:
				<c:out value="${lot.name}" /> ( <c:choose>
						<c:when test="${lot.getCurrentBet()!= null}">
							<c:out value="${lot.getCurrentBet().betPrice}" />
						</c:when>
						<c:otherwise>
							<c:out value="${lot.startPrice}" />
						</c:otherwise>
					</c:choose>$)
			</h2>
			<form method="post"
				action="${pageContext.request.contextPath}/saveBet/${user.userId}/${lot.lotId}">
				<table>
					<tr>
						<td><spring:message code = "abet.your"/>:</td>
						<td><input type="text" name='betPrice' size="30"></td>
					</tr>
				</table>
				<br /> <input type="submit" class="button" value="<spring:message code = "abet.add"/>">
				<a href="${pageContext.request.contextPath}/lots"><spring:message code = "abet.cancel"/></a>
			</form>
		</div>
	</div>
</body>
</html>