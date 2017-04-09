<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<html lang="en">
<jsp:include page="fragments/headTag.jsp" />

<body>
	<jsp:include page="fragments/menu_invers.jsp" />
	
	<c:choose>
	
		<c:when test="${not empty error}">
             <div id="error" class="error">${error}</div>
		</c:when>
		
		<c:otherwise>
			<div class="container-fluid">
				<div class="row">
					<jsp:include page="${jspName}" />
				</div>
			</div>

		</c:otherwise>
	</c:choose>
</body>
</html>
