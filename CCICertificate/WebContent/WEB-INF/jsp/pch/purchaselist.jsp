<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="resources/css/cci.css" var="CCICss" />
<link href="${CCICss}" rel="stylesheet" />

<script>
	function clear() {
		$('input').val('');
		$('select').val('');
	}

	function reset() {
		$('#fitem')[0].reset();
	}
	
	function close() {
		$("#pview").dialog("close");
	}

	function save(action) {
		
		url = $("#fitem").attr("action");
		$.post(url, $("#fitem").serialize());
		
		$( document ).ajaxComplete(function(event,request, settings ) {
			  goToList(action + '?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
			  close();
		});
	}
	
	function update(action) {
        var x;
		if (confirm("Сохранить сделанные изменения?") == true) {
		
			url = $("#fitem").attr("action");
			$.post(url, $("#fitem").serialize());
		
			$( document ).ajaxComplete(function(event,request, settings ) {
				  goToList(action + '?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
				  close();
			});
		} 
	}
	
	
	$(document).ready(function() {
		$("#pview").dialog({
			autoOpen : false
		});
        document.getElementById("filter").checked=${pmanager.onfilter};

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
  		document.location.href = url;
	}

	function swithFilter(action) {
		goToList(action+'?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
		
		if (document.getElementById("filter").checked) {
			$("#filterlink").html('<a href="javascript: setFilter();">&nbsp;Фильтр</a>');
		} else {
			$("#filterlink").html('&nbsp;Фильтр');
		}
	}

	function setFilter() {
        link="pfilter.do?&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Фильтр поиска cltkrb');
		$("#pview").dialog("option", "width", 740);
		$("#pview").dialog("option", "height", 500);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false );
		$("#pview").dialog({ buttons: [ { text: "Применить",  click : function() { submit(); } },  
				               { text: "Очистить Все ", click: function() { clear(); } },
 				               { text: "Отменить изменения", click: function() { reset(); } },
				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
                  	                                               ] });
		$("#pview").dialog( "option", "position", { my: "center",  at: "center", of:window} );
		$("#pview").dialog("open");
	}

	function viewItem(id) {
        link = "viewpurchase.do?id=" + id;
		$("#pview").load(link);        
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 650);
		$("#pview").dialog("option", "height", 480);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ 	{ text : "Закрыть",	click : function() {$(this).dialog("close");}} ]
		});

		$("#pview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#pview").dialog("open");
	}
	
	function editItem(id) {
        link = "editpurchase.do?id=" + id;
		$("#pview").load(link);        
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 820);
		$("#pview").dialog("option", "height", 570);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({ buttons: [ { text: "Сохранить",  click : function() { updateClient(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });

		$("#pview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#pview").dialog("open");
	}
	

	function addItem(id) {
        link = "addpurchase.do";
		$("#pview").load(link);        
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 820);
		$("#pview").dialog("option", "height", 570);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({ buttons: [ { text: "Сохранить",  click : function() { saveClient(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });

		$("#pview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

		$("#pview").dialog("open");
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
    	iframe.src = "exportpurchases.do";
	}

</script>


<div id="listwindow" class="col-md-10 col-md-offset-2 main">
	<h3>Список сделок</h3>
	<table style="width: 100%">
		<tr>

			<td style="width: 60%"><input id="filter" type="checkbox"
				onclick="javascript:swithFilter('purchases.do');" /> <span id="filterlink"></span>
			</td>

			<td style="width: 40%; text-align: right"><a
				href="javascript:addItem();"><img
					src="resources/images/addclient.png" alt="Добавить" /></a> <a
				href="javascript:download();"><img
					src="resources/images/exp_excel.png" alt="Загрузить" /></a> &nbsp;
				Строк в списке: <c:forEach items="${sizes}" var="item"> 
	           	   &nbsp;	
	               <a
						href="javascript: goToList('purchases.do?page=1&pagesize=${item}&orderby=${pmanager.orderby}&order=${pmanager.order}');">${item}</a>
				</c:forEach></td>


		</tr>
	</table>

	<table class="certificate" style="width: 100%">
		<tr>
			<c:forEach items="${pmanager.headers}" var="item">
				<td
					style="width:${item.width}%;background-color: gray; color: black">
					<a	href="javascript: goToList('${item.link}');"
					style="color: white; font-size: 120%">${item.name}${item.selection}

				</a></td>
			</c:forEach>
		</tr>

		<c:forEach items="${purchases}" var="item">
			<tr>
				<td><a href="javascript:viewItem('${item.id}')">${item.pchdate}</a></td>
				<td>${item.product}</td>
				<td>${item.company}</td>
				<td>${item.price}</td>
				<td>${item.volume}</td>
				<td>${item.department}</td>
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
	               <a href="javascript: goToList('purchases.do?page=${item}&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');"
						<c:if test="${item==pmanager.page}">style="border-style: solid; border-width: 1px;"</c:if>>
						${item} </a>
				</c:forEach> &nbsp; [Клиентов: &nbsp;${pmanager.pagecount}]</td>
			<td style="width: 20%; text-align: right"><a
				href="javascript: goToList('${next_page}');"><img
					src="resources/images/next_page_24.png" alt="След."></a> <a
				href="javascript: goToList('${last_page}');"><img
					src="resources/images/last_page_24.png" alt="Посл."></a></td>
		</tr>
	</table>


	<div id="pview" name="pview"></div>


</div>


























