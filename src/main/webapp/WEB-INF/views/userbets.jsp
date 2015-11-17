<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/main.css" />
<title>My bets</title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<h1>My Bets</h1>
		<div class="error">
			<h2>
				<c:out value="${errormsg}" />
			</h2>
		</div>
		<c:forEach items="${bets}" var="bet" varStatus="status">
			<div class="lot">
				<div id="lotimg">
					<img src="<c:url value="${bet.lot.getImgPath()}" />" hspace="5"
						alt="LotImg" />
				</div>
				<h3>
					<c:out value="${bet.getLot().name}" />
				</h3>
				<c:out value="${bet.getLot().description}" />
				<span class="price"> <c:out value="${bet.betPrice}" />
				</span>
				<div class="mid">
					<span class="date">Bet date: <span class="date_value">
							<fmt:formatDate value="${bet.getBetDate()}"
								pattern="yyyy-MM-dd HH:mm" />
					</span></span>
				</div>
				<div id="lot_bottom">
					<a
						href="${pageContext.request.contextPath}/addBet/${bet.getLot().lotId}">Add
						Bet</a> <a
						href="${pageContext.request.contextPath}/removeBet/${user.userId}/${bet.getLot().lotId}/${bet.betId}">Cancel
						bet </a> <a
						href="${pageContext.request.contextPath}/lotDetail/${bet.getLot().lotId}">Details</a>
					<span class="date">End date: <span class="date_value">
							<fmt:formatDate value="${bet.getLot().endDate.getTime()}"
								pattern="yyyy-MM-dd HH:mm" />
					</span>
					</span>
				</div>
				<div class="${bet.betState} state">
					<c:out value="${bet.betState}" />
				</div>
			</div>
		</c:forEach>

	</div>
</body>