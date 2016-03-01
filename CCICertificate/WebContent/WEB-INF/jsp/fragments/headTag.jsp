<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Intranet Portal BelCCI</title>

    <spring:url value="/resources/bootstrap3/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    
    <spring:url value="/resources/css/cci.css" var="cciCss"/>
    <link href="${cciCss}" rel="stylesheet"/>
    
    <spring:url value="/resources/css/dashboard.css" var="dashCss"/>
    <link href="${dashCss}" rel="stylesheet"/>
    

    <spring:url value="/resources/jquery/jquery-1.11.1.js" var="jQuery"/>
    <script src="${jQuery}"></script>
    
    <spring:url value="/resources/bootstrap3/js/bootstrap.min.js" var="jBootStrap"/>
    <script src="${jBootStrap}"></script>
    
    <spring:url value="/resources/jquery-ui-1.10.3/ui/jquery-ui.js" var="jQueryUiCore"/>
    <script src="${jQueryUiCore}"></script>
    
    <spring:url value="/resources/jspin/jspin.js" var="jSpin"/>
    <script src="${jSpin}"></script>
    
    <spring:url value="/resources/jspin/jquery.spin.js" var="jQuerySpin"/>
    <script src="${jQuerySpin}"></script>

    <spring:url value="/resources/jquery-ui-1.10.3/themes/base/jquery-ui.css" var="jQueryUiCss"/>
    <link href="${jQueryUiCss}" rel="stylesheet"></link>
    
    
    <script>
  		
  		$(function($){
  			$.datepicker.regional['ru'] = {
  				closeText: 'Закрыть',
  				prevText: '&#x3C;Пред',
  				nextText: 'След&#x3E;',
  				currentText: 'Сегодня',
  				monthNames: ['Январь','Февраль','Март','Апрель','Май','Июнь',
  				'Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'],
  				monthNamesShort: ['Янв','Фев','Мар','Апр','Май','Июн',
  				'Июл','Авг','Сен','Окт','Ноя','Дек'],
  				dayNames: ['воскресенье','понедельник','вторник','среда','четверг','пятница','суббота'],
  				dayNamesShort: ['вск','пнд','втр','срд','чтв','птн','сбт'],
  				dayNamesMin: ['Вс','Пн','Вт','Ср','Чт','Пт','Сб'],
  				weekHeader: 'Нед',
  				dateFormat: 'dd.mm.yy',
  				firstDay: 1,
  				isRTL: false,
  				showMonthAfterYear: false,
  				yearSuffix: ''};
  			$.datepicker.setDefaults($.datepicker.regional['ru']);
  		});
  		
  		$(function() {
    		$( "#menu" ).menu();
  		});
  		
  		
  	</script>
    
</head>


