<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	function clearelement(element) {
		element.val('');
	}
</script>
<h5>Список отображаемых полей сертификата</h5>
<form:form id="config" method="POST" commandName="downloadconfig">
	<ul>
		<form:checkboxes element="li" path="fields"	items="${downloadconfig.headermap}" />
	</ul>
</form:form>