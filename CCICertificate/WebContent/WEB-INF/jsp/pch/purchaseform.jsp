<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="col-md-10 col-md-offset-2 main">
		<h2>Сделка</h2>

		<form:form method="POST"  commandName="purchase">

			<form:errors path="*" cssClass="errorblock" element="div" />

			<table>
				<tr>
					<td>Покупатель :</td>
					<td><form:select path="id_otd">
						<form:option value="NONE" label="--- Выберите ---" />
						<form:options items="${departmentList}"/>
					</form:select></td> 					
					<td><form:errors path="id_otd" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Товар :</td>
					<td><form:select path="id_product">
				    	<form:option value="NONE" label="--- Выберите ---" />
						<form:options items="${productList}" />
					</form:select></td> 					
   				    <td><form:errors path="id_product" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Характеристики товара :</td>
					<td><form:textarea path="productProperty" /></td>
					<td><form:errors path="productProperty" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Цена :</td>
					<td><form:input path="price" /></td>
					<td><form:errors path="price" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Объем :</td>
					<td><form:input path="volume" /></td>
					<td><form:errors path="volume" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Единица измерения объема :</td>
					<td><form:input path="unit" /></td>
					<td><form:errors path="unit" cssClass="error" /></td>
				</tr>
				
				<tr>
    	            <td>Дата :</td>
        	       	<td><form:input path="pchDate" id="datepicker" /></td>
            	    	<script>
                	    	$(function() {
	                	        $("#datepicker").datepicker();
    	                	});
        	        	</script>
        	        <td><form:errors path="pchDate" cssClass="error" /></td>	
	            </tr>
	            <tr>
					<td>Продавец :</td>
					<td><form:select path="id_company">
				    	<form:option value="NONE" label="--- Выберите ---" />
						<form:options items="${companyList}" />
					</form:select></td> 					
   				    <td><form:errors path="id_company" cssClass="error" /></td>
				</tr>
	             
				
				<form:hidden path="id" />

				<tr>
					<td colspan="3"><input name="Сохранить" type="submit"/></td>
				</tr>
			</table>
		</form:form>
</div>