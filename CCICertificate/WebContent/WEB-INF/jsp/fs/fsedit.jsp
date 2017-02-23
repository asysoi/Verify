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
 		  		        $("#codecountrytarget")
							.val('${viewfsfilter.viewcertificate.codecountrytarget}');
					$("#str_otd_id")
							.val('${viewfsfilter.viewcertificate.str_otd_id}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="ffilter" method="POST" modelAttribute="fscert">

	<fieldset>
		<legend class="grp_title">Основные данные</legend>

		<table class="filter">
			<tr>
				<td>Номер сертификата</td>
				<td><form:input path="certnumber" id="certnumber" /><a
					href="javascript:clearelement($('#certnumber'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Дата сертификата</td>
				<td><form:input path="dateissue" id="dateisse"
						class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; 
				<form:input	path="dateexpiry" id="dateexpiry" class="datepicker"
						size="8" placeholder="по" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Кол. доплистов</td>
				<td><form:input path="listscount" id="listscount" class="doplist" 
						size="8" placeholder="от" />&nbsp;-&nbsp; 
						<a href="javascript:clearelement($('.doplist'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<td>Для предоставления в</td>
				<td><form:select path="codecountrytarget"
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
				<td><form:input path="exporter.name" id="exportername" /><a
					href="javascript:clearelement($('#exportername'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Производитель</td>
				<td><form:input path="producer.name" id="producername" /><a
					href="javascript:clearelement($('#producername'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Адрес экспортера</td>
				<td><form:input path="exporter.address" id="exporteraddress" /><a
					href="javascript:clearelement($('#exporteraddress'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Адрес производителя</td>
				<td><form:input path="producer.address" id="produceraddress" /><a
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
				      <form:select path="otd_id"
						items="${departments}" id="otd_id" />
					  <a href="javascript:clearelement($('#str_otd_id'));"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			</td>
			</tr>
			</security:authorize>
						<tr>
				<td>Эксперт</td>
				<td><form:input path="expert.name" id="expertname" /><a
					href="javascript:clearelement($('#expertname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Подписан</td>
				<td><form:input path="signer.name" id="signername" /><a
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
				<td><form:input path="products" id="productname" style="width: 380px"/><a
					href="javascript:clearelement($('#productname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
				<td>Экспортер</td>
				<td><form:input path="blanks" id="blanks" /><a
					href="javascript:clearelement($('#exporter_name'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			
		</table>
	</fieldset>
</form:form>