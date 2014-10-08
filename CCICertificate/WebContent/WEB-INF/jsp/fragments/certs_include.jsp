<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="resources/css/login.css" var="LoginCss" />
<link href="${LoginCss}" rel="stylesheet" />

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
		$.post(url, $("#ffilter").serialize());
		goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');		
		$("#pview").dialog("close");
	}

	function close() {
		$("#pview").dialog("close");
	}
</script>

<script>
	$(document).ready(function() {
		$("#pview").dialog({
			autoOpen : false
		});
	
        document.getElementById("filter").checked=${vmanager.onfilter};

		if (document.getElementById("filter").checked) {
			$("#filterlink").html('<a  href="javascript: loadWindow();">&nbsp;Фильтр</a>');
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
                                   //alert(url);
		document.location.href = url;
	}

	function swithFilter() {
		if (document.getElementById("filter").checked) {
			$("#filterlink").html('<a href="javascript: loadWindow();">&nbsp;Фильтр</a>');
		} else {
			$("#filterlink").html('&nbsp;Фильтр');
		}
		goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');		
	}

	function loadWindow(link) {
        link="filter.do?&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Фильтр поиска');
		$("#pview").dialog("option", "width", 800);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog({ buttons: [ { text: "Применить",  click : function() { submit(); } },  
				               { text: "Очистить Все ", click: function() { clear(); } },
 				               { text: "Отменить изменения", click: function() { reset(); } },
				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
                  	                                               ] });
              
		$("#pview").dialog( "option", "position", { my: "center",  at: "center", of:window} );
		$("#pview").dialog("open");
	}
	
</script>

<div id="listvindow" class="col-md-10 col-md-offset-2 main">
	<h3>Список сертификатов</h3>
	<table style="width: 100%">
		<tr>

			<td style="width: 70%">
			    <!--  <input id="fullsearchvalue"
				value="${vmanager.fullsearchvalue}" />
				<a href="javascript:goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=

${vmanager.order}');">
				<img src="resources/images/red_search.png" alt="Искать"/></a> -->
				
                <input id="filter" type="checkbox"	onclick="javascript:swithFilter();" >
                <span id="filterlink"></span>
                </input></td>

			<td style="width: 30%; text-align: right">Строк в списке: <c:forEach
					items="${sizes}" var="item">
	           	   &nbsp;	
	               <a
						href="javascript: goToList('certs.do?page=1&pagesize=${item}&orderby=${vmanager.orderby}

&order=

${vmanager.order}');">${item}</a>
				</c:forEach>
			</td>


		</tr>
	</table>

	<table class="certificate" style="width: 100%">
		<tr>
			<c:forEach items="${vmanager.headers}" var="item">
				<td
					style="width:${item.width}%;background-color: gray; color: black"><a
					href="javascript: goToList('${item.link}');" style="color: white">${item.name}${item.selection}</a></td>
			</c:forEach>
		</tr>

		<c:forEach items="${certs}" var="cert">
			<tr>
				<td><a href="gocert.do?certid=${cert.cert_id}">${cert.nomercert}</a></td>
				<td>${cert.otd_name}</td>
				<td>${cert.kontrp}</td>
				<td>${cert.nblanka}</td>
				<td>${cert.datacert}</td>

				<!-- 
	        <td>
	        <c:if test="${cert.child_id != null}">
	            <a href="gocert.do?certid=${cert.child_id}">child</a>
	        </c:if>    
	        </td>  
	        -->

				<td><c:if test="${cert.parent_id > 0}">
						<a href="gocert.do?certid=${cert.parent_id}">замена для</a>
					</c:if></td>
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
						href="javascript: goToList('certs.do?page=${item}&pagesize=${vmanager.pagesize}&orderby=

${vmanager.orderby}

&order=${vmanager.order}');"
						<c:if test="${item==vmanager.page}">style="border-style: solid; border-width: 1px;"</c:if>>
						${item} </a>
				</c:forEach> &nbsp; (количество сертификатов - ${vmanager.pagecount})</td>
			<td style="width: 20%; text-align: right"><a
				href="javascript: goToList('${next_page}');"><img
					src="resources/images/next_page_24.png" alt="След."></a> <a
				href="javascript: goToList('${last_page}');"><img
					src="resources/images/last_page_24.png" alt="Посл."></a></td>
		</tr>
	</table>

	
	<div id="pview" name="pview">
	</div>

</div>



