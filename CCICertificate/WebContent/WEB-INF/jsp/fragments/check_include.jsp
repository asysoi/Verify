<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>

<div class="col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Верификация сертификата</h1>

	<div class="row placeholders">
    
	<div class="container">
	        <p>${msg}</p>
			<form:form method="POST" commandName="cert" class="form-signin" role="form">
					<form:input path="nomercert" class="form-control" placeholder="Номер сертификата" />
 				    <form:input path="nblanka" class="form-control" placeholder="Номер бланка сертификата" />
 				    <form:input path="datacert" class="form-control" placeholder="Дата выпуска сертификата" />
 				    <button class="btn btn-lg btn-primary btn-block" type="submit">Проверить наличие</button>
			</form:form>
	</div>
	
	</div>

</div>
