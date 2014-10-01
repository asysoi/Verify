<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	function clear() {
		$('input').val('');
		$('select').val('');
	}

	function reset() {
		$('#ffilter')[0].reset();
	}

	function submit() {
		url = $("#ffilter").attr("action");
		$.post(url, $("#ffilter").serialize());
		goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');		
		$("#pview").dialog("close");
	}

	function close() {
		$("#pview").dialog("close");
	}
</script>


<form:form id="ffilter" method="POST" commandName="viewfilter">
	<table class="filter">
		<tr>
			<td>Грузоотправитель/экспортер</td>
			<td><form:select path="condition.kontrp" items="${operators}" /></td>
			<td><form:input path="certificate.kontrp" /></td>

		</tr>
		<tr>
			<td>Адрес грузоотправителя/экспортера</td>
			<td><form:select path="condition.adress" items="${operators}" /></td>
			<td><form:input path="certificate.adress" /></td>
		</tr>
		<td>Грузопролучатель/импортер</td>
		<td><form:select path="condition.poluchat" items="${operators}" /></td>
		<td><form:input path="certificate.poluchat" /></td>

		</tr>
		<tr>
			<td>Адрес грузополучателя/импортера</td>
			<td><form:select path="condition.adresspol" items="${operators}" /></td>
			<td><form:input path="certificate.adresspol" /></td>
		</tr>

		<tr>
			<td>Маршрут транспорта</td>
			<td><form:select path="condition.marshrut" items="${operators}" /></td>
			<td><form:input path="certificate.marshrut" /></td>
		</tr>
		<tr>
			<td>Отделение</td>
			<td><form:select path="condition.otd_name" items="${operators}" /></td>
			<td><form:select path="certificate.otd_name"  items="${departments}"/></td>
		</tr>
		<tr>
			<td>Номер сертификата</td>
			<td><form:select path="condition.nomercert" items="${operators}" /></td>
			<td><form:input path="certificate.nomercert" /></td>
		</tr>
		<tr>
			<td>Номер бланка</td>
			<td><form:select path="condition.nblanka" items="${operators}" /></td>
			<td><form:input path="certificate.nblanka" /></td>
		</tr>
		
		
		<tr>
			<td>Страна предоставления</td>
			<td><form:select path="condition.stranapr" items="${operators}" /></td>
			<td><form:input path="certificate.stranapr" /></td>
		</tr>

		<tr>
			<td>Отметка</td>
			<td><form:select path="condition.otmetka" items="${operators}" /></td>
			<td><form:input path="certificate.otmetka" /></td>
		</tr>
		<tr>
			<td>Товар</td>
			<td><form:select path="condition.tovar" items="${operators}" /></td>
			<td><form:input path="certificate.tovar" /></td>
		</tr>
		<tr />
	</table>

	<div style="text-align: center">

		<button type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			role="button">
			<span class="ui-button-text"><a href="javascript: submit();">применить</a></span>
		</button>
		<button type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			role="button">
			<span class="ui-button-text"> <a href="javascript:clear();">очистить</a></span>
		</button>

		<button type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			role="button">
			<span class="ui-button-text"> <a href="javascript:reset();">отменить
					изменения</a></span>
		</button>

		<button type="button"
			class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
			role="button">
			<span class="ui-button-text"> <a href="javascript:close();">закрыть</a></span>
		</button>

	</div>

</form:form>