<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<link href="${CCICss}" rel="stylesheet" />

<script>

	function reloadFSNumber() {
		url = "rldfsnumber.do?codecountry="+$("#codecountrytarget").val() ;
		$.get(url, function(data, status) {
	    	$("#certnumber").val(data);
		});	
	}

	function getListCount() {
		url = "getlistcount.do";
		$.get(url, function(data, status) {
	     	$("#listscount").text(data);
		});	
	}

	function save() {
	  if (inputValidate() == 0) {
            
            if ( $('#locked').prop('checked') ) {
    			$("#confirm").text("Сохранный сертификат с установленной пометкой о выдаче не может быть впоследствии отредатирован. Сохранять?");
	     		$( "#dialog-confirm" ).dialog({
 			    	  buttons: {
 			        	"Сохранить": function() {
 							$.ajax({
 						  		type:    "POST",
 						  		url:     "fsedit.do",
 						  		data:    $("#fscert").serialize(),
 						  		success: function(data) {
 							 			popupMessage("Cохранение сертификата", "Сертификат сохранен в базе");
 						  		},
 			 		      		error:  function(jqXHR, textStatus, errorThrown) {
 			 		    	 		var obj = JSON.parse("" + jqXHR.responseText);
 			 		    	 		popupMessage("Ошибка сохранения сертификата", obj.error);
 						  		}
 							});
   			          		$( this ).dialog( "close" );
 			            },
 			            "Отменить": function() {
 			               saveflag = false;	
 			          	   $( this ).dialog( "close" );
 			            }
 			           }
 			    });
	     		$("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
	      		$("#dialog-confirm" ).dialog("option", "modal", true);
	      		$("#dialog-confirm" ).dialog("option", "resizable", false );
	     		$("#dialog-confirm").dialog( "option", "title", "Сохранить сертификат");
 				$("#dialog-confirm").dialog("open");
            } else {
   				$.ajax({
			  		type:    "POST",
			  		url:     "fsedit.do",
			  		data:    $("#fscert").serialize(),
			  		success: function(data) {
				 			popupMessage("Cохранение сертификата", "Сертификат сохранен в базе");
			  		},
 		      		error:  function(jqXHR, textStatus, errorThrown) {
 		    	 		var obj = JSON.parse("" + jqXHR.responseText);
 		    	 		popupMessage("Ошибка сохранения сертификата", obj.error);
			  		}
				});
            }
	    }
	}
	
	function saveAndClose() {
	  if (inputValidate() == 0) {
		  
         if ( $('#locked').prop('checked') ) {
	   	  	  $("#confirm").text("Сохранный сертификат с установленной пометкой о выдаче не может быть впоследствии отредатирован. Сохранять?");
   			  $( "#dialog-confirm" ).dialog({
		    	  buttons: {
		        	"Сохранить": function() {
						saveCertificateAjax();
			          	$( this ).dialog( "close" );
		            },
		            "Отменить": function() {
		               saveflag = false;	
		          	   $( this ).dialog( "close" );
		            }
		           }
		     });
   			 $("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
    		 $("#dialog-confirm" ).dialog("option", "modal", true);
    		 $("#dialog-confirm" ).dialog("option", "resizable", false );
   		 	 $("#dialog-confirm").dialog( "option", "title", "Сохранить сертификат");
			 $("#dialog-confirm").dialog("open");
      	 } else {
      		saveCertificateAjax()
         }
      }
	}
	
	function saveCertificateAjax() {
		$.ajax({
	  		type:    "POST",
	  		url:     "fsedit.do",
	  		data:    $("#fscert").serialize(),
	  		success: function(data) {
	  			location.href='fscerts.do?page=${fsmanager.page}&pagesize=${fsmanager.pagesize}&orderby=${fsmanager.orderby}&order=${fsmanager.order}';
	  		},
	      		error:  function(jqXHR, textStatus, errorThrown) {
	    	 		var obj = JSON.parse("" + jqXHR.responseText);
	    	 		popupMessage("Ошибка сохранения сертификата", obj.error);
	  		}
		});
	}
	
	function popupMessage(title, data) {
		$("#message").text(data);
		$( "#dialog-message" ).dialog("option", "title", title);
  		$( "#dialog-message" ).dialog("option", "width", 420);
  		$( "#dialog-message" ).dialog("option", "height", 220);
  		$( "#dialog-message" ).dialog("option", "modal", true);
  		$( "#dialog-message" ).dialog("option", "resizable", true );
  		$( "#dialog-message" ).dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
		$("#dialog-message").dialog("open");
	}
		
	$(function() {
		
		$("#parentnumber").autocomplete({
		   source: "parentlist.do",
	       minLength: 2
		} );
		
		$(".datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(
				function() {
					$("#fsview").dialog({
						autoOpen : false
					});

					$( "#dialog-confirm" ).dialog({
						  autoOpen : false,
					  	  width: 420,
						  height: "auto",
					      modal: true,
         			      resizable: false
					});

					
					$( "#dialog-message" ).dialog({
						  autoOpen : false,
					  	  width: 420,
						  height: "auto",
					      modal: true,
					      buttons: {
					        Ok: function() {
					          $( this ).dialog( "close" );
					        }
					      }
					});
					 
					$( "#dialog-addproductlist" ).dialog({
						  autoOpen : false,
	            	      resizable: true,
	            	      height: "auto",
	            	      width: 400,
	            	      modal: true,
	            	      buttons: {
	            	        "Добавить продукты": function() {
	                         	 var grid = $("#products");
	                             $.ajaxSetup({async:false});
	                             $.post("fsaddproducts.do", $("#addproductlist").serialize());
	                             grid.trigger('reloadGrid');
	            	             $( this ).dialog( "close" );
	            	        },
	            	        "Закрыть": function() {
	              	             $( this ).dialog( "close" );
	            	        }
	            	      }
	            	});
					
					$( "#dialog-addblanklist" ).dialog({
						  autoOpen : false,
	            	      resizable: true,
	            	      height: "auto",
	            	      width: 400,
	            	      modal: true,
	            	      buttons: {
	            	        "Добавить номера": function() {
	                         	 var grid = $("#blanks");
	                             $.ajaxSetup({async:false});
	                             $.post("fsaddblanks.do", $("#addblanklist").serialize());
	                             grid.trigger('reloadGrid');
	            	             $( this ).dialog( "close" );
	            	        },
	            	        "Закрыть": function() {
	              	             $( this ).dialog( "close" );
	            	        }
	            	      }
	            	});

					
					$(".datepicker").datepicker("option", "dateFormat",
							'dd.mm.yy');
		  		    $("#language")
						    .val('${fscert.language}');
		  		    $("#codecountrytarget")
							.val('${fscert.codecountrytarget}');
					$("#otd_id")
							.val('${fscert.otd_id}');
				});

		// Product grid  
		var lastSelection;
		
		jQuery("#products").jqGrid({
    		url: "fsgoods.do",
    		editurl: "fsgoodsupdate.do",
    		datatype: "xml",
    		mtype: "GET",
    		height: '20%',
			width : null,
			shrinkToFit : false,
   			colModel:[
   				{label:'Номер',name:'numerator', index:'numerator', width:250, sortable:false, editrules:{number:true}},
   	    		{label:'Наименование товара', name:'tovar', index:'tovar', width:720, editable: true, sortable:false}
		   	],
		    rowNum: 10,
		    rowList:[5,10,20,50],
		   	sortname: 'numerator',
		   	sortorder: 'asc',
   			viewrecords: true,
   			pager: jQuery('#pagerproducts'),
    	    gridview: true,
    		autoencode: true,
   			caption: "Товары",
   			ondblClickRow : function editRow(id) {
				 var grid = $("#products");
             	 if (id && id !== lastSelection) {
                	 grid.jqGrid('restoreRow',lastSelection);
                 	 lastSelection = id;
             	 }
             	 grid.jqGrid('editRow',id, {keys: true} );
            }
		});
		
		$('#products').jqGrid('navGrid', "#pagerproducts", {                
    		search: false, 
    		add: false,
    		edit: false,	
    		del: false,
    		refresh: false,
    		view: false
		}).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-plus',
            onClickButton: function(id) {
                var lastsel = id;
                $.ajaxSetup({async:false});
        		$.get("fsaddproduct.do");
                jQuery("#products").trigger('reloadGrid');
            },
            title: "Добавить продукт в конец списка",
            position: "last"
        }).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-closethick',
            onClickButton: function(event) {
            	 var grid = $("#products");
                 var id = grid.jqGrid('getGridParam','selrow');
                 var recs = grid.getGridParam("reccount");
            	 
            	 if (recs > 0 ) {
     		     	if (id) { 
     		     		$("#confirm").text("Удалить выбранный продукт?");
     		     		$( "#dialog-confirm" ).dialog({
             			      buttons: {
             			        "Удалить": function() {
                                  $.ajaxSetup({async:false});
                         		  $.get("fsdelproduct.do?id="+id);
                         		  grid.trigger('reloadGrid');
               			          $( this ).dialog( "close" );
             			        },
             			        "Отменить": function() {
             			          $( this ).dialog( "close" );
             			        }
             			      }
             			});
     		     		$("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
     		     		$("#dialog-confirm").dialog( "option", "title", "Удаление продукта");
             			$( "#dialog-confirm" ).dialog("open");
     		     	} else { 
     		     		$("#dialog-message").dialog("option", "title", 'Удаление продукта');
     		     		$("#dialog-message").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
     		     		$("#message").text("Продукт не выбран. Выберете продукт для удаления.");
 						$("#dialog-message").dialog("open");
    	            }
            	 }
            },
            title: "Удалить выбранный продукт",
            position: "last"
        }).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-circle-triangle-n',
            onClickButton: function(event) {
            	 var grid = $("#products");
            	 var id = grid.jqGrid('getGridParam','selrow');
                 var recs = grid.getGridParam("reccount");
            	 
            	 if (recs > 0 ) {
     		       if (id) { 
                     $.ajaxSetup({async:false});
             		 $.get("fsinsertproduct.do?id="+id);
     		       } else { 
  		     	 	 $("#dialog-message").dialog("option", "title", 'Вставить продукт');
  		     		 $("#dialog-message").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
 		     		 $("#message").text("Продукт не выбран. Выберете продукт для добавления перед ним нового.");
					 $("#dialog-message").dialog("open");
                   }  
                   grid.trigger('reloadGrid');
            	 }
            },
            title: "Вставить продукт перед текущим",
            position: "last"
        }).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-script',
            onClickButton: function(event) {
            	$("#productlist").val(''); 
            	$("#dialog-addproductlist").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
            	$("#dialog-addproductlist" ).dialog("open"); },
            title: "Добавить список продуктов",
            position: "last"
        }).navButtonAdd('#pagerproducts',{
		    caption: '',	
            buttonicon: 'ui-icon-trash',
            onClickButton: function(event) {
            	var grid = $("#products");
                var recs = grid.getGridParam("reccount");
          	 
 	           	if (recs > 0 ) {
 		     		$("#confirm").text("Удалить все продукты из списка продуктов?");
 		     		$( "#dialog-confirm" ).dialog({
         			      buttons: {
         			        "Удалить": function() {
          	                  $.ajaxSetup({async:false});
            	    		  $.get("fsdelallproducts.do");
                	          grid.trigger('reloadGrid');
           			          $( this ).dialog( "close" );
         			        },
         			        "Отменить": function() {
         			          $( this ).dialog( "close" );
         			        }
         			      }
         			});
 		     		$("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
 		     		$("#dialog-confirm").dialog( "option", "title", "Удаление продуктов");
         			$( "#dialog-confirm" ).dialog("open");
 	           	}
            },
            title: "Удалить все продукты",
            position: "last"
        });
		
		grid = $("#products");
		grid.jqGrid('gridResize', {minWidth: 350, minHeight: 150});
		$("#pagerproducts_left", "#pagerproducts").width(250);
		
		//Blank grid
		jQuery("#blanks").jqGrid({
    		url: "fsblanks.do",
    		editurl: "fsblankupdate.do",
    		datatype: "xml",
    		mtype: "GET",
    		height: '20%',
			width : null,
			shrinkToFit : false,
   			colModel:[
   				{label:'Номер листа',name:'page', index:'page', width:250, sortable:false, editrules:{number:true}},
   	    		{label:'Номер бланка', name:'blanknumber', index:'blanknumber', width:720, editable: true, sortable:false}
		   	],
		    rowNum: 10,
		    rowList:[5,10,20,50],
		   	sortname: 'page',
		   	sortorder: 'asc',
   			viewrecords: true,
   			pager: jQuery('#pagerblanks'),
    	    gridview: true,
    		autoencode: true,
   			caption: "Номера бланков сертификата",
   			ondblClickRow : function editRow(id) {
				 var grid = $("#blanks");
             	 if (id && id !== lastSelection) {
                	 grid.jqGrid('restoreRow',lastSelection);
                 	 lastSelection = id;
             	 }
             	 grid.jqGrid('editRow',id, {keys: true} );
            }
		});

		$('#blanks').jqGrid('navGrid', "#pagerblanks", {                
    		search: false, 
    		add: false,
    		edit: false,	
    		del: false,
    		refresh: false,
    		view: false
		}).navButtonAdd('#pagerblanks',{
		    caption: '',	
            buttonicon: 'ui-icon-plus',
            onClickButton: function(id) {
                var lastsel = id;
                $.ajaxSetup({async:false});
        		$.get("fsaddblank.do");
                jQuery("#blanks").trigger('reloadGrid');
            },
            title: "Добавить бланк в конец списка",
            position: "last"
        }).navButtonAdd('#pagerblanks',{
		    caption: '',	
            buttonicon: 'ui-icon-closethick',
            onClickButton: function(event) {
            	 console.log(event);
            	 var grid = $("#blanks");
            	 var id = grid.jqGrid('getGridParam','selrow');
                 var recs = grid.getGridParam("reccount");
              	 
  	           	if (recs > 0 ) {
	     		     if (id) { 
     		     		$("#confirm").text("Удалить выбранный номер бланка?");
     		     		$( "#dialog-confirm" ).dialog({
             			      buttons: {
             			        "Удалить": function() {
            	                    $.ajaxSetup({async:false});
                	         		$.get("fsdelblank.do?id="+id);
             			        	grid.trigger('reloadGrid');
               			           $( this ).dialog( "close" );
             			        },
             			        "Отменить": function() {
             			          $( this ).dialog( "close" );
             			        }
             			      }
             			});
     		     		$("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
     		     		$("#dialog-confirm").dialog( "option", "title", "Удаление номера бланка");
             			$( "#dialog-confirm" ).dialog("open");
     			     } else { 
      		     		$("#dialog-message").dialog("option", "title", 'Удаление бланка');
      		     		$("#dialog-message").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
     		     		$("#message").text("Бланк не выбран. Выберете бланк для удаления.");
 						$("#dialog-message").dialog("open");
                	 }  
                 	grid.trigger('reloadGrid');
  	           	}
            },
            title: "Удалить выбранный бланк",
            position: "last"
        }).navButtonAdd('#pagerblanks',{
		    caption: '',	
            buttonicon: 'ui-icon-circle-triangle-n',
            onClickButton: function(event) {
            	 var grid = $("#blanks");
            	 var id = grid.jqGrid('getGridParam','selrow');
                 var recs = grid.getGridParam("reccount");
            	 
            	 if (recs > 0 ) {
		   		     if (id) { 
        	            $.ajaxSetup({async:false});
            	 		$.get("fsinsertblank.do?id="+id);
     		    	 } else { 
	   		     		$("#dialog-message" ).dialog("option", "title", 'Добавление бланка');
   			     	    $( "#dialog-message" ).dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
 			     		$("#message").text("Бланк не выбран. Выберете бланк для вставки перед ним нового.");
						$("#dialog-message").dialog("open");
                	 }  
                 	grid.trigger('reloadGrid');
            	 }
            },
            title: "Вставить бланк перед текущим",
            position: "last"
        }).navButtonAdd('#pagerblanks',{
		    caption: '',	
            buttonicon: 'ui-icon-script',
            onClickButton: function(event) {
            	$("#blanklist").val(''); 
            	$("#dialog-addblanklist").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
            	$("#dialog-addblanklist" ).dialog("open"); },
            title: "Добавить список бланков",
            position: "last"
        }).navButtonAdd('#pagerblanks',{
		    caption: '',	
            buttonicon: 'ui-icon-trash',
            onClickButton: function(event) {
            	var grid = $("#blanks");
                var recs = grid.getGridParam("reccount");
          	 
 	           	if (recs > 0 ) {
        	        $("#confirm").text("Удалить все номера бланков из списка?");
 		     		$( "#dialog-confirm" ).dialog({
         			      buttons: {
         			        "Удалить": function() {
          	                  $.ajaxSetup({async:false});
              	    		  $.get("fsdelallblanks.do");
                	          grid.trigger('reloadGrid');
           			          $( this ).dialog( "close" );
         			        },
         			        "Отменить": function() {
         			          $( this ).dialog( "close" );
         			        }
         			      }
         			});
 		     		$("#dialog-confirm").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
 		     		$("#dialog-confirm").dialog( "option", "title", "Удаление номеров бланков");
         			$( "#dialog-confirm" ).dialog("open");
 	           	}
            },
            title: "Удалить все бланки",
            position: "last"
        });
		
		grid = $("#blanks");
		grid.jqGrid('gridResize', {minWidth: 350, minHeight: 100});
		$("#pagerblanks_left", "#pagerblanks").width(250);
		
	});

	function clearelement(element) {
		element.val('');
	}

	function openClients(clienttype) {
        //link="sclients.do?pagesize=5&clienttype="+clienttype+"&lang="+$("#language").val();
        link="sglients.do?clienttype="+clienttype;
		$("#fsview").load(link);
		$("#fsview").dialog("option", "title", 'Список компаний');
		$("#fsview").dialog("option", "width", 1100);
		$("#fsview").dialog("option", "height", 420);
		$("#fsview").dialog("option", "modal", true);
		$("#fsview").dialog("option", "resizable", true );
		$("#fsview").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
		$("#fsview").dialog("open"); 
	}
	
	function reloadConfirmation() {
		url = "rldconfirm.do?lang=" + $("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log(data); 
			 console.log($("#confirmation").text());
		     $("#confirmation").val(data);
		});	
	}
	
	function reloadDeclaration() { 
		url = "rlddecl.do?lang=" + $("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log(data); 
			 console.log($("#declaration").text());
		     $("#declaration").val(data);
		});	
	}
	
	function openEmployees(employeetype) {
        link="sgemp.do?pagesize=5&employeetype="+employeetype;
		$("#fsview").load(link);
		$("#fsview").dialog("option", "title", 'Список сотрудников');
		$("#fsview").dialog("option", "width", 1100);
		$("#fsview").dialog("option", "height", 420);
		$("#fsview").dialog("option", "modal", true);
		$("#fsview").dialog("option", "resizable", true );
		$("#fsview").dialog( "option", "position", { my: "center",  at: "center", of:centerdiv} );
		$("#fsview").dialog("open");
	}
	
	
	function changeLanguage() {
		url = "rldlang.do?lang=" + $("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 var obj = JSON.parse(data);
		     $("#exporter").text(obj.exporter);
		     $("#producer").text(obj.producer);
		     $("#expert").text(obj.expert);
		     $("#signer").text(obj.signer);
		     $("#branchname").text(obj.branchname);
		     $("#branchaddress").text(obj.branchaddress);
		     $("#branchcontact").text(obj.branchcontact);
		});
	}
