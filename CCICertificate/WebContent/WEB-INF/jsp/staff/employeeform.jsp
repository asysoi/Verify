<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>

function refreshName() {
	var name = $("#lastname").val().trim() + " "
	   + ($("#firstname").val().trim() == "" ? "" : ($("#firstname").val().trim().charAt(0) + "."))
	   + ($("#middlename").val().trim() == "" ? "" : ($("#middlename").val().trim().charAt(0) + "."));
	$("#name").val(name);
} 

$(function() {
	
	$(".datepicker").datepicker({
		changeMonth : true,
		changeYear : true
	});

	$("document").ready(
			function() {
				
				fillindepartment( ${editemployee.department.id_otd} );
				
				$(".datepicker").datepicker("option", "dateFormat",
						'dd.mm.yy');
				$("#bday").datepicker("setDate",
						"${editemployee.bday}");
				$("#id_department")
  	  					.val('${editemployee.department.id}');
				$("#id_otd")
						.val('${editemployee.department.id_otd}');
			});

 });

 function clearelement(element) {
	element.val('');
 }
 
 function cleardepertment() {
		$("#id_otd").val('');
		$('#id_department option').remove();
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
	$("#id_department").width($("#id_otd").width());
 }
 
 function selectBranch() {
	 fillindepartment($("#id_otd").val());	 
 }

</script>

<form:form id="femployee" method="POST" commandName="editemployee">
    <form:hidden path="id"/>  
	<fieldset>
		<legend class="grp_title">Персональные данные</legend>
		<table class="filter">
			<tr>
				<td>Фамилия</td>
				<td><form:input path="lastname" id="lastname"
						size="18" class="required"/><a
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
				<td>Отчество</td>
				<td><form:input path="middlename" id="middlename"
						size="18" /><a
					href="javascript:clearelement($('#middlename'));"> <img
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
		<legend class="grp_title">Подпись и должность</legend>
		<table class="filter">
			<tr>
				<td>ФИО</td>
				<td><form:input path="name" id="name"
						size="25" class="required" readonly="true"/>
						<a	href="javascript:refreshName();"> 
						<i class="glyphicon glyphicon-refresh"></i>
				        </a>
						<a	href="javascript:clearelement($('#name'));"> 
						<img src="resources/images/delete-16.png" alt="удл." />
				        </a>
				</td>
			</tr>
			<tr>
				<td>Должность</td>
				<td><form:input path="job" id="job"
						size="40" /><a
					href="javascript:clearelement($('#job'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>	
			<tr>
				<td>ФИО(English)</td>
				<td><form:input path="enname" id="enname"
						size="25" />
						<a	href="javascript:translit($('#name'), $('#enname'));"> 
						<i class="glyphicon glyphicon-refresh"></i>
				        </a>
						<a	href="javascript:clearelement($('#enname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Должность(English)</td>
				<td><form:input path="enjob" id="enjob" size="40" />
				<a href="javascript:clearelement($('#enjob'));"> <img
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
				<td><form:checkbox path="active" value="${editemployee.active}" id="active" /><a
					href="javascript:clearelement($('#active'));"></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Структурное подразделение</legend>

		<table class="filter">
			<tr>
				<td>Отделение</td>
				<td>
		          <form:select path="department.id_otd"
						items="${branches}" id="id_otd" 
						onChange="javaScript:selectBranch();"/>
					  <a href="javascript:cleardepertment();"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			    </td>
			</tr>
			<tr>
				<td>Подразделение</td>
				<td>
				      <form:select path="department.id"
						 id="id_department" class="required" size="5"/>
					  <a href="javascript:clearelement($('#id_department'));"> 
					  <img src="resources/images/delete-16.png" alt="удл." />	</a>
			    </td>
			
			</tr>
		</table>


	</fieldset>
</form:form>