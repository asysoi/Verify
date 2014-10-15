<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	$(function() {

		$(".datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(
				function() {
					$(".datepicker").datepicker("option", "dateFormat",
							'dd.mm.yy');
					$("#datefrom").datepicker("setDate",
							"${viewfilter.viewcertificate.datefrom}");
					$("#dateto").datepicker("setDate",
							"${viewfilter.viewcertificate.dateto}");
					$("#stranapr")
							.val('${viewfilter.viewcertificate.stranapr}');
					$("#otd_name")
							.val('${viewfilter.viewcertificate.otd_name}');
					$("#forms").val('${viewfilter.viewcertificate.forms}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="ffilter" method="POST" commandName="viewfilter">
	<table class="filter">
	
	<fieldset>
		<legend>Основные данные</legend>
		<tr>
			<td>Форма сертификата</td>
			<td><form:select path="viewcertificate.forms" items="${forms}"
					id="forms" /><a href="javascript:clearelement($('#forms'));"> X
			</a></td>
		</tr>
		<tr>
			<td>Номер сертификата</td>
			<td><form:input path="viewcertificate.nomercert" id="nomercert" /><a
				href="javascript:clearelement($('#nomercert'));"> X </a></td>
		</tr>
		<tr>
			<td>Номер бланка</td>
			<td><form:input path="viewcertificate.nblanka" id="nblanka" /><a
				href="javascript:clearelement($('#nblanka'));"> X </a></td>
		</tr>
		<tr>
			<td>Дата сертификата</td>
			<td><form:input path="viewcertificate.datefrom" id="datefrom"
					class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; <form:input
					path="viewcertificate.dateto" id="dateto" class="datepicker"
					size="8" placeholder="по" /> <a
				href="javascript:clearelement($('.datepicker'));"> X </a></td>
		</tr>
	</table>
	
	<fieldset>
		<legend>Экспортеры и Импортеры</legend>
		<table class="filter">
			<tr>
				<td>Экспортер</td>
				<td><form:input path="viewcertificate.kontrp" id="kontrp" /><a
					href="javascript:clearelement($('#kontrp'));"> X </a></td>
			</tr>
			<tr>
				<td>Адрес экспортера</td>
				<td><form:input path="viewcertificate.adress" id="adress" /><a
					href="javascript:clearelement($('#adress'));"> X </a></td>
			</tr>
			<tr>
				<td>Грузоотправитель</td>
				<td><form:input path="viewcertificate.expp" id="expp" /><a
					href="javascript:clearelement($('#expp'));"> X </a></td>
			</tr>
			<tr>
				<td>Адрес грузоотправителя</td>
				<td><form:input path="viewcertificate.expadress" id="expadress" /><a
					href="javascript:clearelement($('#expadress'));"> X </a></td>
			</tr>
			<tr>
				<td>Иимпортер</td>
				<td><form:input path="viewcertificate.poluchat" id="poluchat" /><a
					href="javascript:clearelement($('#poluchat'));"> X </a></td>
			</tr>
			<tr>
				<td>Адрес импортера</td>
				<td><form:input path="viewcertificate.adresspol" id="adresspol" /><a
					href="javascript:clearelement($('#adresspol'));"> X </a></td>
			</tr>
			<tr>
				<td>Грузопролучатель</td>
				<td><form:input path="viewcertificate.importer" id="importer" /><a
					href="javascript:clearelement($('#importer'));"> X </a></td>
			</tr>
			<tr>
				<td>Адрес грузополучателя</td>
				<td><form:input path="viewcertificate.adressimp" id="adressimp" /><a
					href="javascript:clearelement($('#adressimp'));"> X </a></td>
			</tr>
		</table>
		</fieldset>

	<fieldset>
		<legend>Территории</legend>
	
		<table class="filter">
			<tr>
				<td>Маршрут транспорта</td>
				<td><form:input path="viewcertificate.marshrut" id="marchrut" /><a
					href="javascript:clearelement($('#marshrut'));"> X </a></td>
			</tr>
			<tr>
				<td>Отделение</td>
				<td><form:select path="viewcertificate.otd_name"
						items="${departments}" id="otd_name" /><a
					href="javascript:clearelement($('#otd_name'));"> X </a></td>
			</tr>
			<tr>
				<td>Страна предоставления</td>
				<td><form:select path="viewcertificate.stranapr"
						items="${countries}" id="stranapr" /><a
					href="javascript:clearelement($('#stranapr'));"> X </a>
				</td>
			</tr>
			<tr>
				<td>Отметка</td>
				<td><form:input path="viewcertificate.otmetka" id="otmetka" /><a
					href="javascript:clearelement($('#otmetka'));"> X </a></td>
			</tr>
			</table>
			</fieldset>

	<fieldset>
		<legend>Товарные позиции</legend>
			
			<table class="filter">			
			<tr>
				<td>Товар</td>
				<td><form:input path="viewcertificate.tovar" id="tovar" /><a
					href="javascript:clearelement($('#tovar'));"> X </a></td>
			</tr>
			<tr>
				<td>Критерий происхождения</td>
				<td><form:input path="viewcertificate.kriter" id="kriter" /><a
					href="javascript:clearelement($('#kriter'));"> X </a></td>
			</tr>
			<tr>
				<td>Счет фактура</td>
				<td><form:input path="viewcertificate.schet" id="schet" /><a
					href="javascript:clearelement($('#schet'));"> X </a></td>
			</tr>

			<tr />
		</table>
		</fieldset>
</form:form>