</script>

<c:if test="${not empty error}">
<div id="error" class="error">${error}</div>
</c:if>  

<form:form id="fscert" method="POST" modelAttribute="fscert">

<div class="row">
<div class="col-md-6" align="left"> Сертификат выдан : <form:checkbox path="locked" id="locked"/>
</div>
<div class="col-md-6" align="right">
<a href="javascript:goBack();" title="Вернуться к списку сертификатов"><i class="glyphicon glyphicon-arrow-left"></i></a>

<c:if test="${! fscert.locked}">
<a href="javascript:save();" title="Сохранить сертификат в хранилище сертификатов и продолжить редактирование"><i class="glyphicon glyphicon-save" ></i></a>
<a href="javascript:saveAndClose();" title="Сохранить сертификат и вернуться к списку сертификатов"><i class="glyphicon glyphicon-floppy-save" ></i></a>
</c:if>

<a href="javascript:printFSCertificate();" title="Вывести сертификат в формате PDF"><i class="glyphicon glyphicon-print" ></i></a>
<a href="javascript:printFSCertificateCopy();" title="Вывести копию сертификата в формате PDF"><i class="glyphicon glyphicon-print" style="color: green"></i></a>
</div>
</div>

<h3 align="center"><b>СЕРТИФИКАТ СВОБОДНОЙ ПРОДАЖИ</b></h3>
<h4 align="center">

