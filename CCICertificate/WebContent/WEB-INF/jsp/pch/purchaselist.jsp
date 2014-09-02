<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>


<div class="col-md-10 col-md-offset-2 main">
     <table style="width:100%">     
      <tr>
        <c:forEach items="${headers}" var="item">
	        <td style="width:${item.width}%"><a href="${item.link}">${item.name}${item.selection}</a></td>
        </c:forEach>
     </tr>  
	 <c:forEach items="${purchases}" var="item">
	        <tr>
	        <td style="width:15%"><a href="purchaseview.do?id=${item.id}">${item.pchDateString}</a></td>
	        <td style="width:15%">${item.product}</td>
	        <td style="width:40%">${item.department}</td>
	        <td style="width:10%">${item.price}</td>
	        <td style="width:10%">${item.volume}</td>
	        <td style="width:10%">${item.company}</td>
	        </tr>
     </c:forEach>
     <tr>
     <td><br></td>
     <td> </td><td></td><td> </td><td> </td>
     <td><br></td>
     </tr>
     <tr>
     <td><a href="${prev_page}">Предыдущая страница</a></td>
     <td> </td><td></td><td> </td><td> </td>
     <td><a href="${next_page}">Следующая страница</a></td>
     </tr>
     </table>     
     
</div>
