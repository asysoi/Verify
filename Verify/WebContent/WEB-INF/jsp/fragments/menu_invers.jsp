<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">

	<div class="container-fluid">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<div class="navbar-brand">
				<a href="https://www.cci.by"> <img
					src="resources/images/logo_40.png" width="32" height="32"
					style="vertical-align: middle;" />
				</a>
			</div>
			<a href="check.do">
			<c:if test="${lang=='ru'}">
			  <div class="navbar-brand" style="font-size: 180%">Проверка
				выдачи сертификатов БелТПП</div>
			</c:if>
			<c:if test="${lang=='eng'}">
			  <div class="navbar-brand" style="font-size: 180%">Verification of BelCCI certificate issue</div>
			</c:if>	
			</a>
		</div>


		<div class="navbar-collapse collapse">

			<ul class="nav navbar-nav navbar-right">
			
			    <c:if test="${lang=='ru'}">  
				    <li><a href="javascript:setLanguage('eng')">English</a></li>
				</c:if>
				<c:if test="${lang=='eng'}">  
				    <li><a href="javascript:setLanguage('ru')">Русский</a></li>
				</c:if>
			</ul>
		</div>

	</div>
</div>
