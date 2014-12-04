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
					$(".datepicker").datepicker("option", "dateFormat",
							'dd/mm/yy');
					$("#pchdatefrom").datepicker("setDate",
							"${viewfilter.viewpurchase.pchdatefrom}");
					$("#pchdateto").datepicker("setDate",
							"${viewfilter.viewpurchase.pchdateto}");
					$("#id_product")
							.val('${viewfilter.viewpurchase.id_product}');
					$("#department")
							.val('${viewfilter.viewpurchase.id_otd}');
					$("#id_company").val('${viewfilter.viewpurchase.id_company}');
				});

	});

	function clearelement(element) {
		element.val('');
	}
	
	
	
</script>

<form:form id="ffilter" method="POST" commandName="viewfilter">

	<table class="filter">
	
			<tr>
				<td>Покупатель</td>
				<td><form:select path="viewpurchase.id_otd"
						items="${departmentList}" id="department" /><a
					href="javascript:clearelement($('#department'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>

			<tr>
				<td>Товар</td>
				<td><form:input path="viewpurchase.product" id="product" size="25"/>
				<a href="javascript:clearelement($('#product'));"> 
					<img src="resources/images/delete-16.png" alt="удл."/></a>
				<a href="javascript:addproduct($('#product'));"> 
					<img src="resources/images/search-add-icon.png" alt="доб."/></a>					
				
				</td>
			</tr>

			<tr>
				<td>Продавец</td>
				<td><form:input path="viewpurchase.company" id="company" size="25"/><a
					href="javascript:clearelement($('#company'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>

			<tr>
				<td>Характеристика товара</td>
				<td><form:input path="viewpurchase.productproperty" id="productproperty"  size="42" /><a
					href="javascript:clearelement($('#productproperty'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Цена</td>
				<td><form:input path="viewpurchase.pricefrom" id="pricefrom" class="price" 
						size="8" placeholder="от" />&nbsp;-&nbsp; 
						<form:input	path="viewpurchase.priceto" id="priceto" class="price" 
						size="8" placeholder="до" /> <a
					href="javascript:clearelement($('.price'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			   
			<tr>
				<td>Количество</td>
				<td><form:input path="viewpurchase.volumefrom" id="volumefrom" class="volume" 
						size="8" placeholder="от" />&nbsp;-&nbsp; 
						<form:input	path="viewpurchase.volumeto" id="volumeto" class="volume" 
						size="8" placeholder="до" /> <a
					href="javascript:clearelement($('.volume'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Единица измерения</td>
				<td><form:input path="viewpurchase.unit" id="unit" /><a
					href="javascript:clearelement($('#unit'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			
			<tr>
				<td>Дата сделки</td>
				<td><form:input path="viewpurchase.pchdatefrom" id="pchdatefrom"
						class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; 
						<form:input	path="viewpurchase.pchdateto" id="pchdateto" class="datepicker"
						size="8" placeholder="по" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
	</table>
</form:form>

