<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<c:forEach items="${lots}" var="lot" varStatus="status">
	<div class="lot">
		<div id="lotimg">
			<img src="<c:url value="${lot.getImgPath()}" />" hspace="5"
				alt="LotImg" />
		</div>
		<h3>
			<c:out value="${lot.name}" />
		</h3>
		<c:out value="${lot.description}" />
		<span class="price"><c:choose>
				<c:when test="${lot.getCurrentBet()!= null}">
					<c:out value="${lot.getCurrentBet().betPrice}" />
				</c:when>
				<c:otherwise>
					<c:out value="${lot.startPrice}" />
				</c:otherwise>
			</c:choose></span>
		<div id="lot_bottom">
			<c:if test="${lot.getUser().email != user.email}">
				<a href="${pageContext.request.contextPath}/addBet/${lot.lotId}"><spring:message code = "nlots.add"/></a>
			</c:if>
			<a href="lotDetail/${lot.lotId}"><spring:message code = "nlots.details"/></a> <span class="date"><spring:message code = "nlots.date"/>: <span class="date_value"> <fmt:formatDate
						value="${lot.endDate.getTime()}" pattern="yyyy-MM-dd HH:mm" />
			</span>
			</span>
		</div>
	</div>
</c:forEach>

