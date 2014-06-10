<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>


<div class="col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Login</h1>

	<div class="row placeholders">
    
	<div class="container">
			<form:form method="POST" commandName="user" class="form-signin" role="form">
					<form:input path="userName" class="form-control" placeholder="Имя пользователя" />
 				    <form:password path="password" class="form-control" placeholder="Пароль" />
 				    <label class="checkbox"> <input type="checkbox" value="remember-me"> Запомнить </label>
 				    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
			</form:form>
	</div>
	
	</div>

</div>


















