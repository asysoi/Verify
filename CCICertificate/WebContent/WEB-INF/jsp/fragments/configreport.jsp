<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script>
	function submit() {
		url = $("#configreport").attr("action");
        alert($("#configreport").serialize());
		$.post(url, $("#configreport").serialize(), function(result) {
			$("#viewreport").val(result);
		});

	}
</script>

<div id="content">
	<form:form id="configreport" action="makereport.do" method="POST" 	commandName="reportconfig">

		Сгруппировать сертификаты по:
		<form:select path="fields" items="${reportconfig.headermap}" />
		<a href="javascript:submit()"><img src="resources/images/refresh_32.png" alt="Отчет" /></a>
		
	</form:form>
</div>

<div id="viewreport" name="viewreport"></div>