<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
 .title {
     display: inline; 
     font-size: 150%;
     color: #E9C92E; 
     height: 86; 
     vertical-align: middle;
     text-decoration: none; 
 } 
 
 a:hover {
    text-decoration: none; 
   }
 
</style>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">

	<div class="container-fluid">

		<div class="navbar-header" style="height: 86;">
			<div class="navbar-brand">
			<a href="https://www.cci.by"> <img src="resources/images/logo_40.png" width="64" height="64"
					style="vertical-align: middle;" />
			</a>
					
			<a  href="check.do">
			<c:if test="${lang=='ru'}">
			  <div class="title">Проверка сертификатов о происхождении товара</div>
			</c:if>
			<c:if test="${lang=='eng'}">
			  <div class="title">Verification certificates of origin</div>
			</c:if>	
			</a>
		   </div>
		</div>


		<div class="navbar-collapse collapse">

			<ul class="nav navbar-nav navbar-right " >
			
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
