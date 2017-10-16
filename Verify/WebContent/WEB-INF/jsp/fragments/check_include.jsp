﻿<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="resources/css/login.css" var="LoginCss" />
<link href="${LoginCss}" rel="stylesheet" />

<script>

   $("document").ready(function() {
		$("#pdfview").dialog({
			autoOpen : false
		});
	});


	$(function() {

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(function() {
			$("#datepicker").datepicker("option", "dateFormat", 'dd.mm.yy');
			$("#datepicker").datepicker("setDate", "${cert.datacert}");
		});
	});

    function setLanguage(lang) {
    	
		url = "check.do?lang=" + lang 
				+ "&ncert=" + $("#nomercert").val()  
				+ "&nblanka=" + $("#nblanka").val() 
				+ "&datecert=" + $("#datepicker").val();
  		document.location.href = url;
    }
	
	function submit() {
		var opts = {
				  lines: 11, // The number of lines to draw
				  length: 15, // The length of each line
				  width: 8, // The line thickness
				  radius: 20, // The radius of the inner circle
				  corners: 1, // Corner roundness (0..1)
				  rotate: 0, // The rotation offset
				  direction: 1, // 1: clockwise, -1: counterclockwise
				  color: '#000', // #rgb or #rrggbb or array of colors
				  speed: 1, // Rounds per second
				  trail: 60, // Afterglow percentage
				  shadow: false, // Whether to render a shadow
				  hwaccel: false, // Whether to use hardware acceleration
				  className: 'spinner', // The CSS class to assign to the spinner
				  zIndex: 2e9, // The z-index (defaults to 2000000000)
				  top: '50%', // Top position relative to parent
				  left: '50%' // Left position relative to parent
			   };
	    var spinner = new Spinner(opts).spin(document.getElementById('checker'));
		var errors = 0;
		$("#certview").empty();
		
	    $("#form :input").map(function(){
	         if( !$(this).val() ) {
	              errors++;
	        }   
	    });
	    
	    if(errors > 0) {
	    	lang = "${lang}";
	    	if ( lang == "ru") {
			    $("#certview").html("Все аттрибуту должны быть заполнены");
	        } else {
	        	$("#certview").html("All fields have to be filled");
	        } 
			spinner.stop();
	    } else {
			url = "check.do";
			var posting = $.post(url, $("#form").serialize());
             
           	posting.done(function(data) {
			   var content = $(data).find("#message");
			   $("#certview").html(content);
			   spinner.stop();
		    });
	    }
	}

	function viewCertificate(link) {
	    $('#pdf').contents().find('body').attr('style', 'background-color: black'); 
		$("#pdfview").dialog("option", "title", 'Сертификат');
		$("#pdfview").dialog("option", "width", 963);
		$("#pdfview").dialog("option", "height", 570);
		$("#pdfview").dialog("option", "modal", true);
		$("#pdfview").dialog("option", "resizable", false);
		$("#pdfview").dialog({
			buttons : [ 	{ text : "Закрыть",	click : function() {$(this).dialog("close"); $('#pdf').contents().find("body").html('');}} ]
		});

		$("#pdfview").dialog("option", "position", {
			my : "center",
			at : "center",
			of : window
		});
                                   
        $('#pdf').attr('height', $("#pdfview").dialog("option", "height") - 150);
        $('#pdf').attr('width', $("#pdfview").dialog("option", "width") - 40);
        $('#pdf').attr('scrolling', 'yes');
		$('#pdf').attr('src', link);

		$("#pdfview").dialog("open");
	}

	function openCertificate(link) {
		var win=window.open(link,'_blank');
		win.focus();
	}

</script>

<div class="main" id="checker">
	
    <div class="row placeholders" id="msg" class="ver_message">
    
    <c:if test="${lang=='ru'}">
        <h3 class="page-header">Cертификат происхождения</h3>
    </c:if>
    <c:if test="${lang=='eng'}">
        <h3 class="page-header">Certificate of origin</h3>
    </c:if>
    

	<form:form id="form" method="POST" commandName="cert" role="form">
		<table class="verification">
			<tr style="height: 46px;">
				<td style="text-align: right; height: 36px; vertical-align: middle; font-size: 120%;">
				
    			<c:if test="${lang=='ru'}">
						Номер сертификата&nbsp;        
    			</c:if>
    			<c:if test="${lang=='eng'}">
        				Certificate number&nbsp;
    			</c:if>

				</td>
				<td><form:input path="nomercert" class="form-control" type="text" placeholder="" id="nomercert" style="height: 28px; width: 60%;" /></td>
			</tr>
			<tr style="height: 46px;">
				<td style="text-align: right; height: 36px; vertical-align: middle; font-size: 120%;">
				<c:if test="${lang=='ru'}">
						Номер бланка&nbsp;        
    			</c:if>
    			<c:if test="${lang=='eng'}">
        				Form number&nbsp;
    			</c:if>
				</td>
				<td><form:input path="nblanka" class="form-control" type="text" placeholder="" id="nblanka" style="height: 28px; width: 60%;"/></td>
			<tr style="height: 46px;">
				<td style="text-align: right; height: 36px; vertical-align: middle; font-size: 120%;">
				<c:if test="${lang=='ru'}">
						Дата выдачи&nbsp;        
    			</c:if>
    			<c:if test="${lang=='eng'}">
        				Issue date&nbsp;
    			</c:if>
				</td>
				<td><form:input path="datacert" class="form-control" placeholder="" id="datepicker" style="height: 28px; width: 60%;"/></td>
			</tr>
			<tr>
				<td />
				<td>
					<div align="left">
						<a href="javascript:submit()" style="height: 28px; font-size: 100%">
						<div class="btn btn-lg btn-primary btn-block" style="background-color: #36478B; width: 40%;">
						
						<c:if test="${lang=='ru'}">
								Проверить        
    					</c:if>
    					<c:if test="${lang=='eng'}">
        						Check
    					</c:if>
    					
						</div>
						</a>
					</div>
				</td>
			</tr>
		</table>
	</form:form>
	
	<div id="certview" name="certview" align="center"></div>


	<div id="pdfview" name="pdfview" style="text-align:center;">
	          <iframe class="pdf" id="pdf"></iframe>
    </div> 
    
    </div>
   	
</div>



