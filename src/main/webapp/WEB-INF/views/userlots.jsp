<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/main.css" />
<script type="text/JavaScript"
	src="<%=request.getContextPath()%>/styles/jquery-1.9.1.min.js"></script>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         $('#main').load('<%=request.getContextPath()%>/userlots/${user.userId}?pg='
									+ $(this).text());
					e.preventDefault();
				});
	});
</script>
<title><spring:message code = "userdet.lots"/></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<h1><spring:message code = "userdet.lots"/></h1>
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
				<span class="price"> <c:choose>
						<c:when test="${lot.getCurrentBet()!= null}">
							<c:out value="${lot.getCurrentBet().betPrice}" />
						</c:when>
						<c:otherwise>
							<c:out value="${lot.startPrice}" />
						</c:otherwise>
					</c:choose>
				</span>
				<div id="lot_bottom">
					<a href="${pageContext.request.contextPath}/lotDetail/${lot.lotId}"><spring:message code = "nlots.details"/></a>

					<span class="date"><spring:message code = "alot.date"/>: <span class="date_value">
							<fmt:formatDate value="${lot.endDate.getTime()}"
								pattern="yyyy-MM-dd HH:mm" />
					</span>
					</span>
				</div>
				<div class="${lot.lotState} state">
					<c:out value="${lot.lotState}" />
				</div>
			</div>
		</c:forEach>
		<div class="page_nav">${pageNav}</div>
	</div>
</body>
</html>