<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="resources/css/cci.css" var="CCICss" />
<link href="${CCICss}" rel="stylesheet" />

<script>
   
	function clear() {
		$('input').val('');
		$('select').val('');
	}

	function reset() {
		$('#ffilter')[0].reset();
	}

	function submit() {
		url = $("#ffilter").attr("action");
		$.ajaxSetup({async:false});
		$.post(url, $("#ffilter").serialize());
  	    goToList('selemployees.do?page=1&pagesize=${employeemanager.pagesize}&orderby=${employeemanager.orderby}&order=${employeemanager.order}');
		$("#pview").dialog("close");
	}
	
	function resetEmployee() {
		$('#femployee')[0].reset();
	}

	function saveEmployee() {
		if (checkRequired()) {
			url = $("#femployee").attr("action");
			$.ajaxSetup({async:false});
			$.post(url, $("#femployee").serialize());
  		    goToList('selemployees.do?page=1&pagesize=${employeemanager.pagesize}&orderby=${employeemanager.orderby}&order=${employeemanager.order}');
			$("#clview").dialog("close");
		} 
	}
	
	function updateEmployee() {
        var x;
		if (confirm("Сохранить сделанные изменения?") == true) {
			url = $("#femployee").attr("action");
			$.ajaxSetup({async:false});
			$.post(url, $("#femployee").serialize());
 		    goToList('selemployees.do?page=1&pagesize=${employeemanager.pagesize}&orderby=${employeemanager.orderby}&order=${employeemanager.order}');
  		    $("#clview").dialog("close");
		} 
			
	}
	
	function close() {
		$("#pview").dialog("close");
	}
	
	$(document).ready(function() {
		$("#pview").dialog({
			autoOpen : false
		});
		$("#clview").dialog({
			autoOpen : false
		});
        document.getElementById("filter").checked=${employeemanager.onfilter};

		if (document.getElementById("filter").checked) {
			$("#filterlink").html('<a  href="javascript: setFilter();">&nbsp;Фильтр</a>');
		} else {
			$("#filterlink").html('&nbsp;Фильтр');
		}

	});

	function goToList(link) {
		var url = link;
		if (document.getElementById("filter").checked) {
			url = url + "&filter="
			        + document.getElementById("filter").checked;
   	    }
		$("#fsview").load(url);
	}

	function swithFilter() {
		goToList('selemployees.do?page=1&pagesize=${employeemanager.pagesize}&orderby=${employeemanager.orderby}&order=${employeemanager.order}');
		
		if (document.getElementById("filter").checked) {
			$("#filterlink").html('<a href="javascript: setFilter();">&nbsp;Фильтр</a>');
		} else {
			$("#filterlink").html('&nbsp;Фильтр');
		}
	}

	function setFilter(link) {
        link="employeefilter.do?&pagesize=${employeemanager.pagesize}&orderby=${employeemanager.orderby}&order=${employeemanager.order}";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Фильтр выбора сотрудника');
		$("#pview").dialog("option", "width", 690);
		$("#pview").dialog("option", "height", 470);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false );
		$("#pview").dialog({ buttons: [ { text: "Применить",  click : function() { submit(); } },  
				               { text: "Очистить Все ", click: function() { clear(); } },
 				               { text: "Отменить изменения", click: function() { reset(); } },
				               { text: "Отмена", click: function() { $( this ).html(""); $( this ).dialog( "close" ); } }
                  	                                               ] });
		$("#pview").dialog( "option", "position", { my: "center",  at: "center", of:window});
		$("#pview").dialog({close: function(event, ui){ $( this ).html("");}});
		$("#pview").dialog("open");
	}
	
	function editEmployee(id) {
        link = "employeeedit.do?id=" + id;
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Сотрудник БелТПП');
		$("#clview").dialog("option", "width", 690);
		$("#clview").dialog("option", "height", 590);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { updateEmployee(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() {  $( this ).dialog( "close" ); } }
		                      	                                               ] });

		$("#clview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#clview").dialog("open");
	}
	

	

	function viewEmployee(id) {
        link = "employeeview.do?id=" + id;
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Сотрудник БелТПП');
		$("#clview").dialog("option", "width", 650);
		$("#clview").dialog("option", "height", 530);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({
			buttons : [ 	{ text : "Закрыть",	click : function() { $(this).dialog("close");}} ]
		});

		$("#clview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#clview").dialog("open");
	}
	

	function addEmployee(id) {
        link = "employeeadd.do";
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Новый Сотрудник БелТПП');
		$("#clview").dialog("option", "width", 690);
		$("#clview").dialog("option", "height", 590);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { saveEmployee(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() {  $( this ).dialog( "close" ); } }
		                      	                                               ] });

		$("#clview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#clview").dialog("open");
	}
	

	function downloadEmployees() {
		link = "сlientconfig.do";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Экспорт списка сотрудников БелТПП');
		$("#pview").dialog("option", "width", 850);
		$("#pview").dialog("option", "height", 520);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ { text : "Загрузить",	click : function() {download();}}, 
 				{ text : "Очистить Все ", click : function() {clearconfig(); }}, 
 				{ text : "Выбрать Все ", click : function() {selectall(); }}, 
				{ text : "Закрыть",	click : function() { $(this).dialog("close");}
			} ]
		});

		$("#pview").dialog("option", "position", {
			my : "center",
			at : "center",
			of : window
		});
		$("#pview").dialog("open");
	}


	function clearconfig() {
	      $('form input[type="checkbox"]').prop('checked', false);
	}

	function selectall() {
	      $('form input[type="checkbox"]').prop('checked', true);
	}

	function download() {
		
   		var hiddenIFrameID = 'hiddenDownloader';
        var iframe = document.getElementById(hiddenIFrameID);
        
    	if (iframe == null) {
        	iframe = document.createElement('iframe');
        	iframe.id = hiddenIFrameID;
	    	iframe.style.display = 'none';
	    	document.body.appendChild(iframe);
    	}
    	iframe.src = "employeesecport.do";
	}
	
	
	function linkEmployee(eid, etype) {
		url = "selemployee.do?id=" + eid + "&employeetype=" + etype;
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log("Employee type: " + etype);
			 console.log("Employee date: " + data);
			 if (etype == 'expert') { 
			     $("#expert").text(data);
			 } else if (etype == 'signer') {
				 $("#signer").text(data);
			 }
		});	
		$("#fsview").dialog("close");
	}
	
