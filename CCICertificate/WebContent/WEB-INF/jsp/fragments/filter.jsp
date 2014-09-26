<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form method="POST" commandName="filter" >
	<table>
			<tr>
 				 <td>Грузопролучатель/экспортер :</td>
					<td></td>
					
				</tr>
				<tr>
					<td>Адрес грузополучателя/экспортера :</td>
					<td></td>
				</tr>
				<tr>
					<td>Маршрут транспорта :</td>
					<td></td>
				</tr>
				<tr>
					<td>Номер сертификата :</td>
					<td></td>
				</tr>
				<tr>
					<td>Номер бланка :</td>
					<td></td>
				</tr>
				<tr>
					<td>Страна предоставления :</td>
					<td></td>
				</tr>
				
				<tr>
    	            <td>Отметка :</td>
    	            <td></td>
	            </tr>
	            <tr>
					<td>Товар :</td>
					<td></td>
				</tr>
				<tr>
					<td>Все поля :</td>
					<td><form:input path="fullsearchvalue"/></td>
				</tr>
				
	            <tr>
					<td colspan="3"><form:button name="Сохранить" type="submit" >Применить</form:button></td>
					<td colspan="3"><input name="Сохранить" type="submit">Применить</input></td>
				</tr>
	</table>
</form:form>