<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	$(function() {
		$("document").ready(
				function() {
					$("#codecountry")
						.val('${client.codecountry}');
					$("#bcodecountry")
						.val('${client.bcodecountry}');
				});
	});

	function clearelement(element) {
		element.val('');
	}
</script>

<form:form id="fclient" method="POST" commandName="client">

	<fieldset>
		<legend class="grp_title">Основные данные</legend>
        <form:hidden path="id" id="id" />
        <form:hidden path="version" id="version" />
		<table class="filter">
			<tr>
				<td>Наименование</td>
				<td><form:input path="name" id="name" size="25"/><a
					href="javascript:clearelement($('#name'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><form:input path="enname" id="enname" size="25"/>
				<a	href="javascript:translit($('#name'), $('#enname'));"> 
				<i class="glyphicon glyphicon-refresh"></i>
		        </a>
				<a	href="javascript:clearelement($('#enname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Учетный номер плательшика</td>
				<td><form:input path="unp" id="unp" /><a
					href="javascript:clearelement($('#unp'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Код ОКПО</td>
				<td><form:input path="okpo" id="okpo"
						size="20" /><a
					href="javascript:clearelement($('#okpo'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Контактная информафция</legend>
		<table class="filter">
			<tr>
				<td>Телефон</td>
				<td><form:input path="phone" id="phone" /><a
					href="javascript:clearelement($('#phone'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Мобильный телефон</td>
				<td><form:input path="cell" id="cell" /><a
					href="javascript:clearelement($('#cell'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Адрес электронной почты</td>
				<td><form:input path="email" id="email" /><a
					href="javascript:clearelement($('#email'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Адрес</legend>

		<table class="filter">
			<tr>
				<td>Страна <form:select path="codecountry"
						items="${countries}" id="codecountry"/><a
					href="javascript:clearelement($('#codecountry'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a>
				</td>
			</tr>
	    </table>		
	    <table class="filter">		
			<tr>	
				<td>Индекс</td>
				<td><form:input path="cindex" id="cindex" /><a
					href="javascript:clearelement($('#cindex'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			<tr>	
				<td>Город</td>
				<td><form:input path="city" id="city" /><a
					href="javascript:clearelement($('#city'));"> <img
						src="resources/images/delete-16.png" alt="удл." />   
				</a></td>
				<td>City</td>
				<td><form:input path="encity" id="encity" />
				<a	href="javascript:translit($('#city'), $('#encity'));"> 
				<i class="glyphicon glyphicon-refresh"></i>
				</a>
				<a href="javascript:clearelement($('#encity'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>	
				<td>Улица</td>
				<td><form:input path="street" id="street"/><a
					href="javascript:clearelement($('#street'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Street</td>
				<td><form:input path="enstreet" id="enstreet" />
				<a	href="javascript:translit($('#street'), $('#enstreet'));"> 
				<i class="glyphicon glyphicon-refresh"></i>
				</a>
				<a href="javascript:clearelement($('#enstreet'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				
			</tr>
			<tr>
				<td>Номер дома</td>
				<td><form:input path="building" id="building" /><a
					href="javascript:clearelement($('#building'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Номер офиса</td>
				<td><form:input path="office" id="office" /><a
					href="javascript:clearelement($('#office'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
	</fieldset>

	<fieldset>
		<legend class="grp_title">Банкие реквизиты</legend>

		<table class="filter">
			<tr>
				<td>Расчетный cчет</td>
				<td><form:input path="account" id="account"/><a
					href="javascript:clearelement($('#account'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td></td>
				<td></td>				
			</tr>
			<tr>
				<td>Наименование банка</td>
				<td><form:input path="bname" id="bname"/><a
					href="javascript:clearelement($('#bname'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td></td>
				<td></td>				
			</tr>
		</table>
		<table class="filter">	
			<tr>
				<td>Страна
				<form:select path="bcodecountry"
						items="${countries}" id="bcodecountry" /><a
					href="javascript:clearelement($('#bcodecountry'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
		</table>
		<table class="filter">		
			<tr>	
				<td>Индекс</td>
				<td><form:input path="bindex" id="bindex" /><a
					href="javascript:clearelement($('#bindex'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Город</td>
				<td><form:input path="bcity" id="bcity" /><a
					href="javascript:clearelement($('#city'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Улица</td>
				<td><form:input path="bstreet" id="bstreet" /><a
					href="javascript:clearelement($('#bstreet'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>
			<tr>
				<td>Номер дома</td>
				<td><form:input path="bbuilding" id="bbuilding" /><a
					href="javascript:clearelement($('#bbuilding'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
				<td>Номер офиса</td>
				<td><form:input path="boffice" id="boffice" /><a
					href="javascript:clearelement($('#boffice'));"> <img
						src="resources/images/delete-16.png" alt="удл." />
				</a></td>
			</tr>			
		</table>
	</fieldset>
</form:form>