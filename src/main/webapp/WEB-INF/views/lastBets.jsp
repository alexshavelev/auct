<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<c:forEach items="${bets}" var="bet" varStatus="status">
	<div class="lot">
		<div id="lotimg">
			<img src="<c:url value="${bet.lot.getImgPath()}" />" hspace="5"
				alt="LotImg" />
		</div>
		<h3>
			<c:out value="${bet.lot.name}" />
		</h3>
		<p>
			<b class="price"><c:out value="${bet.betPrice}" /></b>
		</p>
		<p>
			<spring:message code = "lbets.state"/>: ${bet.betState} by
			<c:out value="${bet.user.name}" />
		</p>

		<div id="lot_bottom">
			<c:if test="${bet.lot.getUser().email != user.email}">
				<a href="${pageContext.request.contextPath}/addBet/${bet.lot.lotId}">
				<spring:message code = "lbets.add"/></a>
			</c:if>
			<a href="lotDetail/${bet.lot.lotId}"><spring:message code = "lbets.details"/></a> <span class="date">
				<spring:message code = "lbets.date"/>: <span class="date_value"> <fmt:formatDate
						value="${bet.betDate}" pattern="yyyy-MM-dd HH:mm" /></span>
			</span>
		</div>
	</div>
</c:forEach>

