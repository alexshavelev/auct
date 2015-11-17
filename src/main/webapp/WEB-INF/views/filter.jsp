<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="filter">
	<form id="search" method="get" action="searchLots">
		<table>
			<tr>
				<td rowspan="2"><spring:message code="filter.name" />: <input type="text" name='lotname'
					size="20"></td>
				<td>	<spring:message code="filter.price" />: <input type="text" name='minprice' size="10"><spring:message code="filter.to" /> <input
					type="text" name='maxprice' size="10">
					<input
					type="submit" class="button" value="<spring:message code="filter.search" />"> <a
					href="${pageContext.request.contextPath}/lots"> &#215;</a>
				</td>
			</tr>
			<tr>
			
				<td><spring:message code="filter.date" />: <input type="text" name='minenddate' size="10"><spring:message code="filter.to" />
					<input type="text" name='maxenddate' size="10"> 
				</td>
			</tr>
		</table>
	</form>

</div>