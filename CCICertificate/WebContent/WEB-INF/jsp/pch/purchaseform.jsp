<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	$(function() {
		
		$(document).ready(
				function() {
					$("#ewin").dialog({
						autoOpen : false
					});
		});
		
		$(".datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(function() {
			$(".datepicker").datepicker("option", "dateFormat", 'dd/mm/yy');
			$("#pchdate").datepicker("setDate", "${purchase.pchdate}");
			$("#product").val('${purchase.id_product}');
			$("#department").val('${purchase.id_otd}');
			$("#company").val('${purchase.id_company}');
		});

	});

	function clearelement(element) {
		element.val('');
	}
	
	function addproduct() {
        $( "#newproduct").val('');		
		$( "#ewin" ).dialog("option", "title", "Добавить продукт");
		$( "#ewin" ).dialog("option", "modal", true);
		$( "#ewin" ).dialog("option", "resizable", false);
		
	    $( "#ewin" ).dialog({
			buttons : [ {
				text : "Добавить",
				click : function() {
					url = $("#fadd").attr("action");
					alert($("#fadd").serialize()); 
					var ret = $.post(url, $("#fadd").serialize());
					$(document).ajaxComplete(
								function(event, request, settings) {
									alert(ret);
								});
  				    $( this ).dialog( "close" );
				}
			},{  
			text : "Отмена",
			click : function() {
				 $( this ).dialog( "close" );
				}
			}
			]
		});
		$( "#ewin" ).dialog("open");
	}
	
</script>

<form:form id="fitem" method="POST" commandName="purchase">

	<table class="filter">

		<tr>
			<td>Покупатель</td>
			<td><form:select path="id_otd" items="${departmentList}"
					id="department" /><a
				href="javascript:clearelement($('#department'));"> <img
					src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>

		<tr>
			<td>Товар</td>
			<td><form:select path="id_product" items="${productList}"
					id="product" /><a href="javascript:clearelement($('#product'));">
					<img src="resources/images/delete-16.png" alt="удл." /></a>
					<a href="javascript:addproduct($('#product'));"> 
					<img src="resources/images/search-add-icon.png" alt="доб."/></a>
			</td>
		</tr>

		<tr>
			<td>Продавец</td>
			<td><form:select path="id_company" items="${companyList}"
					id="company" /><a href="javascript:clearelement($('#company'));">
					<img src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>

		<tr>
			<td>Характеристика товара</td>
			<td><form:input path="productproperty" id="productproperty"
					size="35" /><a
				href="javascript:clearelement($('#productproperty'));"> <img
					src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>
		<tr>
			<td>Цена</td>
			<td><form:input path="price" id="price" class="price" size="8" />
				<a href="javascript:clearelement($('#price'));"> <img
					src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>

		<tr>
			<td>Количество</td>
			<td><form:input path="volume" id="volume" class="volume"
					size="8" /><a href="javascript:clearelement($('#volume'));"> <img
					src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>
		<tr>
			<td>Единица измерения</td>
			<td><form:input path="unit" id="unit" /><a
				href="javascript:clearelement($('#unit'));"> <img
					src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>

		<tr>
			<td>Дата сделки</td>
			<td><form:input path="pchdate" id="pchdate" class="datepicker"
					size="10" /> <a href="javascript:clearelement($('.datepicker'));">
					<img src="resources/images/delete-16.png" alt="удл." />
			</a></td>
		</tr>
	</table>
</form:form>

<div id="ewin">
<form id="fadd" action="addproduct.do" method="POST"> 
<label>Продукт</label><input id="newproduct" name="newproduct" class="newproduct" type="text"/>
</form>
</div>