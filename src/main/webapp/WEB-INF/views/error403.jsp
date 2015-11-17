<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/main.css" />
<title>Error 403</title>
</head>
<body>
	
		<div class="error">
			<h1>
				<img height="15" width="15"
					src="<c:url value="/resources/img/error.png" />" alt="error!">
					Sorry...Access denied.
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				
			</h1>
		</div>
	

</body>
</html>