<c:if test="${fscert.language == 'RU'}">  
<div id="branchname">${fscert.branch.name}</div>
<div id="branchaddress">${fscert.branch.address}</div>
<div id="branchcontact">телефон: ${fscert.branch.phone}, факс: ${fscert.branch.fax}, e-mail: ${fscert.branch.email}</div>
</c:if>
  
<c:if test="${fscert.language != 'RU'}">
<div id="branchname">${fscert.branch.getLocale(fscert.language).name}</div>
<div id="branchaddress">${fscert.branch.getLocale(fscert.language).address}</div>
<div id="branchcontact">phone: ${fscert.branch.phone}, fax: ${fscert.branch.fax}, e-mail: ${fscert.branch.email}</div>
</c:if>

</h4>

<form:hidden path="id"/>
<div class="container-fluid">

<div class="row">
<div class="col-md-6">Номер сертификата: <form:input path="certnumber" id="certnumber" size="15"/>
<a href="javascript:reloadFSNumber();" title="Сгенерировать номер">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a>
</div>
<div class="col-md-6">Дата сертификата: <form:input path="datecert" id="datecert" class="datepicker" size="12"/></div> 
</div>

<div class="row">
<div class="col-md-12 ui-widget" >Дубликат сертификата: <form:input path="parentnumber" id="parentnumber" /></div>				
</div>

