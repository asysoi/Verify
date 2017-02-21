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
<p align="right"><b>${fscert.blanks[0].blanknumber}</b></p>
<h2 align="center">БЕЛОРУССКАЯ ТОРГОВО-ПРОМЫШЛЕННАЯ ПАЛАТА</h2>
<h2 align="center">BELARUSIAN CHAMBER OF COMMERCE AND INDUSTRY</h2>

<h3 align="center"><b>${fscert.branch.name}</b></h3>
<h3 align="center"><b>${fscert.branch.address}</b></h3>

<h1 align="center"><b>СЕРТИФИКАТ СВОБОДНОЙ ПРОДАЖИ</b></h1>
<h1 align="center"><b>CERTIFICATE OF FREE SALE</b></h1>
<p>№ <b>${fscert.certnumber}</b> <b>${fscert.datecert}</b></p>

<p> Экспортер: <b>${fscert.exporter.name}, ${fscert.exporter.cindex}, ${fscert.exporter.line}, ${fscert.exporter.building}, 
${fscert.exporter.city}, ${fscert.exporter.country}</b> </p> 

<p>${fscert.confirmation}</b> </p>

<p>Перечень товаров:
  <table>
       <c:forEach items="${fscert.products}" var="product">
          <tr>
			<td>${product.numerator}. </td>
			<td>${product.tovar};</td>
		  </tr> 
	   </c:forEach>
      </table>

<p>Продолжение перечня товаров смотри в приложении на ${fscert.listscount} листах.</p>
<p> ${fscert.declaration} </p>


<p>Срок действия с  <b>${fscert.dateissue}</b>  по  <b>${fscert.dateexpiry}</b> включительно</p>
<p></p>
 <p style="font-size: 120% "><b>${fscert.signer.name}  ${fscert.signer.job} </b></p>
 </td> <td></td>
<tr> 
<td width="5%" height="20px"></td>
<td width="90%" height="20px"></td>
<td width="5%" height="20px"></td>
</tr>
</table>
 </body>
</html>
	
	






