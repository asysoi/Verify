<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="resources/css/cci.css" var="CCICss" />

<html> 
<header>

<link href="${CCICss}" rel="stylesheet" />
</header>
<body>

<table>
<tr> 
<td width="5%" height="20px"></td>
<td width="90%" height="20px"></td>
<td width="5%" height="20px"></td>
</tr>
<tr>
<td > </td>
<td width="90%">
<p align="right"><b>${viewcert.cert.blanknumber}</b></p>
<h2 align="center">БЕЛОРУССКАЯ ТОРГОВО-ПРОМЫШЛЕННАЯ ПАЛАТА</h2>
<h3 align="center"><b>${viewcert.beltpp}</b></h3>
<h1 align="center"><b>СЕРТИФИКАТ </b> № ${viewcert.cert.number}</h1> 
<p align="center">${viewcert.certtype}</p>   
<p>1. Производитель: <b>${viewcert.customer}</b> </p> 
<p>${viewcert.titlebrancheslist}<br><b>${viewcert.cert.branchlist}</b> </p>
<p>${viewcert.titleunp}<b>${viewcert.cert.customerunp}</b></p>
<p>${viewcert.placeproduction}</p>
<p>${viewcert.titleproductslist}
  <table>
  <tr>
  		    <td>Номер</td>
			<td>Наименование</td>
			<td>Код ТН ВЭД ТС</td>
			</tr>
       <c:forEach items="${viewcert.cert.products}" var="product">
          <tr>
			<td>${product.number}</td>
			<td>${product.name}</td>
			<td>${product.code}</td>
		  </tr> 
	   </c:forEach>
      </table>
<p>${viewcert.validdates}</p>
<p>${viewcert.confirmation}</p>
 <p style="font-size: 120% "><b>${viewcert.cert.signerjob}  ${viewcert.cert.signer}  ${viewcert.cert.datecert}</b></p>
 </td>
 <td></td>
<tr> 
<td width="5%" height="20px"></td>
<td width="90%" height="20px"></td>
<td width="5%" height="20px"></td>
</tr>
</table>
 </body>
</html>
	
	