<div class="row">
<div class="col-md-6">Выдан для представления в: <form:select path="codecountrytarget"						
						items="${countries}" id="codecountrytarget" /></div>
<div class="col-md-6">Язык сертификата: <form:select path="language"						
						items="${languages}" id="language" onChange="javaScript:changeLanguage();"/></div>
						
</div>
					
<div class="row">
<div class="col-md-2"><a href="javascript:openClients('exporter');">Экспортер: </a></div>
<div class="col-md-10" id="exporter">
<c:if test="${fscert.language != 'RU'}">${fscert.exporter.getLocale(fscert.language).name} ${fscert.exporter.getLocale(fscert.language).address}
(${fscert.exporter.name}, ${fscert.exporter.address})
</c:if>
<c:if test="${fscert.language == 'RU'}">  ${fscert.exporter.name}, ${fscert.exporter.address}</c:if>
</div>
</div>

<div class="row">
<div class="col-md-2"><a href="javascript:openClients('producer')">Производитель:</a></div>
<div class="col-md-10" id="producer">
<c:if test="${fscert.language != 'RU'}">
${fscert.producer.getLocale(fscert.language).name}, ${fscert.producer.getLocale(fscert.language).address}
(${fscert.producer.name}, ${fscert.producer.address})
</c:if>
<c:if test="${fscert.language == 'RU'}">${fscert.producer.name} ${fscert.producer.address}</c:if>
</div>
</div>	