</script>


<div id="listwindow" class="main">
	<table style="width: 100%">
		<tr>

			<td style="width: 60%">
                <input id="filter" type="checkbox"	onclick="javascript:swithFilter();" />
                <span id="filterlink"></span>
                </td>

			<td style="width: 40%; text-align: right">
				<a href="javascript:addEmployee();"><img src="resources/images/addclient.png" alt="Добавить"/></a>
				<a href="javascript:downloadEmployees();"><img src="resources/images/exp_excel.png" alt="Загрузить"/></a>
				   &nbsp; Строк в списке: 
				   <c:forEach items="${sizes}" var="item"> 
	           	   &nbsp;	
	               <a  href="javascript: goToList('selemployees.do?page=1&pagesize=${item}&orderby=${employeemanager.orderby}&order=${employeemanager.order}');">${item}</a>
				</c:forEach>
			</td>


		</tr>
	</table>

	<table class="certificate" style="width: 100%">
		<tr>
			<c:forEach items="${employeemanager.headers}" var="item">
				<td
					style="width:${item.width}%;background-color: gray; color: black"><a
					href="javascript: goToList('${item.link}');" style="color: white; font-size: 120%">${item.name}${item.selection}

</a></td>
			</c:forEach>
		</tr>

		<c:forEach items="${employees}" var="employee">
			<tr>
				<td>
				<div class="ccidropdown"><span>${employee.name}</span>
				<div class="ccidropdown-content"> 
				<ul class="cci">
					<li class="cci"><a class="cci" href="javascript:editEmployee('${employee.id}')"><i class="glyphicon glyphicon-edit"></i></a></li>
					<li class="cci"><a class="cci" href="javascript:linkEmployee('${employee.id}','${employeetype}')"><i class="glyphicon glyphicon-paperclip"></i></a></li>
				</ul> </div> </div>
				
				</td>
				<td>${employee.job}</td>
                <td>${employee.departmentname}</td>
                <td>${fn:substring(employee.otd_name, 0, 15)}...</td>
                <td>${employee.phone}</td>
			</tr>
		</c:forEach>
	</table>

	<table style="width: 100%">
		<tr>
			<td style="width: 80%; text-align: left"><a
				href="javascript: goToList('${first_page}');"><img
					src="resources/images/first_page_24.png" alt="Перв."> </a> <a
				href="javascript: goToList('${prev_page}');"><img
					src="resources/images/prev_page_24.png" alt="Пред."></a> <c:forEach
					items="${pages}" var="item">
	           	   &nbsp;	
	               <a
						href="javascript: goToList('selemployees.do?page=${item}&pagesize=${employeemanager.pagesize}

&orderby=

${employeemanager.orderby}

&order=${employeemanager.order}');"
						<c:if test="${item==employeemanager.page}">style="border-style: solid; border-width: 

1px;"</c:if>>
						${item} </a>
				</c:forEach> &nbsp; [Клиентов: &nbsp;${employeemanager.pagecount}]</td>
			<td style="width: 20%; text-align: right"><a
				href="javascript: goToList('${next_page}');"><img
					src="resources/images/next_page_24.png" alt="След."></a> <a
				href="javascript: goToList('${last_page}');"><img
					src="resources/images/last_page_24.png" alt="Посл."></a></td>
		</tr>
	</table>

	
	<div id="pview" name="pview">
	</div>

	<div id="clview" name="clview">
	</div>
   
</div>



