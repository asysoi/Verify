<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Intranet Portal BelCCI</title>

<spring:url value="/resources/bootstrap3/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />

<spring:url value="/resources/css/cci.css" var="cciCss" />
<link href="${cciCss}" rel="stylesheet" />

<spring:url value="/resources/css/dashboard.css" var="dashCss" />
<link href="${dashCss}" rel="stylesheet" />

<spring:url value="/resources/cci/cci.js" var="jCCI" />
<script src="${jCCI}"></script>

<spring:url value="/resources/jquery/jquery-1.11.1.js" var="jQuery" />
<script src="${jQuery}"></script>

<spring:url value="/resources/bootstrap3/js/bootstrap.min.js"
	var="jBootStrap" />
<script src="${jBootStrap}"></script>

<spring:url value="/resources/jquery-ui-1.10.3/ui/jquery-ui.js"
	var="jQueryUiCore" />
<script src="${jQueryUiCore}"></script>

<spring:url value="/resources/jspin/jspin.js" var="jSpin" />
<script src="${jSpin}"></script>

<spring:url value="/resources/jspin/jquery.spin.js" var="jQuerySpin" />
<script src="${jQuerySpin}"></script>

<spring:url
	value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css"
	var="jQueryUiCss" />
<link href="${jQueryUiCss}" rel="stylesheet"></link>

<spring:url value="/resources/jqgrid/css/ui.jqgrid.css" var="jqGridCss" />
<link href="${jqGridCss}" rel="stylesheet"></link>

<spring:url value="/resources/jqgrid/js/i18n/grid.locale-ru.js"
	var="jqGridLocale" />
<script src="${jqGridLocale}"></script>

<spring:url value="/resources/jqgrid/js/jquery.jqGrid.min.js"
	var="jqGrid" />
<script src="${jqGrid}"></script>

<script>
	$(function($) {
		$.datepicker.regional['ru'] = {
			closeText : 'Закрыть',
			prevText : '&#x3C;Пред',
			nextText : 'След&#x3E;',
			currentText : 'Сегодня',
			monthNames : [ 'Январь', 'Февраль', 'Март', 'Апрель', 'Май',
					'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь',
					'Декабрь' ],
			monthNamesShort : [ 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн',
					'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек' ],
			dayNames : [ 'воскресенье', 'понедельник', 'вторник', 'среда',
					'четверг', 'пятница', 'суббота' ],
			dayNamesShort : [ 'вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт' ],
			dayNamesMin : [ 'Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб' ],
			weekHeader : 'Нед',
			dateFormat : 'dd.mm.yy',
			firstDay : 1,
			isRTL : false,
			showMonthAfterYear : false,
			yearSuffix : ''
		};
		$.datepicker.setDefaults($.datepicker.regional['ru']);
	});

	$(function() {
		$("#menu").menu();
	});
</script>

<style>
.footer {
	position: fixed;
	left: 0;
	bottom: 0;
	width: 100%;
	background-color: #36478B;
	background: linear-gradient(to top left, #395B8D, #C1DDF1, #395B8D);
	color: #36478B;
	text-align: left;
	vertical-align: middle;
	height: 40;
}

.yline {
	height: 4;
	background-color: grey;
	width: 100%;
	margin-top: 0px;
	margin-bottom: 0px;
	margin-right: 0px;
	margin-left: 0px;
}

.brand {
	color__: #E9C92E;
	color: #36478B;
	text-decoration: none;
}

a:hover {
	color_: #E9C92E;
	color: #36478B;
	text-decoration: none;
}
</style>

</head>


