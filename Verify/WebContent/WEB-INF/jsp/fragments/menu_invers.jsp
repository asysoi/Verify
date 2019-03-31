<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
 .title {
     display: inline; 
     font-size: 150%;
     color__: #E9C92E;
     color: #36478B; 
     height: 106; 
     vertical-align: middle;
     text-decoration: none; 
 } 
 
 a:hover {
    color: #36478B;
    text-decoration: none; 
   }

</style>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation"  style="border-color: #E9C92E; border-width: 0 0 3px; background: linear-gradient(to top left, #395B8D, #C1DDF1, #395B8D);">

	<div class="container-fluid">

		<div class="navbar-header" style="height: 106;">
			<div class="navbar-brand">
			
			<a href="https://www.cci.by"> <img src="resources/images/logo_60.png" style="vertical-align: middle;" />
			</a>

			<a  href="check.do">
			<c:if test="${lang=='ru'}">
			  <div class="title">Проверка сертификатов о происхождении товара</div>
			  <div style="margin-left: 66px; margin-top: -22px;"> Белорусская торгово-промышленная палата </div>
			</c:if>
			<c:if test="${lang=='eng'}">
			  <div class="title">Verification certificates of origin<br></div>
			  <div style="margin-left: 66px; margin-top: -22px;"> Belarusian Chamber of Commerce and Industry</div>
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

