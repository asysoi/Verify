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
		  		    $("#codecountrytarget")
							.val('${fscert.codecountrytarget}');
					$("#otd_id")
							.val('${fscert.otd_id}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
</script>

<h2 align="center"><b>СЕРТИФИКАТ СВОБОДНОЙ ПРОДАЖИ</b></h2>
<h2 align="center"><b>CERTIFICATE OF FREE SALE</b></h2>
<h3 align="center"><b>${fscert.branch.name}</b></h3>
<h3 align="center"><b>${fscert.branch.address}, Республика Беларусь</b></h3>
<h3 align="center"><b>телефон: ${fscert.branch.work_phone}, факс: ${fscert.branch.cell_phone}, e-mail: ${fscert.branch.email} </b></h3>

<form:form id="fscertedit" method="POST" modelAttribute="fscert">
<form:hidden path="id"/>

<table>
<tr>
<td align="left">Номер сертификата:<form:input path="certnumber" id="certnumber" size="15"/><a
					href="javascript:clearelement($('#certnumber'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td> 
<td align="right">Дата сертификата: <form:input path="datecert" id="datecert"
						class="datepicker" size="8" placeholder="с" /> 
					<a href="javascript:clearelement($('#datecert'));"> 
					<img src="resources/images/delete-16.png" alt="удл." />	</a></td>
</tr>
<tr>
<td>Дубликат сертификата:</td>
 <td><form:select path="parentnumber" id="parentnumber" /><a
					href="javascript:clearelement($('#parentnumber'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a>
				
</td>
</tr>
<tr>
<td>Выдан для представления в:<td>
<td> <form:select path="codecountrytarget"
						
						items="${countries}" id="codecountrytarget" /><a
					href="javascript:clearelement($('#codecountrytarget'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></p>
</td>
</tr>
<tr>
<td>Экспортер: </td>
<td>${fscert.exporter.name}, ${fscert.exporter.address}</td>
</tr>
<tr>
|<td>Производитель:</td>
<td>${fscert.producer.name}, ${fscert.producer.address}</td>	
</table>

<table>
<tr>
<td><form:textarea path="confirmation" id="confirmation" /></td>
</tr>
<tr>
<td><form:textarea path="declaration" id="declaration" /></td>
</tr>

<tr>
<td>Срок действия сертификата: 
				<form:input path="dateissue" id="dateissue"
						class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; 
				<form:input	path="dateexpiry" id="dateexpiry" class="datepicker"
						size="8" placeholder="по" />
</td>
</tr>
<tr>
<td>Количество листов сертификата <form:input path="listscount" id="listscount" class="doplist" 
						size="8" /> 
</td>
</tr>
</table>



<table>
<tr>
		  <td>Эксперт</td>
				<td><form:input path="expert.name" id="expertname" /></td>
</tr>
<tr>				
				<td>Должность эксперта</td>
				<td><form:input path="expert.job" id="expertjob" /></td>
</tr>
<tr>
				<td>Подписан</td>
				<td><form:input path="signer.name" id="signername" /></td>
</tr>				
				<td>Должность подписанты</td>
				<td><form:input path="signer.job" id="signerjob" /></td>
</tr>
</table>


<table>
 <tr>
		        <td>Товарные позиции</td> 
				<td>
				 <table> 
				    <c:forEach items="${fscert.products}" var="product">
				        <tr> 
				        <td> ${product.numerator} </td>
				        <td> ${product.tovar} </td>
				        </tr>
			       </c:forEach>
				</table>
				</td>
			</tr>
				<td>Бланки сертификата</td>
				<td>
				 <table> 
				    <c:forEach items="${fscert.blanks}" var="blank">
				        <tr> 
				        <td> ${blank.page} </td>
				        <td> ${blank.blanknumber} </td>
				        </tr>
			       </c:forEach>
				</table>
				
               </td>
			
</table>


	<fieldset>
		<legend class="grp_title">Основные данные</legend>
		 

		<table class="filter">
		</table>
	</fieldset>
	
	<fieldset>
		<legend class="grp_title">Производство</legend>
		<table class="filter">
			
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Выдан</legend>

	
	</fieldset>

	<fieldset>
		<legend class="grp_title">Товарные позиции</legend>

		
	</fieldset>
</form:form>