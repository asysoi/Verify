<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:url value="resources/css/login.css" var="LoginCss" />
<link href="${LoginCss}" rel="stylesheet" />


<div class="main">
	<h3 class="page-header">Сертификат</h3>

	<div class="row placeholders">

		<div class="container" id="message">

			<table style="text-align: left; width: 951px; height: 390px;"
				border="1" cellpadding="2" cellspacing="2">
				<tbody>
					<tr>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; height: 89px; width: 69px;">&nbsp;
							1. Грузоотправитель/экспортер(наименование и адрес)<br>
							${cert.kontrs}<br> ${cert.adress}<br>
						</td>
						<td colspan="3" rowspan="2"
							style="vertical-align: top; width: 85px;">4.&nbsp;&nbsp;&nbsp;
							№ <u>${cert.nomercert}</u>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cert.nblanka} <br> <br>
							<div style="text-align: center;">
								Сертификат <br> о происхождении товара<br> форма СТ-1<br>
								<br>
								<div style="text-align: left;">
									&nbsp; Выдан <span style="text-decoration: underline;">в&nbsp;
										Республика Беларусь</span><br> &nbsp; Для представления в <u>${cert.stranapr}
									</u>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; height: 28px; width: 69px;">. 2.
							Грузополучатель/импортер(наименование и адрес)<BR>
							${cert.poluchat}<br>${cert.adresspol}
						</td>
					</tr>
					<tr>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; height: 50px; width: 69px;">&nbsp;
							3. Средства транспорта и маршрут следования <br> (насколько
							это известно)<br>${cert.transport}, ${cert.marshrut}<br>
						</td>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; width: 85px;">5. Для служебных
							отметок<br>
						</td>
					</tr>
					<tr>
						<td 
							style="vertical-align: top; width: 45px; ">6. №<br>
						</td>
						<td 
							style="vertical-align: top; width: 69px;">7. Количество<br>
							мест и вид<br> упаковки<br>
						</td>
						<td style="vertical-align: top; width: 435px;">8. Описание
							товара<br>
						</td>
						<td style="vertical-align: top; width: 128px;">9. Критерий
							происхождения<br>
						</td>
						<td style="vertical-align: top; width: 137px;">10. Количество
							товара<br>
						</td>
						<td style="vertical-align: top; width: 85px;">11. Номер и<br>
							дата счета-<br>фактуры<br>
						</td>
					</tr>
					
                    <c:set var="count" value="0" scope="page" />
                    <c:set var="position" value="0" scope="page" />
					<c:set var="prevposition" value="0" scope="page" />                    
					<c:forEach items="${cert.products}" var="product">
						<tr>
							<td style="vertical-align: top; width: 45px; border-bottom-style:none; border-top-style: none;">
								${product.numerator}<br>
							</td>
                            <c:if test="${product.vidup != null}">  
							    <td id="g${count}" rowspan="${fn:length(cert.products) - position}" style="vertical-align: top; width: 89px; border-bottom-style:none; border-top-style: none;">
                                   ${product.vidup}<br>
								</td>
								<c:if test="${count > 0}">
								     <script> $(function() { $('#g${count-1}').attr('rowspan', ${position-prevposition});}); </script>
								     <c:set var="prevposition" value="${position}" scope="page"/>
								</c:if>
								<c:set var="count" value="${count + 1}" scope="page"/> 
							</c:if>
							<td style="vertical-align: top; width: 415px;border-bottom-style:none; border-top-style: none;">	
                                ${product.tovar}<br>
							</td>

							<td style="vertical-align: top; width: 128px; border-bottom-style:none; border-top-style: none;">	
                                ${product.kriter}
                            </td>

							<td style="vertical-align: top; width: 137px; border-bottom-style:none; border-top-style: none;">	
                                ${product.ves}<br>
							</td>

							<td style="vertical-align: top; width: 85px; border-bottom-style:none; border-top-style: none;">	
                                ${product.schet}
							</td>
						</tr>
						<c:set var="position" value="${position + 1}" scope="page"/>
					</c:forEach>

					<tr>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; height: 0px; width: 69px;">12.
							Удостоверение<br> Настоящим удостоверяется, что декларация<br>
							заявителя соответствует действительности<br> <br> <br>
							Унитарное предприятие по оказанию услуг<br>
							${cert.otd_name},<br> ${cert.otd_address_index},
							${cert.otd_address_city}, ${cert.otd_address_line},
							${cert.otd_address_home}<br> <br> [Подпись]
							${cert.expert} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Дата:
							${cert.datacert}<br> [Печать]

						</td>
						<td colspan="3" rowspan="1"
							style="vertical-align: top; height: 0px; width: 85px;">13.
							Декларация заявителя<br> Нижеподписавшийся заявляет, что
							вышепреведенные сведения соответствуют действительности: что все
							товары полностью произведены или подвергнуты достаточной
							переработке в<br>
							<div>
								<u>Республике Беларусь</u>
							</div> <br> и что они отвечают требованиям происхождения,
							установленными в отношении таких товаров <br> <br>
							[Подпись] ${cert.rukovod}<br> Дата: ${cert.datacert}

						</td>
					</tr>
				</tbody>
			</table>
			<br>

		</div>
	</div>
</div>