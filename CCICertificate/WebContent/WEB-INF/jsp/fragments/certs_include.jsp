<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>


<div class="col-md-10 col-md-offset-2 main">
     <table style="width:100%">     
      <tr>
      <th style="width:10%">Номер сертификата</th>
      <th style="width:9%">Эксперт</th>
      <th style="width:60%">Получатель</th>
      <th style="width:7%">УНП</th>
      <th style="width:7%">Номер бланка</th>
      <th style="width:7%">Дата Сертификата</th>
      </tr>  
	 <c:forEach items="${certs}" var="cert">
	        <tr>
	        <td style="width:10%"><a href="gocert.do?certid=${cert.cert_id}">${cert.nomercert}</a></td>
	        <td style="width:9%">${cert.expert}</td>
	        <td style="width:50%">${cert.short_kontrp}</td>
	        <td style="width:7%">${cert.unn}</td>
	        <td style="width:7%">${cert.nblanka}</td>
	        <td style="width:7%">${cert.datacert}</td>
	        
	        <td style="width:5%">
	        <c:if test="${cert.child_id != null}">
	            <a href="gocert.do?certid=${cert.child_id}">child</a>
	        </c:if>    
	        </td>
	        
	        <td style="width:5%">
	        <c:if test="${cert.parentnumber != null}">
	        <a href="gocert.do?certid=${cert.parentnumber}">parent</a>
	        </c:if>
	        </td>
	        
	          
	        </tr>
     </c:forEach>
     <tr>
     <td><a href="${prev_page}">Предыдущая страница</a></td>
     <td> </td><td></td><td> </td><td> </td>
     <td><a href="${next_page}">Следующая страница</a></td>
     </tr>
     </table>     
     
</div>
