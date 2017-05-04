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
	$( "#tabs" ).tabs();
	
	$(".datepicker").datepicker({
		changeMonth : true,
		changeYear : true
	});

	$("document").ready(
			function() {
				
				fillindepartment(${editemployee.department.id_otd});
				
				$(".datepicker").datepicker("option", "dateFormat",
						'dd.mm.yy');
				$("#bday").datepicker("setDate",
						"${editemployee.bday}");
				$("#id_department")
  	  					.val('${editemployee.department.id}');
				$("#id_otd")
						.val('${editemployee.department.id_otd}');
	});
	
	var lastSelection;
	
	jQuery("#locales").jqGrid({
		url: "emplocales.do",
		editurl: "emplocaleupdate.do",
		datatype: "xml",
		mtype: "GET",
		height: '50%',
		width : null,
		shrinkToFit : false,
			colModel:[
				{label:'Язык',name:'locale', index:'locale', width:125, editable: true, sortable:true, edittype:"select",editoptions:{value:"${languages}"}},
	    		{label:'ФИО', name:'lname', index:'lname', width:300, editable: true, sortable:false},
	    		{label:'Должность', name:'ljob', index:'ljob', width:120, editable: true, sortable:false}
	   	],
	    rowNum: 5,
	    rowList:[5,10,15],
	   	sortname: 'locale',
	   	sortorder: 'asc',
			viewrecords: true,
			pager: jQuery('#localespager'),
	    gridview: true,
		autoencode: true,
			ondblClickRow : function editRow(id) {
			 var grid = $("#locales");
         	 if (id && id !== lastSelection) {
            	 grid.jqGrid('restoreRow',lastSelection);
             	 lastSelection = id;
         	 }
         	 grid.jqGrid('editRow',id, {keys: true} );
        }
	});
	
	$('#locales').jqGrid('navGrid', "#localespager", {                
		search: false, 
		add: false,
		edit: false,	
		del: false,
		refresh: false,
		view: false
	}).navButtonAdd('#localespager',{
	    caption: '',	
        buttonicon: 'ui-icon-plus',
        onClickButton: function(id) {
            var lastsel = id;
            $.ajaxSetup({async:false});
    		$.get("emplocaleadd.do");
            jQuery("#locales").trigger('reloadGrid');
        },
        title: "Добавить ФИО для нового языка",
        position: "last"
    }).navButtonAdd('#localespager',{
	    caption: '',	
        buttonicon: 'ui-icon-closethick',
        onClickButton: function(event) {
        	 var grid = $("#locales");
             var id = grid.jqGrid('getGridParam','selrow');
             var recs = grid.getGridParam("reccount");
        	 
        	 if (recs > 0 ) {
 		     	if (id) { 
                	$.ajaxSetup({async:false});
         			$.get("emplocaledel.do?id="+id);
 		     	} else { 
 		     		$("#dialog-message" ).dialog("option", "title", 'Удаление продукта');
 		     		$("#message").text("Строка не выбрана. Выберете строку для удаления.");
						$("#dialog-message").dialog("open");
	            }
    	        grid.trigger('reloadGrid');
        	 }
        },
        title: "Удалить выбранную запись локализации",
        position: "last"
    });
	
	grid = $("#locales");
	grid.jqGrid('gridResize', {minWidth: 450, minHeight: 250});
	$("#locales_left", "#locales").width(250);
	
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
    
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Персональные данные</a></li>
    <li><a href="#tabs-2">Структурное подразделение</a></li>
    <li><a href="#tabs-3">Локализация</a></li>
  </ul>
    
  <div id="tabs-1">	  
      
		<table class="filter">
			<tr>
				<td>Фамилия</td>
				<td><form:input path="lastname" id="lastname"
						size="18" class="required"/></td>
				<td>Имя</td>
				<td><form:input path="firstname" id="firstname"	size="18" /></td>
			</tr>
			<tr>
				<td>Отчество</td>
				<td><form:input path="middlename" id="middlename"
						size="18" /></td>
			</tr>
			<tr>
				<td>ФИО</td>
				<td><form:input path="name" id="name"
						size="25" class="required" readonly="true"/>
						<a	href="javascript:refreshName();"> 
						<i class="glyphicon glyphicon-refresh"></i>
				        </a>
						
				</td>
			</tr>
			<tr>
				<td>Дата рождения</td>
				<td><form:input path="bday" id="bday"
						class="datepicker" size="12" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
		
		<fieldset>
		<legend class="grp_title">Контакты</legend>        
  
		<table class="filter">
			<tr>
				<td>Телефон</td>
				<td><form:input size="12" path="phone" id="phone" /></td>
			</tr>
			<tr>
				<td>Адрес электронной почты</td>
				<td><form:input size="12" path="email" id="email" /></td>
			</tr>
		</table>
		</fieldset>
	</div>
	
	<div id="tabs-2">
		<table class="filter">
			<tr>
				<td>Должность</td>
				<td><form:input path="job" id="job"
						size="70" /></td>
			</tr>	
		
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
	</div>
	
	<div id="tabs-3">
	   <table id="locales"></table>
	   <div id="localespager"></div>
	</div>
</div>
</form:form>