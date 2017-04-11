<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(function () {
	
    console.log("Grid init...");
    
	jQuery("#listelements").jqGrid({
	    url: "clientgrid.do",
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
 		rowNum:20,
	   	rowList:[10,20,50],
	   	pager: '#pagerl',
	    sortname: "name",
        sortorder: "desc",
        viewrecords: true,
        rownumbers: true, 
        rownumWidth: 25, 
        gridview: true,
        autoencode: true,
        sortable: true,
	   	caption: "",
	   	ondblClickRow: function(rowid, selected) {
			if(rowid != null) {
				linkClient(rowid, "${clienttype}");
				//link="clientedit.do?id="+rowid;
				//$("#elementview").load(link);
  		    }					
		},
	    onSelectRow: function(rowid, selected) {
			if(rowid != null) {
				//link="clientview.do?id="+rowid;
				//$("#elemetview").load(link);
  		    }					
		}, 
		onSortCol : clearSelection,
		onPaging : clearSelection
	});
    $('#listelements').jqGrid('filterToolbar');
	$('#listelements').jqGrid('navGrid',"#pagerl", {                
        search: false, 
        add: false,
        edit: false,
        del: false,
        refresh: true,
        view: true
    });
 
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

</script>


<table id="listelements"></table>
<div id="pagerl"></div>
<p></p>
<table id="fileupload"></table>
<div id="pagerfile"></div>



