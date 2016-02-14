<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="resources/css/cci.css" var="CCICss" />
<link href="${CCICss}" rel="stylesheet" />

<script>

	$(document).ready(
		function() {
			$("#pview").dialog({
				autoOpen : false
			});
			
			$("#jalert").dialog({
				autoOpen : false
			});
			
			document.getElementById("filter").checked = ${pmanager.onfilter};

			if (document.getElementById("filter").checked) {
				$("#filterlink")
						.html(
								'<a  href="javascript: setFilter();">&nbsp;Фильтр</a>');
			} else {
				$("#filterlink").html('&nbsp;Фильтр');
			}
	});


	function clear() {
		$('input').val('');
		$('select').val('');
	}

	function reset() {
		$('#fitem')[0].reset();
	}

	function close() {
		$("#pview").dialog("close");
		$("#pview").text('');
		$(document).off('ajaxComplete');
	}
	
	
	function submit() {
		url = $("#ffilter").attr("action");
		$.post(url, $("#ffilter").serialize());
		$(document)
				.ajaxComplete(
						function(event, request, settings) {
							goToList('purchases.do?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
							close();
						});
	}

	function save() {
		if ( is_date($('#pchdate').val()) ) {
			
			if ($.isNumeric($('#price').val()) && $.isNumeric($('#volume').val())) {
				url = $("#fitem").attr("action");
				$.post(url, $("#fitem").serialize());

				$(document)
					.ajaxComplete(
							function(event, request, settings) {
								goToList('purchases.do?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
								close();
							});
			} else {
				jalert ("Сообщение", "Поля Цена и Количество должны иметь цифровое значение");
			}
		} else {
			jalert ("Сообщение", "Введите корректную дату сделки");
		}
	}

	function update(action) {
		if ( is_date($('#pchdate').val()) ) {
			
			if ($.isNumeric($('#price').val()) && $.isNumeric($('#volume').val())) {
				var x;
				if (confirm("Сохранить сделанные изменения?") == true) {

					url = $("#fitem").attr("action");
					$.post(url, $("#fitem").serialize());

					$(document)
						.ajaxComplete(
							function(event, request, settings) {
								goToList('purchases.do?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');
								close();
							});
				}
			} else {
				jalert ("Сообщение", "Поля Цена и Количество должны иметь цифровое значение");
			}
		} else {
			jalert ("Сообщение", "Введите корректную дату сделки");
		}
	}

	function goToList(link) {
		var url = link;
		if (document.getElementById("filter").checked) {
			url = url + "&filter=" + document.getElementById("filter").checked;
		}
		document.location.href = url;
	}

	function swithFilter(action) {
		goToList(action
				+ '?page=1&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');

		if (document.getElementById("filter").checked) {
			$("#filterlink").html(
					'<a href="javascript: setFilter();">&nbsp;Фильтр</a>');
		} else {
			$("#filterlink").html('&nbsp;Фильтр');
		}
	}

	function setFilter() {
		link = "filterpurchase.do?&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Фильтр поиска Сделок');
		$("#pview").dialog("option", "width", 620);
		$("#pview").dialog("option", "height", 340);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ {
				text : "Применить",
				click : function() {
					submit();
				}
			}, {
				text : "Очистить Все ",
				click : function() {
					clear();
				}
			}, {
				text : "Отменить изменения",
				click : function() {
					reset();
				}
			}, {
				text : "Отмена",
				click : function() {
					close();
				}
			}
			]
		});
		$("#pview").dialog("option", "position", {
			my : "center",
			at : "center",
			of : window
		});
		$("#pview").dialog("open");
	}

	function viewItem(id) {
		link = "viewpurchase.do?id=" + id;
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 490);
		$("#pview").dialog("option", "height", 300);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ {
				text : "Закрыть",
				click : function() {
					close();
				}
			} 
			]
		});

		$("#pview").dialog("option", "position", {
			my : "center top",
			at : "center",
			of : listwindow
		});

		$("#pview").dialog("open");
	}

	function editItem(id) {
		link = "editpurchase.do?id=" + id;
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 620);
		$("#pview").dialog("option", "height", 340);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ {
				text : "Сохранить",
				click : function() {
					update();
				}
			}, {
				text : "Очистить Все ",
				click : function() {
					clear();
				}
			}, {
				text : "Отмена",
				click : function() {
					close();
				}
			} 
			]
		});

		$("#pview").dialog("option", "position", {
			my : "center top",
			at : "center",
			of : listwindow
		});

		$("#pview").dialog("open");
	}

	function addItem(id) {
		link = "addpurchase.do";
		$("#pview").load(link);
		$("#pview").dialog("option", "title", 'Сделка');
		$("#pview").dialog("option", "width", 620);
		$("#pview").dialog("option", "height", 340);
		$("#pview").dialog("option", "modal", true);
		$("#pview").dialog("option", "resizable", false);
		$("#pview").dialog({
			buttons : [ {
				text : "Сохранить",
				click : function() {
					save();
				}
			}, {
				text : "Очистить Все ",
				click : function() {
					clear();
				}
			}, {
				text : "Отмена",
				click : function() {
					close();
				}
			} 
			]
		});

		$("#pview").dialog("option", "position", {
			my : "center top",
			at : "center",
			of : listwindow
		});

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
	
	
	function is_date(txtDate)
	{
         if ($.trim(txtDate) == '')  return false;
         re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
         if(!txtDate.match(re)) return false;
         return true;
	}
	
	function jalert(title, msg) {
		$( "#jalert" ).text(msg);		
		$( "#jalert" ).dialog("option", "title", title);
		$( "#jalert" ).dialog("option", "modal", true);
		$( "#jalert" ).dialog("option", "resizable", false);
		
	    $( "#jalert" ).dialog({
			buttons : [ {
				text : "Ок",
				click : function() {
					 $( this ).dialog( "close" );
 		  		  	 $( this ).text('');
				}
			}]
		});
		$( "#jalert" ).dialog("open");
	}
	
</script>


<div id="listwindow" class="main">
	<h3>Список сделок</h3>
	<table style="width: 100%">
		<tr>

			<td style="width: 60%"><input id="filter" type="checkbox"
				onclick="javascript:swithFilter('purchases.do');" /> <span
				id="filterlink"></span></td>

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
					<a href="javascript: goToList('${item.link}');"
					style="color: white; font-size: 120%">${item.name}${item.selection}

				</a>
				</td>
			</c:forEach>
		</tr>

		<c:forEach items="${purchases}" var="item">
			<tr>
				<td><a href="javascript:editItem('${item.id}')">${item.pchdatestring}</a></td>
				<td>${item.product}</td>
				<td>${item.company}</td>
				<td>${item.price}</td>
				<td>${item.volume}</td>
				<td>${item.unit}</td>
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
	               <a
						href="javascript: goToList('purchases.do?page=${item}&pagesize=${pmanager.pagesize}&orderby=${pmanager.orderby}&order=${pmanager.order}');"
						<c:if test="${item==pmanager.page}">style="border-style: solid; border-width: 1px;"</c:if>>
						${item} </a>
				</c:forEach> &nbsp; [Количество сделок: &nbsp;${pmanager.pagecount}]</td>
			<td style="width: 20%; text-align: right"><a
				href="javascript: goToList('${next_page}');"><img
					src="resources/images/next_page_24.png" alt="След."></a> <a
				href="javascript: goToList('${last_page}');"><img
					src="resources/images/last_page_24.png" alt="Посл."></a></td>
		</tr>
	</table>


	<div id="pview"></div>
	<div id="jalert" style="text-align: center;"></div>

</div>