<div class="row">
<div class="col-md-2">Удостоверение:<p align="center"><a href="javascript:reloadConfirmation()" title="Сгенерировать из шаблона">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a></p></div>
<div class="col-md-10"><form:textarea rows="5" cols="110" path="confirmation" id="confirmation" /></div>
</div>
<div class="row">
<div class="col-md-2">Декларация:<p align="center"><a href="javascript:reloadDeclaration()" title="Сгенерировать из шаблона">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a></p></div>
<div class="col-md-10"><form:textarea rows="5" cols="110" path="declaration" id="declaration" /></div>
</div>

<div class="row">
<div class="col-md-12">Срок действия сертификата c: 
				<form:input path="dateissue" id="dateissue"
						class="datepicker" size="8" placeholder="с" /> по  
				<form:input	path="dateexpiry" id="dateexpiry" class="datepicker"
						size="8" placeholder="по" />
</div>
</div>

<!-- div class="row">
<div class="col-md-12">Количество листов сертификата <form:label path="listscount" id="listscount" class="doplist" 
						size="8" />  <a href="javascript:getListCount()" title="Посчитать количество страниц">
     <i class="glyphicon glyphicon-refresh" align="center"></i></a></div>
</div -->

<div class="row">
		  <div class="col-md-1"><a href="javascript:openEmployees('expert')">Эксперт:</a></div>
		  <div class="col-md-10" id="expert">
		  <c:if test="${fscert.language == 'RU'}">${fscert.expert.job} ${fscert.expert.name}</c:if>
		  <c:if test="${fscert.language != 'RU'}">${fscert.expert.getLocale(fscert.language).job} ${fscert.expert.getLocale(fscert.language).name}
		  (${fscert.expert.job} ${fscert.expert.name})
		  </c:if>
		  </div>
