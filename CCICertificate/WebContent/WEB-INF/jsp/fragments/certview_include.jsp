<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>


<div class="col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Сертификат</h1>

	 <div class="row placeholders">
    
	  <div class="container">
	       
<table style="text-align: left; width: 951px; height: 390px;" border="1"
cellpadding="2" cellspacing="2">
<tbody>
<tr>
<td colspan="4" rowspan="1"
style="vertical-align: top; height: 89px;">&nbsp; 1.
Грузоотправитель/экспортер(наименование и адрес)<br>
${cert.kontrp}
</td>
<td colspan="1" rowspan="2" style="vertical-align: top;">4.&nbsp;&nbsp;&nbsp;
№ _______________ <br>
<br>
<div style="text-align: center;">Сертификат <br>
о происхождении товара<br>
форма СТ-1<br>
<br>
<div style="text-align: left;">&nbsp; Выдан <span
style="text-decoration: underline;">в&nbsp; Республика Беларусь</span><br>
&nbsp; Для представления в _______________</div>
</div>
</td>
</tr>
<tr>
<td colspan="4" rowspan="1"
style="vertical-align: top; height: 28px;">. 2.
Грузополучатель/импортер(наименование и адрес)</td>
</tr>
<tr>
<td colspan="4" rowspan="1"
style="vertical-align: top; height: 50px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<br>
</td>
<td style="vertical-align: top;">5. Для служебных отметок<br>
</td>
</tr>
<tr>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
</tr>
<tr>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
<td style="vertical-align: top;"><br>
</td>
</tr>
</tbody>
</table>
<br>
	  
	  </div> 
	</div>
</div>