<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
$(function () {
	
    console.log("Grid init...");
    
	jQuery("#listmembers").jqGrid({
	    url: "fsgrid.do",
	    datatype: "xml",
	    mtype: "GET",
		height: 250,
		width : null,
		shrinkToFit : false,
	   	colNames:['Номер сертификата','Экспортер', 'Адрес экспортера','Призводитель', 'Адрес производителя','Дата сертификата'],
	   	colModel:[
	   		{name:'certnumber',index:'certnumber', width:200, sorttype:"int"},
	   	    {name:'exporter',index:'exportername', width:250 },
	   	    {name:'exporteraddress',index:'exporteraddress', width:160},
	   		{name:'producer',index:'producername', width:250},
	   		{name:'produceraddress',index:'produceraddress', width:160},
	   		{name:'datecert',
	   			index:'datecert', 
	   			width:200, 
	   			align:"center", 			
	   			sorttype:'date',
				formatter: 'date',
	            searchoptions: {
	                dataInit: function (element) {
	                    $(element).datepicker({
	                        id: 'datecert_datePicker',
	                        dateFormat: 'dd.mm.yy',
	                        showOn: 'focus',
	                        onSelect: function (dateText, inst) {
	                            setTimeout(function () {
	                                $('#listmembers')[0].triggerToolbar();
	                            }, 300);
	                        }
	                    });
	                }
	            }
			}		
	   	],
 		rowNum:20,
	   	rowList:[10,20,50],
	   	pager: '#pagerl',
	    sortname: "datecert",
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
				link="fsedit.do?certid="+rowid;
				$("#fscertview").load(link);
  		    }					
		},
	    onSelectRow: function(rowid, selected) {
			if(rowid != null) {
				link="fscert.do?certid="+rowid;
				$("#fscertview").load(link);
  		    }					
		}, 
		onSortCol : clearSelection,
		onPaging : clearSelection
	});
    $('#listmembers').jqGrid('filterToolbar');
	$('#listmembers').jqGrid('navGrid',"#pagerl", {                
        search: false, 
        add: false,
        edit: false,
        del: false,
        refresh: true,
        view: true
    });
 
});


function clearSelection() {
	$("#fscertview").text("");
}



</script>

<table id="listmembers"></table>
<div id="pagerl"></div>
<p></p>
<div id="fscertview" style="border: 1px; border-style: solid; border-radius: 5 px;" ></div>

<table id="fileupload"></table>
<div id="pagerfile"></div>



