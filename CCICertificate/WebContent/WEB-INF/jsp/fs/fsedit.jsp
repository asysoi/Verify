<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<link href="${CCICss}" rel="stylesheet" />

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
		  		    $("#language")
						    .val('${fscert.language}');
		  		    $("#codecountrytarget")
							.val('${fscert.codecountrytarget}');
					$("#otd_id")
							.val('${fscert.otd_id}');
				});

		jQuery("#products").jqGrid({
    		url: "fsgoods.do",
    		editurl: "fsgoodsupdate.do",
    		datatype: "xml",
    		mtype: "GET",
    		height: '20%',
			width : null,
			shrinkToFit : false,
   			colModel:[
   				{label:'Номер',name:'numerator', index:'numerator', width:250, editable: true, sortable:false, editrules:{number:true}},
   	    		{label:'Наименование товара', name:'tovar', index:'tovar', width:880, editable: true, sortable:false}
		   	],
		    rowNum: 10,
		    rowList:[5,10,20,50],
		   	sortname: 'numerator',
		   	sortorder: 'asc',
   			viewrecords: true,
   			pager: jQuery('#pagerproducts'),
    	    gridview: true,
    		autoencode: true,
   			caption: "Товары",
   			ondblClickRow : editRow
		});
		
		$('#products').jqGrid('navGrid', "#pagerproducts", {                
    		search: false, 
    		add: false,
    		edit: false,	
    		del: false,
    		refresh: false,
    		view: false
		}).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-plus',
            onClickButton: function(id) {
                var lastsel = id;
                $.ajaxSetup({async:false});
        		$.get("fsaddproduct.do");
                jQuery("#products").trigger('reloadGrid');
            	// var datarow = { numerator: "", tovar: ""};                
                // var ret = jQuery("#products").addRowData(lastsel, datarow, "last");
            },
            title: "Добавить продукт",
            position: "last"
        }).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-trash',
            onClickButton: function(event) {
            	 console.log(event);
            	 var grid = $("#products");
            	 var id = grid.jqGrid('getGridParam','selrow');
            	 
     		     if (id) { 
                    $.ajaxSetup({async:false});
             		$.get("fsdelproduct.do?id="+id);
     		     } else {} 
     		    	alert("Продукт не выбран.");
                 }  
                 grid.trigger('reloadGrid');
            },
            title: "Удалить продукт",
            position: "last"
        });
		
		var lastSelection;
		function editRow(id) {
			 var grid = $("#products");
             if (id && id !== lastSelection) {
                 grid.jqGrid('restoreRow',lastSelection);
                 lastSelection = id;
             }
             grid.jqGrid('editRow',id, {keys: true} );
        };
        
		grid = $("#products");
		grid.jqGrid('gridResize', {minWidth: 450, minHeight: 150});
		//$('#pagerproducts_center').hide();
		$("#pagerproducts_left", "#pagerproducts").width(150);
		
		jQuery("#getselected").click(function(){
		    var selr = jQuery('#products').jqGrid('getGridParam','selrow');
		    if(selr) alert(selr);
		    else alert("No selected row");
		    return false;
		});
		
		jQuery("#setselection").click(function(){
		    jQuery('#products').jqGrid('setSelection','10259');
		    return false;
		});

	});

	function clearelement(element) {
		element.val('');
	}

	function openClients(clienttype) {
        link="sclients.do?pagesize=5&clienttype="+clienttype+"&lang="+$("#language").val();
		$("#fsview").load(link);
		$("#fsview").dialog("option", "title", 'Список компаний');
		$("#fsview").dialog("option", "width", 1200);
		$("#fsview").dialog("option", "height", 520);
		$("#fsview").dialog("option", "modal", true);
		$("#fsview").dialog("option", "resizable", true );
		$("#fsview").dialog( "option", "position", { my: "center",  at: "center", of:window} );
		$("#fsview").dialog("open");
	}
	
	function reloadConfirmation() {
		url = "rldconfirm.do?lang=" + $("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log(data); 
			 console.log($("#confirmation").text());
		     $("#confirmation").val(data);
		});	
	}
	
	function reloadDeclaration() {
		url = "rlddecl.do?lang=" + $("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log(data); 
			 console.log($("#declaration").text());
		     $("#declaration").val(data);
		});	
	}
	
	function openEmployees(employeetype) {
        link="selemployees.do?pagesize=5&employeetype="+employeetype;
		$("#fsview").load(link);
		$("#fsview").dialog("option", "title", 'Список сотрудников');
		$("#fsview").dialog("option", "width", 1200);
		$("#fsview").dialog("option", "height", 520);
		$("#fsview").dialog("option", "modal", true);
		$("#fsview").dialog("option", "resizable", true );
		$("#fsview").dialog( "option", "position", { my: "center",  at: "center", of:window} );
		$("#fsview").dialog("open");
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
<div class="col-md-6">Язык сертификата: <form:select path="language"						
						items="${languages}" id="language" /></div>
						
</div>
					
<div class="row">
<div class="col-md-1"><a href="javascript:openClients('exporter')">Экспортер: </a></div>
<div class="col-md-6" id="exporter">
<c:if test="${fscert.language == 'EN'}">  ${fscert.exporter.enname}, ${fscert.exporter.enaddress}</c:if>
<c:if test="${fscert.language == 'RU'}">  ${fscert.exporter.name}, ${fscert.exporter.address}</c:if>
</div>
</div>

<div class="row">
<div class="col-md-1"><a href="javascript:openClients('producer')">Производитель:</a></div>
<div class="col-md-10" id="producer">
<c:if test="${fscert.language == 'RU'}">${fscert.producer.name}, ${fscert.producer.address}</c:if>
<c:if test="${fscert.language == 'EN'}">${fscert.producer.enname}, ${fscert.producer.enaddress}</c:if>
</div>
</div>	

<div class="row">
<div class="col-md-1">Удостоверение:<p align="center"><a href="javascript:reloadConfirmation()" title="Сгенерировать из шаблона">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a></p></div>
<div class="col-md-11"><form:textarea rows="6" cols="140" path="confirmation" id="confirmation" /></div>
</div>
<div class="row">
<div class="col-md-1">Декларация:<p align="center"><a href="javascript:reloadDeclaration()" title="Сгенерировать из шаблона">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a></p></div>
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
		  <div class="col-md-1"><a href="javascript:openEmployees('expert')">Эксперт:</a></div>
		  <div class="col-md-10" id="expert">
		  <c:if test="${fscert.language == 'RU'}">${fscert.expert.job} ${fscert.expert.name}</c:if>
		  <c:if test="${fscert.language == 'EN'}">${fscert.expert.enjob} ${fscert.expert.enname}</c:if>
		  </div>
</div>
<div class="row">
		  <div class="col-md-1"><a href="javascript:openEmployees('signer')">Подпись:</a></div>
		  <div class="col-md-10" id="signer">
		  <c:if test="${fscert.language == 'RU'}">${fscert.signer.job} ${fscert.signer.name}</c:if>
		  <c:if test="${fscert.language == 'EN'}">${fscert.signer.enjob} ${fscert.signer.enname}</c:if>		  
		  </div>
</div>				


<div class="row">
		        <div class="col-md-12">
		            <table id="products"></table>
		            <div id="pagerproducts"></div> 
				</div>
</div>

<button id="getselected">Get Selected Rows</button><button id="setselection">Select Row 10259</button>

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

