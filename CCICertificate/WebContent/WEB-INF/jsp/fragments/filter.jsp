<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form id="ffilter" method="POST" commandName="viewfilter">
	<table class="filter">
		<tr>
			<td>Грузоотправитель/экспортер</td>
			<!-- <td><form:select path="condition.kontrp" items="${operators}" /></td>  -->
			<td><form:input path="viewcertificate.kontrp" /></td>

		</tr>
		<tr>
			<td>Адрес грузоотправителя/экспортера</td>
			<!-- <td><form:select path="condition.adress" items="${operators}" /></td> -->
			<td><form:input path="viewcertificate.adress" /></td>
		</tr>
		<td>Грузопролучатель/импортер</td>
		<!-- <td><form:select path="condition.poluchat" items="${operators}" /></td> -->
		<td><form:input path="viewcertificate.poluchat" /></td>

		</tr>
		<tr>
			<td>Адрес грузополучателя/импортера</td>
			<td><form:input path="viewcertificate.adresspol" /></td>
		</tr>

		<tr>
			<td>Маршрут транспорта</td>
			<td><form:input path="viewcertificate.marshrut" /></td>
		</tr>
		<tr>
			<td>Отделение</td>
			<td><form:select path="viewcertificate.otd_name"  items="${departments}"/></td>
		</tr>
		<tr>
			<td>Номер сертификата</td>
			<td><form:input path="viewcertificate.nomercert" /></td>
		</tr>
		<tr>
			<td>Номер бланка</td>
			<td><form:input path="viewcertificate.nblanka" /></td>
		</tr>
		
		<tr>
			<td>Страна предоставления</td>
			<td><form:input path="viewcertificate.stranapr" /></td>
		</tr>

		<tr>
			<td>Отметка</td>
			<td><form:input path="viewcertificate.otmetka" /></td>
		</tr>
		<tr>
			<td>Товар</td>
			<td><form:input path="viewcertificate.tovar" /></td>
		</tr>
		<tr>
			<td>Критерий происхождения</td>
			<td><form:input path="viewcertificate.kriter" /></td>
		</tr>
		<tr>
			<td>Счет фактура</td>
			<td><form:input path="viewcertificate.schet" /></td>
		</tr>
		
		<tr />
	</table>
	
</form:form>