<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	$(function() {
		$("document").ready(
				function() {
					$("#codecountry")
							.val('${viewfilter.viewclient.codecountry}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="ffilter" method="POST" commandName="viewfilter">

	<fieldset>
		<legend class="grp_title">Основные данные</legend>

		<table class="filter">
			<tr>
				<td>Наименование</td>
				<td><form:input path="viewclient.name" id="name" /><a
					href="javascript:clearelement($('#name'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Учетный номер плательшика</td>
				<td><form:input path="viewclient.unp" id="unp" /><a
					href="javascript:clearelement($('#unp'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Код ОКПО</td>
				<td><form:input path="viewclient.okpo" id="okpo"
						size="20" /><a
					href="javascript:clearelement($('#okpo'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Контактная информафция</legend>
		<table class="filter">
			<tr>
				<td>Телефон</td>
				<td><form:input path="viewclient.work_phone" id="work_phone" /><a
					href="javascript:clearelement($('#work_phone'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Мобильный телефон</td>
				<td><form:input path="viewclient.cell_phone" id="cell_phone" /><a
					href="javascript:clearelement($('#cell_phone'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Адрес электронной почты</td>
				<td><form:input path="viewclient.email" id="email" /><a
					href="javascript:clearelement($('#email'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Адрес</legend>

		<table class="filter">
			<tr>
				<td>Страна</td>
				<td><form:select path="viewclient.codecountry"
						items="${countries}" id="codecountry" /><a
					href="javascript:clearelement($('#codecountry'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Город</td>
				<td><form:input path="viewclient.city" id="city" /><a
					href="javascript:clearelement($('#city'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Улица</td>
				<td><form:input path="viewclient.line" id="line" /><a
					href="javascript:clearelement($('#line'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Банк</legend>

		<table class="filter">
			<tr>
				<td>Расчетный cчет</td>
				<td><form:input path="viewclient.account" id="account" /><a
					href="javascript:clearelement($('#account'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Наименование</td>
				<td><form:input path="viewclient.bname" id="bname" /><a
					href="javascript:clearelement($('#bname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr />
		</table>
	</fieldset>
</form:form>