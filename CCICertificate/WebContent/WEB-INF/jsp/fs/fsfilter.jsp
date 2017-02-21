<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

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
							"${viewfsfilter.viewcertificate.datecertfrom}");
					$("#dateto").datepicker("setDate",
							"${viewfsfilter.viewcertificate.datecertto}");
					$("#otd_id")
							.val('${viewfsfilter.viewcertificate.otd_id}');
		  		    $("#codecountrytarget")
							.val('${viewfilter.viewcertificate.codecountrytarget}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="ffilter" method="POST" commandName="viewfsfilter">

	<fieldset>
		<legend class="grp_title">Основные данные</legend>

		<table class="filter">
			<tr>
				<td>Номер сертификата</td>
				<td><form:input path="viewcertificate.certnumber" id="certnumber" /><a
					href="javascript:clearelement($('#certnumber'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Номер бланка</td>
				<td><form:input path="viewcertificate.blanknumber" id="blanknumber" /><a
					href="javascript:clearelement($('#blanknumber'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Дата сертификата</td>
				<td><form:input path="viewcertificate.datecertfrom" id="datecertfrom"
						class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; 
						<form:input	path="viewcertificate.datecertto" id="datecertto" class="datepicker"
						size="8" placeholder="по" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Кол. доплистов</td>
				<td><form:input path="viewcertificate.countfrom" id="countfrom" class="doplist" 
						size="8" placeholder="от" />&nbsp;-&nbsp; 
						<form:input	path="viewcertificate.countto" id="countto" class="doplist" 
						size="8" placeholder="до" /> <a
					href="javascript:clearelement($('.doplist'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<td>Для предоставления в</td>
				<td><form:select path="viewcertificate.codecountrytarget"
						items="${countries}" id="codecountrytarget" /><a
					href="javascript:clearelement($('#codecountrytarget'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
		</table>
	</fieldset>
	<fieldset>
		<legend class="grp_title">Производство</legend>
		<table class="filter">
			<tr>
				<td>Экспортер</td>
				<td><form:input path="viewcertificate.exportername" id="exportername" /><a
					href="javascript:clearelement($('#exportername'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Производитель</td>
				<td><form:input path="viewcertificate.producername" id="producername" /><a
					href="javascript:clearelement($('#producername'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Адрес экспортера</td>
				<td><form:input path="viewcertificate.exporteraddress" id="exporteraddress" /><a
					href="javascript:clearelement($('#exporteraddress'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Адрес производителя</td>
				<td><form:input path="viewcertificate.produceraddress" id="produceraddress" /><a
					href="javascript:clearelement($('#produceraddress'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Выдан</legend>

		<table class="filter">
			<security:authorize ifAnyGranted="ROLE_EXPERT">
			<tr>
				<td>Отделение</td>
				<td>
				      <form:select path="viewcertificate.otd_id"
						items="${departments}" id="otd_id" />
					  <a href="javascript:clearelement($('#otd_id'));"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			</td>
			</tr>
			</security:authorize>
						<tr>
				<td>Эксперт</td>
				<td><form:input path="viewcertificate.expertname" id="expertname" /><a
					href="javascript:clearelement($('#expertname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Подписан</td>
				<td><form:input path="viewcertificate.signername" id="signername" /><a
					href="javascript:clearelement($('#signername'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Товарные позиции</legend>

		<table class="filter">
			<tr>
				<td>Продукция</td>
				<td><form:input path="viewcertificate.productname" id="productname" style="width: 380px"/><a
					href="javascript:clearelement($('#productname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>
</form:form>