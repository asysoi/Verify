<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<style>
div.row {
   margin: 10px;
}
</style>

<script>
	$(function() {

		$(".datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(
				function() {
					$("#fsview").dialog({
						autoOpen : false
					});
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

	function openClients() {
        link="sclients.do?pagesize=5";
		$("#fsview").load(link);
		$("#fsview").dialog("option", "title", 'Список компаний');
		$("#fsview").dialog("option", "width", 1200);
		$("#fsview").dialog("option", "height", 520);
		$("#fsview").dialog("option", "modal", true);
		$("#fsview").dialog("option", "resizable", true );
		$("#fsview").dialog( "option", "position", { my: "center",  at: "center", of:window} );
		$("#fsview").dialog("open");
	}
	
	function updateClients(link) {
    	$("#fsview").load(link);
	}
	
</script>

<c:if test="${not empty error}">
<div id="error" class="error">${error}</div>
</c:if>  

<h3 align="center"><b>СЕРТИФИКАТ СВОБОДНОЙ ПРОДАЖИ</b></h3>
<h4 align="center"><b>${fscert.branch.name}<br>
${fscert.branch.address}, Республика Беларусь<br>
телефон: ${fscert.branch.work_phone}, факс: ${fscert.branch.cell_phone}, e-mail: ${fscert.branch.email} </b></h4>

<form:form id="fscert" method="POST" modelAttribute="fscert">
<form:hidden path="id"/>

<div class="container-fluid">

<div class="row">
<div class="col-md-6">Номер сертификата: <form:input path="certnumber" id="certnumber" size="15"/></div>
<div class="col-md-6">Дата сертификата: <form:input path="datecert" id="datecert" class="datepicker" size="12"/></div> 
</div>

<div class="row">
<div class="col-md-12">Дубликат сертификата: <form:input path="parentnumber" id="parentnumber" /></div>				
</div>

<div class="row">
<div class="col-md-6">Выдан для представления в: <form:select path="codecountrytarget"						
						items="${countries}" id="codecountrytarget" /></div>
</div>
					
<div class="row">
<div class="col-md-1"><a href="javascript:openClients()">Экспортер: </a></div>
<div class="col-md-6" id="exporter">${fscert.exporter.name}, ${fscert.exporter.address}</div>
</div>

<div class="row">
<div class="col-md-1">Производитель:</div>
<div class="col-md-10">${fscert.producer.name}, ${fscert.producer.address}</div>
</div>	

<div class="row">
<div class="col-md-1">Удостоверение:</div>
<div class="col-md-11"><form:textarea rows="6" cols="140" path="confirmation" id="confirmation" /></div>
</div>
<div class="row">
<div class="col-md-1">Декларация:</div>
<div class="col-md-11"><form:textarea rows="6" cols="140" path="declaration" id="declaration" /></div>
</div>

<div class="row">
<div class="col-md-12">Срок действия сертификата c: 
				<form:input path="dateissue" id="dateissue"
						class="datepicker" size="8" placeholder="с" /> по  
				<form:input	path="dateexpiry" id="dateexpiry" class="datepicker"
						size="8" placeholder="по" />
</div>
</div>

<div class="row">
<div class="col-md-12">Количество листов сертификата <form:input path="listscount" id="listscount" class="doplist" 
						size="8" /></div>
</div>

<div class="row">
		  <div class="col-md-1">Эксперт: </div>
		  <div class="col-md-10">${fscert.expert.job} ${fscert.expert.name}</div>
</div>
<div class="row">
		  <div class="col-md-1">Подпись: </div>
		  <div class="col-md-10">${fscert.signer.job} ${fscert.signer.name}</div>
</div>				


<div class="row">
		        <div class="col-md-12">Товарные позиции: <br> 
				 <table> 
				    <c:forEach items="${fscert.products}" var="product">
				        <tr> 
				        <td> ${product.numerator}. </td>
				        <td> ${product.tovar} </td>
				        </tr>
			       </c:forEach>
				</table>
				</div>
</div>

<div class="row">
	<div class="col-md-12">Бланки сертификата: <br>
				 <table> 
				    <c:forEach items="${fscert.blanks}" var="blank">
				        <tr> 
				        <td> ${blank.page}. </td>
				        <td> ${blank.blanknumber} </td>
				        </tr>
			       </c:forEach>
				</table>
   </div>
</div>			


 <form:hidden path="expert.id" id="expertid" />
 <form:hidden path="signer.id" id="signerid" />
 
 <div class="row">
 <div class="col-md-12">
		<button type="submit">Save</button> 
		<button type="reset">Reset</button>
</div>
 </div>
 
</div>

</form:form>

<div id="fsview" name="fsview">
</div>

