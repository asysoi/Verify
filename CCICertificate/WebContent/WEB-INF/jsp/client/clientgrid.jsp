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
		//height: '20%',
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
				//linkClient(rowid, "${clienttype}");
				//link="clientedit.do?id="+rowid;
				//$("#elementview").load(link);
  		    }					
		},
	    onSelectRow: function(rowid, selected) {
			if(rowid != null) {
				//link="clientview.do?id="+rowid;
				//$("#elemetview").load(link);
  		    }					
		} 
	});
	
    $('#listelements').jqGrid('filterToolbar');
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
        	addClient();      	
            jQuery("#listelements").trigger('reloadGrid');
        },
        title: "Добавить нового контрагента",
        position: "next"
    }).navButtonAdd('#pagerl',{
	    caption: '',	
        buttonicon: 'ui-icon-edit',
        onClickButton: function(id) {
        	editClient(id);      	
            jQuery("#listelements").trigger('reloadGrid');
        },
        title: "Редактировать контрагента",
        position: "next"
    }).navButtonAdd('#pagerl',{
	    caption: '',	
        buttonicon: 'ui-icon-paperclip',
        onClickButton: function(id) {
        	if (id) { 
        		linkClient(id, "${clienttype}");
        	} else { 
  		     		$( "#dialog-message" ).dialog("option", "title", 'Выбор контрагента');
 		     		$("#message").text("Контрагент не выбран.");
					$("#dialog-message").dialog("open");
            }
            jQuery("#listelements").trigger('reloadGrid');
        },
        title: "Редактировать контрагента",
        position: "next"
    });
	
	grid = $("#listelements");
	grid.jqGrid('gridResize', {minWidth: 450, minHeight: 150});
	$("#pagerproducts_left", "#pagerl").width(270);
 
    });

	function clearSelection() {
		$("#elementview").text("");
	}

	function linkClient(clientid, clienttype) {
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
	
	function saveClient() {
		url = $("#fclient").attr("action");
		$.ajaxSetup({async:false});
		$.post(url, $("#fclient").serialize());
		$("#clview").dialog("close");
	}
	
	function updateClient() {
        var x;
		if (confirm("Сохранить сделанные изменения?") == true) {
			url = $("#fclient").attr("action");
			$.ajaxSetup({async:false});
			$.post(url, $("#fclient").serialize());
			$("#clview").dialog("close");
		} 
	}
	
	function close() {
		$("#clpview").dialog("close");
	}
	
	
    function addClient() {
        link = "clientadd.do";
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Компания');
		$("#clview").dialog("option", "width", 820);
		$("#clview").dialog("option", "height", 370);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { saveClient(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });

		$("#clview").dialog("option", "position", {
			my : "center top", at : "center", of :  window });

		$("#clview").dialog("open");
	}
    
    function editClient(id) {
        link = "clientedit.do?id=" + id;
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Компания');
		$("#clview").dialog("option", "width", 820);
		$("#clview").dialog("option", "height", 370);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { updateClient(); } },  
		    				               { text: "Очистить Все ", click: function() { clear(); } },
		     				               { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });
		$("#clview").dialog("option", "position", {
			my : "center top", at : "center", of :  listwindow });

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