</div>
<div class="row">
		  <div class="col-md-1"><a href="javascript:openEmployees('signer')">Подпись:</a></div>
		  <div class="col-md-10" id="signer">
		  <c:if test="${fscert.language == 'RU'}">${fscert.signer.job} ${fscert.signer.name}</c:if>
		  <c:if test="${fscert.language != 'RU'}">${fscert.signer.getLocale(fscert.language).job} ${fscert.signer.getLocale(fscert.language).name}
		  (${fscert.signer.job} ${fscert.signer.name})
		  </c:if>		  
		  </div>
</div>				


<div class="row">
		        <div class="col-md-12">
		            <table id="products"></table>
		            <div id="pagerproducts"></div> 
				</div>
</div>

<div class="row">
		        <div class="col-md-12">
		            <table id="blanks"></table>
		            <div id="pagerblanks"></div> 
				</div>
</div>			


 <form:hidden path="expert.id" id="expertid" />
 <form:hidden path="signer.id" id="signerid" />
 
 </div>
</div>
</form:form>


<div id="fsview" name="fsview">
</div>

<div id="dialog-addproductlist" title="Добавление продуктов">
  <form id="addproductlist" method="POST">
  <fieldset>
       <label>Список продуктов:</label>
       <p></p>
       <p><textarea rows="8" cols="35" id="productlist" name="productlist"></textarea></p>
  </textarea>       
  </form>
</div>

<div id="dialog-addblanklist" title="Добавление номеров бланков">
  <form id="addblanklist" method="POST">
  <fieldset>
       <label>Список номеров бланков:</label>
       <p></p>
       <p><textarea rows="8" cols="35" id="blanklist" name="blanklist"></textarea></p>
  </textarea>       
  </form>
</div>

<div id="dialog-message">
  <p id="message" align="center"><span class="ui-icon ui-icon-alert" style="float:left; margin:6px 6px 10px 5px;">
  </span></p>
</div>

<div id="dialog-confirm">
  <p id="confirm" align="center"><span class="ui-icon ui-icon-alert" style="float:left; margin:6px 6px 10px 5px;">
  </span></p>
</div>


<div id="centerdiv" style="visibility: hidden; width: 100%;  position: fixed; height: 100%; top: 0; left: 0; overflow: auto; z-index=0;"/>


