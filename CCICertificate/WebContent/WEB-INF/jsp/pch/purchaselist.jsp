<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<!--link href="${LoginCss}" rel="stylesheet"/-->
<script>
	$( document ).ready(function() {
	    $( "#pview" ).dialog({autoOpen: false});
	 });

	function goToList(link) {
		var url = link;
          if (document.getElementById("filter").checked) {
			url = url + "&filterfield="
				+ document.getElementById("filterfield").value;
			url = url + "&filteroperator="
				+ document.getElementById("filteroperator").value;
			url = url + "&filtervalue="
				+ document.getElementById("filtervalue").value;
                                    } 
		url = url + "&filter="
			+ document.getElementById("filter").checked;
		document.location.href = url ;
	}

	function swithFilter() {
                 if (document.getElementById("filter").checked) {
                           document.getElementById("filterbutton").disabled = false;  
                           //document.getElementById("filterbutton").style.display='show';
                  } else {
                        document.getElementById("filterbutton").disabled = true;  
                        goToList('purchaselist.do?page=1&pagesize=${pagesize}&orderby=${orderby}&order=${order}');                             
	    }	
    }

    function loadPurchase(link) {
           $("#pview").load(link);
           $("#pview" ).dialog( "option", "title", 'Сделка');
           $("#pview" ).dialog( "option", "width", 600);
           $("#pview" ).dialog( "option", "modal", true );
           $("#pview" ).dialog("open");
    }

                 
</script>

<div class="col-md-10 col-md-offset-2 main">
     <h3> Список сделок </h3>
     <table style="width:100%">
        <tr>
	    <td style="width:70%"><input id="filter" type="checkbox" onclick="javascript:swithFilter();">Фильтр:</input> 
                     <select id="filterfield" name="filterfield"> 
	            		<c:forEach items="${headers}" var="item">
	                      <option value="${item.dbfield}">${item.name}</option>
                		</c:forEach>  
                     </select> 
	           <select id="filteroperator"> 
	        		<option value=">">больше</option>
	        		<option value="<">меньше</option>
       	        	<option value="=">равно</option>
	        	   	<option value="like">включает</option>	        
       	       </select>
                           
               <input id="filtervalue" value="${filtervalue}"/>
                <button id="filterbutton" onclick="javascript:goToList('purchaselist.do?page=1&pagesize=${pagesize}&orderby=${orderby}&order=${order}');">применить</button>               
                </td>

               <script>
					$(document).ready(function() {
						document.getElementById("filterfield").value = "${filterfield}";
						document.getElementById("filteroperator").value = "${filteroperator}";
                        document.getElementById("filter").checked=${filter};
                        
						if (${filter})	{ 
       					                     document.getElementById("filterbutton").disabled=false; 
  						    //document.getElementById("filterbutton").style.display='show';
						} else {
          					                     document.getElementById("filterbutton").disabled=true; 
  						    //document.getElementById("filterbutton").style.display='none';
						}

					});
			   </script>
               
               
	        <td style="width:30%;text-align: right">Строк в списке: 
	           <c:forEach items="${sizes}" var="item">
	           	   &nbsp;	
	               <a href="javascript: goToList('purchaselist.do?page=1&pagesize=${item}&orderby=${orderby}&order=${order}');">${item}</a>
	           </c:forEach>
	        </td>
        </tr>
     </table>
        
     <table class="purchase" style="width:100%">     
     <tr>
        <c:forEach items="${headers}" var="item">
	        <td style="width:${item.width}%;background-color: gray; color: black"><a href="javascript: goToList('${item.link}');" style="color: white">${item.name}${item.selection}</a></td>
        </c:forEach>
     </tr>  
	 <c:forEach items="${purchases}" var="item">
	        <tr>
	        <td style="width:15%"><a href="javascript: loadPurchase('purchaseview.do?id=${item.id}&popup=true');">${item.pchDateString}</a></td>
	        <td style="width:15%">${item.product}</td>
	        <td style="width:20%">${item.company}</td>
	        <td style="width:10%">${item.price}</td>
	        <td style="width:10%">${item.volume}</td>
	        <td style="width:30%">${item.department}</td>
	        </tr>
     </c:forEach>
     </table>
     
     <table style="width:100%">
        <tr>
	        <td style="width:80%;text-align: left"><a href="javascript: goToList('${prev_page}');">◄</a>
	           <c:forEach items="${pages}" var="item">
	           	   &nbsp;	
	               <a href="javascript: goToList('purchaselist.do?page=${item}&pagesize=${pagesize}&orderby=${orderby}&order=${order}');" 
	               <c:if test="${item==page}">style="border-style: solid; border-width: 1px;"</c:if>>
	               ${item}
	               </a>
	           </c:forEach>
	        </td>   
	        <td style="width:20%;text-align: right"><a href="javascript: goToList('${next_page}');">►</a></td>
        </tr>
     </table>  

     <div id="pview" name="pview">   
               
     </div> 
    
</div>



  