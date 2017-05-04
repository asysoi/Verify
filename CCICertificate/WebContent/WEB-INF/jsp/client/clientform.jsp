<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script> 
	$(function() {
		$( "#tabs" ).tabs();
		
		$("document").ready(
				function() {
					$("#codecountry")
						.val('${client.codecountry}');
					$("#bcodecountry")
						.val('${client.bcodecountry}');
		});
	
		var lastSelection;
		
		jQuery("#locales").jqGrid({
    		url: "clientlocales.do",
    		editurl: "clientlocaleupdate.do",
    		datatype: "xml",
    		mtype: "GET",
    		height: '60%',
			width : null,
			shrinkToFit : false,
   			colModel:[
   				{label:'Язык',name:'locale', index:'locale', width:125, editable: true, sortable:true, edittype:"select",editoptions:{value:"${languages}"}},
   	    		{label:'Наименование', name:'lname', index:'lname', width:300, editable: true, sortable:false},
   	    		{label:'Адрес', name:'laddress', index:'laddress', width:300, editable: true, sortable:false}
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
        		$.get("clientlocaleadd.do");
                jQuery("#locales").trigger('reloadGrid');
            },
            title: "Добавить наименовния для нового языка",
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
             			$.get("clientlocaledel.do?id="+id);
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
		grid.jqGrid('gridResize', {minWidth: 450, minHeight: 550});
		$("#locales_left", "#locales").width(250);
		
	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="fclient" method="POST" commandName="client">

<div id="tabs">
  <ul>
    <li><a href="#tabs-1">О компании</a></li>
    <li><a href="#tabs-2">Банковские реквизиты</a></li>
    <li><a href="#tabs-3">На иностранном языке</a></li>
  </ul>
  
  <div id="tabs-1">	
    <form:hidden path="id" id="id" />
    <form:hidden path="version" id="version" />
    
    <fieldset>
		<legend class="grp_title">Неименование</legend>        
		<table class="filter">
		    <tr>	
				<td><form:input path="name" id="name" size="70"/> </td>
			</tr>
		</table>
	</fieldset>
		
	<fieldset>
		<legend class="grp_title">Реквизиты и Контакты</legend>
		<table class="filter">	
			<tr>
				<td>УНП </td>
				<td><form:input path="unp" id="unp" /></td>
				<td>Код ОКПО </td>
				<td><form:input path="okpo" id="okpo"
						size="20" /></td>
			</tr>
			<tr>
				<td>Телефон </td>
				<td><form:input path="phone" id="phone" /></td>
				<td>Факс </td>
				<td><form:input path="cell" id="cell"/></td>
			</tr>
			<tr>
				<td>Email </td>
				<td><form:input path="email" id="email" /></td>
			</tr>
		
		</table>
	</fieldset>  

    <fieldset>
		<legend class="grp_title">Адрес</legend>
		<table class="filter">
			<tr>
				<td>Страна <form:select path="codecountry"
						items="${countries}" id="codecountry"/><a
					href="javascript:clearelement($('#codecountry'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a>
				</td>
			</tr>
	    </table>		
	    <table class="filter">		
			<tr>	
				<td>Индекс </td>
				<td><form:input path="cindex" id="cindex" /></td>
			<tr>	
				<td>Город </td>
				<td><form:input path="city" id="city" size="20"/></td>
			</tr>
			<tr>	
				<td>Улица </td>
				<td><form:input path="street" id="street" size="62"/></td>
			</tr>
	    </table>		
	    <table class="filter">		
			<tr>
				<td>Номер дома </td>
				<td><form:input path="building" id="building" size="6"/></td>
				<td>Номер офиса </td>
				<td><form:input path="office" id="office" size="10" /></td>
			</tr>
		</table>
		<table class="filter">		
			<tr>
				<td>Адрес
				<a href="javascript:makeAddress($('#address'));"> 
				   <i class="glyphicon glyphicon-refresh" align="center"/>
				</a> 
				</td>
				<td><form:textarea rows="2" cols="65" path="address" id="address"/></td>
			</tr>
		</table>
    </fieldset>		
   </div>

   <div id="tabs-2">
		<table class="filter">
			<tr>
				<td>Расчетный cчет</td>
				<td><form:input path="account" id="account"/></td>
				<td></td>
				<td></td>				
			</tr>
			<tr>
				<td>Наименование банка</td>
				<td><form:input path="bname" id="bname"/></td>
				<td></td>
				<td></td>				
			</tr>
		</table>
		<table class="filter">	
			<tr>
				<td>Страна
				<form:select path="bcodecountry"
						items="${countries}" id="bcodecountry" /><a
					href="javascript:clearelement($('#bcodecountry'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
		<table class="filter">		
			<tr>	
				<td>Индекс</td>
				<td><form:input path="bindex" id="bindex" /></td>
				<td>Город</td>
				<td><form:input path="bcity" id="bcity" /></td>
			</tr>
			<tr>
				<td>Улица</td>
				<td><form:input path="bstreet" id="bstreet" /></td>
			</tr>
			<tr>
				<td>Номер дома</td>
				<td><form:input path="bbuilding" id="bbuilding" /></td>
				<td>Номер офиса</td>
				<td><form:input path="boffice" id="boffice" /></td>
			</tr>			
		</table>
	</div>
	
	<div id="tabs-3">
	   <table id="locales"></table>
	   <div id="localespager"></div>
	</div>
</div>	
	
</form:form>