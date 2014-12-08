<table class="certificate" style="width: 100%">
	<tr>
		<c:forEach items="${headers}" var="item">
			<td style="width: 80%; background-color: gray; color: black">
				${item}</td>
			<td style="width: 20%; background-color: gray; color: black">Количество</td>
		</c:forEach>
	</tr>

	<c:forEach items="${reports}" var="report">
		<tr>
			<td>${report.field}</td>
			<td>${report.value}</td>
		</tr>
	</c:forEach>
</table>