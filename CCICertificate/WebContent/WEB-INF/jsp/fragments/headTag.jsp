<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>БелТПП Сертификация</title>

    <spring:url value="resources/bootstrap3/css/bootstrap.min.css" var="bootstrapCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    
    <spring:url value="resources/css/cci.css" var="cciCss"/>
    <link href="${cciCss}" rel="stylesheet"/>
    
    <spring:url value="resources/css/dashboard.css" var="dashCss"/>
    <link href="${dashCss}" rel="stylesheet"/>
    

    <spring:url value="resources/jquery/jquery-1.11.1.js" var="jQuery"/>
    <script src="${jQuery}"></script>

    <spring:url value="resources/bootstrap3/js/bootstrap.min.js" var="jBootStrap"/>
    <script src="${jBootStrap}"></script>
    
    <spring:url value="resources/jquery-ui-1.10.3/ui/jquery-ui.js" var="jQueryUiCore"/>
    <script src="${jQueryUiCore}"></script>

    <spring:url value="resources/jquery-ui-1.10.3/themes/base/jquery-ui.css" var="jQueryUiCss"/>
    <link href="${jQueryUiCss}" rel="stylesheet"></link>
    
    
    <script>
  		$(function() {
    		$( "#menu" ).menu();
  		});
  	</script>
    
</head>


