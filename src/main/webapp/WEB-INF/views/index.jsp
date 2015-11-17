<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/main.css" />
<title><spring:message code = "index.title"/></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<h1><spring:message code = "index.lots"/></h1>


		<jsp:include page="lastLots.jsp" flush="true" />
		<br/><br/><br/>
		<h1><spring:message code = "index.bets"/></h1>
		<jsp:include page="lastBets.jsp" flush="true" />

	</div>

</body>
</html>