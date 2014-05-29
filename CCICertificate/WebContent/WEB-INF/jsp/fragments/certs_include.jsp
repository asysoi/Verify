<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>


<div class="col-md-10 col-md-offset-2 main">
     <!-- 
     table> 
     <tr>
      <th>ID</th>
      <th>Форма</th>
      <th>УНП</th>`
      <th>Номер</th>
      <th>Дата</th>
      <th>Номер бланка</th>`
      
     </tr>
     -->
     <!--  <tr> 
	      <td> <c:out value="${cert.cert_id}" /> </td>
	      <td> ${cert.forms} </td>
	      <td> ${cert.unn} </td>
	      <td> ${cert.numbercert}</td>
	      <td> ${cert.datecert} </td>
	      <td> ${cert.nblanka} </td>
          
          </tr>  
          -->
     <table>     
	 <c:forEach items="${certs}" var="cert">
	        <tr>
	        <td>${cert.cert_id}</td><td> </td><td>${cert.nomercert}</td>
	        </tr>
     </c:forEach>
     </table>
     <div><a href="${prev_page}">Предыдущая страница</a></div>
     <div><a href="${next_page}">Следующая страница</a></div>
     
</div>
