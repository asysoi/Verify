<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(function () {
	$("#clpview").dialog({
		autoOpen : false
	});
	$("#clview").dialog({
		autoOpen : false
	});
	
	$( "#dialog-message" ).dialog({
		autoOpen : false,
		height: "auto",
	    modal: true,
	    buttons: {
	      Ok: function() {
	        $( this ).dialog( "close" );
	      }
	    }
	});
	
    console.log("Clients Grid init...");
    
    jQuery("#listelements").jqGrid({
		url: "clientgrid.do",
		// editurl: "fsgoodsupdate.do",
	    datatype: "xml",
	    mtype: "GET",
		height: 250,
		width : null,
		shrinkToFit : false,
	   	colNames:['Наименование','Адрес', 'УНП','Телефон', 'Email'],
	   	colModel:[
	   		{name:'name',index:'name', width:200},
	   	    {name:'address',index:'address', width:250 },
	   	    {name:'unp',index:'unp', width:160},
	   		{name:'phone',index:'phone', width:250},
	   		{name:'email',index:'email', width:160}
  	   	    ],
 		rowNum:10,
	   	rowList:[5,10,20,50],
	   	pager: '#pagerl',
	    sortname: "name",
        sortorder: "desc",
        viewrecords: true,
        rownumbers: true, 
        //rownumWidth: 25, 
        gridview: true,
        autoencode: true,
        sortable: true,
	   	caption: "",
	   	ondblClickRow: function(rowid, selected) {
			if(rowid != null) {
				linkGridClient(rowid, "${clienttype}");
  		    }					
		} 
	});
	    
	$('#listelements').jqGrid('navGrid',"#pagerl", {                
        search: false, 
        add: false,
        edit: false,
        del: false,
        refresh: false,
        view: false
    }).navButtonAdd('#pagerl',{
	    caption: '',	
        buttonicon: 'ui-icon-plus',
        onClickButton: function() {
        	addGridClient();      	
            jQuery("#listelements").trigger('reloadGrid');
        },
        title: "Добавить нового контрагента",
        position: "last"
    }).navButtonAdd('#pagerl',{
	    caption: '',	
        buttonicon: 'ui-icon-pencil',
        onClickButton: function(event) {
	       	var grid = $("#listelements");
    	    var id = grid.jqGrid('getGridParam','selrow');
        	
        	if (id) { 
        		editGridClient(id);
        	} else { 
  		     	$( "#dialog-message" ).dialog("option", "title", 'Выбор контрагента');
 		     	$("#message").text("Выберете контрагента");
				$("#dialog-message").dialog("open");
            }
        },
        title: "Редактировать контрагента",
        position: "last"
    });
	
	grid = $("#listelements");
	grid.jqGrid('filterToolbar');
	grid.jqGrid('gridResize', {minWidth: 450, minHeight: 150});
	$("#pagerl_left", "#pagerl").width(250);
 
    });

	function clearSelection() {
		$("#elementview").text("");
	}

	function linkGridClient(clientid, clienttype) {
		url = "selclient.do?id=" + clientid+"&clienttype="+clienttype+"&lang="+$("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 console.log("Clienttype: " + clienttype);
			 if (clienttype == 'exporter') { 
		    	 $("#exporter").text(data);
		 	 } else if (clienttype == 'producer') {
				 $("#producer").text(data);
		 	 }
		});
  	    $("#fsview").dialog("close");
    }
	
	function saveGridClient() {
		url = $("#fclient").attr("action");
		$.ajaxSetup({async:false});
		$.post(url, $("#fclient").serialize());
		jQuery("#listelements").trigger('reloadGrid');
		$("#clview").dialog("close");
	}
	
	function updateGridClient() {
        var x;
		url = $("#fclient").attr("action");
		$.ajaxSetup({async:false});
		$.post(url, $("#fclient").serialize());
		jQuery("#listelements").trigger('reloadGrid');
		$("#clview").dialog("close");
	}
	
	function close() {
		$("#clpview").dialog("close");
	}
	
	
    function addGridClient() {
        link = "clientadd.do";
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Новый Контрагент');
		$("#clview").dialog("option", "width", 740);
		$("#clview").dialog("option", "height", 560);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { saveGridClient(); } },  
		     				             { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });
		$("#clview").dialog("option", "position", {
			my : "center", at : "center", of :  $( "#listelements" ) });

		$("#clview").dialog("open");
	}
    
    function editGridClient(id) {
        link = "clientedit.do?id=" + id;
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Контрагент');
		$("#clview").dialog("option", "width", 740);
		$("#clview").dialog("option", "height", 560);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { updateGridClient(); } },  
		     				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });
		$("#clview").dialog("option", "position", {
			my : "center", at : "center", of :  $( "#listelements" ) });
		$("#clview").dialog("open");
	}
</script>


<table id="listelements"></table>
<div id="pagerl"></div>
<p></p>

<table id="fileupload"></table>
<div id="pagerfile"></div>

<div id="clpview" name="clpview">
</div>

<div id="clview" name="clview">
</div>

<div id="dialog-message">
  <p id="message" align="center"><span style="float:left; margin:6px 6px 10px 5px;">
  </span></p>
</div>

<div id="dialog-confirm">
  <p id="confirm" align="center"><span style="float:left; margin:6px 6px 10px 5px;">
  </span></p>
</div>

<div id="centerdiv" style="visibility: hidden; width: 100%;  position: fixed; height: 100%; top: 0; left: 0; overflow: auto; z-index=0;"/>


