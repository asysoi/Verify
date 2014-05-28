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
	 <c:forEach items="${certs}" var="cert">
	        ${cert.cert_id}  <br>
     </c:forEach>

     <a href="${next_page}">Следующая страница</a>
     
</div>
