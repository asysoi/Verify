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
				
				fillindepartment( ${employeefilter.viewemployee.id_otd} );
				
				$(".datepicker").datepicker("option", "dateFormat",
						'dd.mm.yy');
				$("#bday").datepicker("setDate",
						"${employee.bday}");
				$("#id_department")
  	  					.val('${employee.department.id}');
				$("#id_otd")
						.val('${employee.department.id_otd}');
			});

 });

 function clearelement(element) {
	element.val('');
 }
 
 function fillindepartment(id_otd) {
	 $('#id_department option').remove();
	 <c:forEach items="${departments}" var="deplist">
	     if ( id_otd == ${deplist.key} ) {
	        <c:forEach items="${deplist.value}" var="listElement" >
	            $('#id_department').append('<option value="${listElement.key}">${listElement.value}</option>');
	        </c:forEach>
	     }
	   </c:forEach>
	$("#id_department")
			.val('${employee.department.id}');
 }
 
 function selectBranch() {
	 fillindepartment($("#id_otd").val());	 
 }
 
</script>

<form:form id="femployee" method="POST" commandName="employee">

	<fieldset>
		<legend class="grp_title">Основные данные</legend>
		<table class="filter">
			<tr>
				<td>Фамилия</td>
				<td><form:input path="lastname" id="lastname"
						size="18" /><a
					href="javascript:clearelement($('#lastname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Имя</td>
				<td><form:input path="firstname" id="firstname"
						size="18" /><a
					href="javascript:clearelement($('#firstname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Должность</td>
				<td><form:input path="job" id="job" /><a
					href="javascript:clearelement($('#job'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Дата рождения</td>
				<td><form:input path="bday" id="bday"
						class="datepicker" size="8" placeholder="с" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
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
				<td><form:input size="12" path="phone" id="phone" /><a
					href="javascript:clearelement($('#phone'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Адрес электронной почты</td>
				<td><form:input size="12" path="email" id="email" /><a
					href="javascript:clearelement($('#email'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Статус</td>
				<td><form:checkbox path="active" value="${employee.active}" id="active" /><a
					href="javascript:clearelement($('#active'));"></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Адрес</legend>

		<table class="filter">
			<tr>
				<td>Отделение</td>
				<td>
				      <form:select path="department.id_otd"
						items="${branches}" id="id_otd" 
						onChange="javaScript:selectBranch();"
						/>
					  <a href="javascript:clearelement($('#id_otd')); javascript:clearelement($('#id_department'));"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			    </td>
			</tr>
			<tr>
				<td>Подразделение</td>
				<td>
				      <form:select path="department.id"
						 id="id_department"/>
					  <a href="javascript:clearelement($('#id_department'));"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			    </td>
			
			</tr>
		</table>


	</fieldset>
</form:form>