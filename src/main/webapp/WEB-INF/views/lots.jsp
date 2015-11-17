<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/main.css" />
<script type="text/JavaScript"
	src="<%=request.getContextPath()%>/styles/jquery-1.9.1.min.js"></script>

<script type="text/JavaScript">

	var version=0;
	var newversion;

$(document).ready(function() {
	setInterval(function(){
		//alert(version);		
		$.get('<%=request.getContextPath()%>/update', function(data) {
				newversion = data;
				//alert(newversion);
				});
			if (version != newversion) {
				version = newversion;
				
				$('#lots').load('<%=request.getContextPath()%>/filterLots?pg='
						+ $(
								'.current_page')
								.text()
						+ '&'
						+ window.location.search
								.substring(1)
						);
	
			}
			version = newversion;
				}, 5000);
	
	$(function(){
				$('#name').addClass('sortable order_desc');
				$('#startprice').addClass('sortable order_desc');
				$('#enddate').addClass('sortable order_desc');
				});

    $(function(){
        $('.page_nav a').click(function(e) {
         $('#lots').load('<%=request.getContextPath()%>/filterLots?pg='
									+ $(this).text() + '&'
									+ window.location.search.substring(1));
					e.preventDefault();
				});
	});

     $(function(){
        $('.sortable').click(function(e) {
        	var sortDirection = $(this).is('.order_asc') ? "desc" : "asc";
        	$('.sortable').removeClass('sorted');
        	$(this).addClass('sorted');
        	sortDirection == "desc"?$(this).removeClass('order_asc').addClass('order_desc'):
        		$(this).removeClass('order_desc').addClass('order_asc');
         $('#lots').load('<%=request.getContextPath()%>/filterLots?pg='
																		+ $(
																				'.current_page')
																				.text()
																		+ '&'
																		+ window.location.search
																				.substring(1)
																		+ '&orderParam='
																		+ this.id
																		+ '&orderDir='
																		+ sortDirection);
												e.preventDefault();
											});
						});
					});
</script>
<title><spring:message code="lots.title" /></title>
</head>
<body>
	<div id="main">
		<jsp:include page="header.jsp" />
		<h1>
			<spring:message code="lots.h" />
		</h1>
		<div class="error">
			<h2>
				<c:out value="${errormsg}" />
			</h2>
		</div>
		<div id="find">
			<jsp:include page="filter.jsp" />
			<div id="orderby">
				<spring:message code="lots.order" />
				: <span id="name"><spring:message code="lots.ordername" /></span> <span
					id="startprice"><spring:message code="lots.orderprice" /></span> <span
					id="enddate"><spring:message code="lots.orderdate" /></span>
			</div>
		</div>
		<div id="lots">
			<jsp:include page="filteredLots.jsp" />
		</div>
		
	</div>
</body>
</html>