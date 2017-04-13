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
	
    console.log("Employees Grid init...");
    
    jQuery("#listelements").jqGrid({
		url: "empgrid.do",
		// editurl: "fsgoodsupdate.do",
	    datatype: "xml",
	    mtype: "GET",
		height: 250,
		width : null,
		shrinkToFit : false,
	   	colNames:['ФИО','Должность', 'Отделение','Подразделение'],
	   	colModel:[
	   		{name:'name',index:'name', width:200},
	   	    {name:'job',index:'job', width:300 },
	   	    {name:'otd_name',index:'otd_name', width:200},
	   		{name:'departmentname',index:'departmentname', width:350}
  	   	    ],
 		rowNum:10,
	   	rowList:[5,10,20,50],
	   	pager: '#pagerl',
	    sortname: "name",
        sortorder: "desc",
        viewrecords: true,
        rownumbers: true, 
        gridview: true,
        autoencode: true,
        sortable: true,
	   	caption: "",
	   	ondblClickRow: function(rowid, selected) {
			if(rowid != null) {
				linkGridEmployee(rowid, "${employeetype}");
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
        	addGridEmployee();      	
            jQuery("#listelements").trigger('reloadGrid');
        },
        title: "Добавить нового сотрудника",
        position: "last"
    }).navButtonAdd('#pagerl',{
	    caption: '',	
        buttonicon: 'ui-icon-pencil',
        onClickButton: function(event) {
	       	var grid = $("#listelements");
    	    var id = grid.jqGrid('getGridParam','selrow');
        	
        	if (id) { 
        		editGridEmployee(id);
        	} else { 
  		     	$( "#dialog-message" ).dialog("option", "title", 'Редактирование сотрудника');
 		     	$("#message").text("Выберите сотрудника");
				$("#dialog-message").dialog("open");
            }
        },
        title: "Редактировать сотрудника",
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

	function linkGridEmployee(id, type) {
		url = "selemployee.do?id=" + id+"&employeetype="+type+"&lang="+$("#language").val();
		$.ajaxSetup({async:false});
		$.get(url, function(data, status) {
			 var obj = JSON.parse(data); 
			 if (type == 'expert') { 
		    	 $("#expert").text(obj.expert);
		 	 } else if (type == 'signer') {
				 $("#signer").text(obj.signer);
		 	 }
		});
  	    $("#fsview").dialog("close");
    }
	
	function saveGridEmployee() {
		url = $("#fcemployee").attr("action");
		$.ajaxSetup({async:false});
		$.post(url, $("#fcemployee").serialize());
		jQuery("#listelements").trigger('reloadGrid');
		$("#clview").dialog("close");
	}
	
	function updateGridEmployee() {
        var x;
		if (confirm("Сохранить сделанные изменения?") == true) {
			url = $("#fclient").attr("action");
			$.ajaxSetup({async:false});
			$.post(url, $("#fclient").serialize());
			jQuery("#listelements").trigger('reloadGrid');
			$("#clview").dialog("close");
		} 
	}
	
	function close() {
		$("#clpview").dialog("close");
	}
	
	
    function addGridEmployee() {
        link = "employeeadd.do";
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Сотрудник');
		$("#clview").dialog("option", "width", 740);
		$("#clview").dialog("option", "height", 300);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { saveGridEmployee(); } },  
		     				             { text: "Отмена", click: function() { $( this ).dialog( "close" ); } }
		                      	                                               ] });
		$("#clview").dialog("option", "position", {
			my : "center", at : "center", of :  $( "#listelements" ) });

		$("#clview").dialog("open");
	}
    
    function editGridEmployee(id) {
        link = "employeeedit.do?id=" + id;
		$("#clview").load(link);        
		$("#clview").dialog("option", "title", 'Сотрудник');
		$("#clview").dialog("option", "width", 740);
		$("#clview").dialog("option", "height", 300);
		$("#clview").dialog("option", "modal", true);
		$("#clview").dialog("option", "resizable", false);
		$("#clview").dialog({ buttons: [ { text: "Сохранить",  click : function() { updateGridEmployee(); } },  
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


