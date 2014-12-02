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
							'dd.mm.yy');
					$("#datepchfrom").datepicker("setDate",
							"${viewfilter.viewpurchase.datefrom}");
					$("#datepchto").datepicker("setDate",
							"${viewfilter.viewpurchase.dateto}");
					$("#product")
							.val('${viewfilter.viewpurchase.product}');
					$("#department")
							.val('${viewfilter.viewpurchase.department}');
					$("#company").val('${viewfilter.viewpurchase.company}');
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
				<td><form:select path="viewpurchase.id_product"
						items="${productList}" id="product" /><a
					href="javascript:clearelement($('#department'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>

			<tr>
				<td>Продавец</td>
				<td><form:select path="viewpurchase.id_company"
						items="${companyList}" id="company" /><a
					href="javascript:clearelement($('#company'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>

			<tr>
				<td>Характеристика товара</td>
				<td><form:input path="viewpurchase.productproperty" id="productproperty" /><a
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
					href="javascript:clearelement($('#price'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			
			<tr>
				<td>Дата сделки</td>
				<td><form:input path="viewpurchase.datepchfrom" id="datepchfrom"
						class="datepicker" size="8" placeholder="с" />&nbsp;-&nbsp; 
						<form:input	path="viewpurchase.datepchto" id="datepchto" class="datepicker"
						size="8" placeholder="по" /> <a
					href="javascript:clearelement($('.datepicker'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
	</table>
</form:form>