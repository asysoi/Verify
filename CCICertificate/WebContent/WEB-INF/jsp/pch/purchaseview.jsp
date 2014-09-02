<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="col-md-10 col-md-offset-2 main">
		<h2>Сделка</h2>

		<table>
				<tr>
					<td>Покупатель :</td>
					<td>${purchase.department}</td>
					
				</tr>
				<tr>
					<td>Товар :</td>
					<td>${purchase.product}</td>
				</tr>
				<tr>
					<td>Характеристики товара :</td>
					<td>${purchase.productProperty}</td>
				</tr>
				<tr>
					<td>Цена :</td>
					<td>${purchase.price}</td>
				</tr>
				<tr>
					<td>Объем :</td>
					<td>${purchase.volume}</td>
				</tr>
				<tr>
					<td>Единица измерения объема :</td>
					<td>${purchase.unit}</td>
				</tr>
				
				<tr>
    	            <td>Дата :</td>
    	            <td>${purchase.pchDate}</td>
	            </tr>
	            <tr>
					<td>Продавец :</td>
					<td>${purchase.company}</td>
				</tr>
	            
			</table>
</div>