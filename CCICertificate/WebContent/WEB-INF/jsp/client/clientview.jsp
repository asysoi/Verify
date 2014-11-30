<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="resources/css/cci.css" var="CCICss" />
<link href="${CCICss}" rel="stylesheet" />

<fieldset>
	<legend class="grp_title" style="text-align: left;">Основные данные</legend>

	<table class="filter">
		<tr>
			<td>Наименование:</td>
			<td>${client.name}</td>
		</tr>
		<tr>
			<td>Учетный номер плательшика:</td>
			<td>${client.unp}</td>
		</tr>
		<tr>
			<td>Код ОКПО:</td>
			<td>${client.okpo}</td>
		</tr>
	</table>
</fieldset>

<fieldset>
	<legend class="grp_title" style="text-align: left;">Контактная информафция</legend>
	<table class="filter">
		<tr>
			<td>Телефон:</td>
			<td>${client.work_phone}</td>
			<td>Мобильный телефон:</td>
			<td>${client.cell_phone}</td>
		</tr>
		<tr>
			<td>Адрес электронной почты:</td>
			<td>${client.email}</td>
			<td></td>
			<td></td>
		</tr>
	</table>
</fieldset>

<fieldset>
	<legend class="grp_title" style="text-align: left;">Адрес компании</legend>

	<table class="filter">
		<tr>
			<td>Страна:</td>
			<td>${client.country}</td>
			<td>Индекс:</td>
			<td>${client.cindex}</td>
		</tr>
		<tr>
			<td>Город:</td>
			<td>${client.city}</td>
			<td>Улица:</td>
			<td>${client.line}</td>
		</tr>
		<tr>
			<td>Номер дома:</td>
			<td>${client.building}</td>
			<td>Номер офиса:</td>
			<td>${client.office}</td>
		</tr>
	</table>
</fieldset>

<fieldset>
	<legend class="grp_title" style="text-align: left;">Банковские реквизиты</legend>

	<table class="filter">
		<tr>
			<td>Расчетный счет:</td>
			<td>${client.account}</td>
			<td>УНП банка: </td>
			<td>${client.bunp}</td>
		</tr>
		<tr>
			<td>Страна:</td>
			<td>${client.bcountry}</td>
			<td>Индекс:</td>
			<td>${client.bindex}</td>
		</tr>
		<tr>
			<td>Город:</td>
			<td>${client.bcity}</td>
			<td>Улица:</td>
			<td>${client.bline}</td>
		</tr>
		<tr>
			<td>Номер дома:</td>
			<td>${client.bbuilding}</td>
			<td>Номер офиса:</td>
			<td>${client.boffice}</td>
		</tr>
	</table>
</fieldset>