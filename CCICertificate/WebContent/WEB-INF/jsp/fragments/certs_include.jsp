<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="resources/css/login.css" var="LoginCss"/>
<link href="${LoginCss}" rel="stylesheet"/>

<script>
	$( document ).ready(function() {
	    $( "#pview" ).dialog({autoOpen: false});
	    document.getElementById("filterfield").value = "${vmanager.filterfield}";
		document.getElementById("filteroperator").value = "${vmanager.filteroperator}";
        document.getElementById("filter").checked=${vmanager.onfilter};
        
		if ( "${vmanager.onfilter}" )	{ 
		         document.getElementById("filterbutton").disabled=false; 
		} else {
		         document.getElementById("filterbutton").disabled=true; 
		}
	    
	 });

	function goToList(link) {
		var url = link;
          if (document.getElementById("filter").checked) {
			//url = url + "&filterfield="
			//	+ document.getElementById("filterfield").value;
			//url = url + "&filteroperator="
			//	+ document.getElementById("filteroperator").value;
			//url = url + "&filtervalue="
			//	+ document.getElementById("filtervalue").value;
			//		url = url + "&filter="
			//+ document.getElementById("filter").checked;

        	 url = url + "&fullsearchvalue="
				+ document.getElementById("fullsearchvalue").value; 
		document.location.href = url ;
	}

	function swithFilter() {
                 if (document.getElementById("filter").checked) {
                           document.getElementById("filterbutton").disabled = false;  
                  } else {
                        document.getElementById("filterbutton").disabled = true;  
                        goToList('certs.do?page=1&pagesize=${pagesize}&orderby=${orderby}&order=${order}');                             
	    }	
    }

    function loadCertificate(link) {
           $("#pview").load(link);
           $("#pview" ).dialog( "option", "title", 'Сертификат');
           $("#pview" ).dialog( "option", "width", 600);
           $("#pview" ).dialog( "option", "modal", true );
           $("#pview" ).dialog("open");
    }
                 
</script>

<div class="col-md-10 col-md-offset-2 main">
     <h3> Список сертификатов </h3>
     <table style="width:100%">
        <tr>
            <!-- 
	        <td style="width:70%"><input id="filter" type="checkbox" onclick="javascript:swithFilter();">Фильтр:</input> 
                     <select id="filterfield" name="filterfield"> 
	            		<c:forEach items="${vmanager.headers}" var="item">
	                      <option value="${item.dbfield}">${item.name}</option>
                		</c:forEach>  
                     </select> 
	           <select id="filteroperator"> 
	        		<option value=">">больше</option>
	        		<option value="<">меньше</option>
       	        	<option value="=">равно</option>
	        	   	<option value="like">включает</option>	        
       	       </select>
                           
               <input id="filtervalue" value="${vmanager.filtervalue}"/>
                <button id="filterbutton" onclick="javascript:goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');">применить</button>               
               </td>
             -->
               
 	       <td style="width:20%">
 	           <input id="filter" type="checkbox" onclick="javascript:swithFilter();">Фильтр</input>
 	       </td>
 	        
 	       <td style="width:50%">    
               <input id="fullsearchvalue" value="${vmanager.fullsearchvalue}"/>
               <button id="filterbutton" onclick="javascript:goToList('certs.do?page=1&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');">
               <img srcsrc="resources/images/red_searc.png" alt="Искать"></button>               
           </td>
                
           <td style="width:30%;text-align: right">Строк в списке: 
	           <c:forEach items="${sizes}" var="item">
	           	   &nbsp;	
	               <a href="javascript: goToList('certs.do?page=1&pagesize=${item}&orderby=${vmanager.orderby}&order=${vmanager.order}');">${item}</a>
	           </c:forEach>
	        </td>
	        
	        
        </tr>
     </table>
        
     <table class="certificate" style="width:100%">     
     <tr>
        <c:forEach items="${vmanager.headers}" var="item">
	        <td style="width:${item.width}%;background-color: gray; color: black"><a href="javascript: goToList('${item.link}');" style="color: white">${item.name}${item.selection}</a></td>
        </c:forEach>
     </tr>
       
	 <c:forEach items="${certs}" var="cert">
	        <tr>
	        <td><a href="gocert.do?certid=${cert.cert_id}">${cert.nomercert}</a></td>
	        <td>${cert.otd_name}</td>
	        <td>${cert.kontrp}</td>
	        <td>${cert.nblanka}</td>
	        <td>${cert.datacert}</td>
        
	        <!-- 
	        <td>
	        <c:if test="${cert.child_id != null}">
	            <a href="gocert.do?certid=${cert.child_id}">child</a>
	        </c:if>    
	        </td>  
	        -->
	        
	        <td>
	        <c:if test="${cert.parentnumber != null}">
	        <a href="gocert.do?certid=${cert.parentnumber}">parent</a>
	        </c:if>
	        </td>
	        </tr>
     </c:forEach>
     </table>
     
     <table style="width:100%">
        <tr>
	        <td style="width:80%;text-align: left">
	           <a href="javascript: goToList('${first_page}');"><img src="resources/images/first_page_24.png" alt="Перв."> </a>
	           <a href="javascript: goToList('${prev_page}');"><img src="resources/images/prev_page_24.png" alt="Пред."></a>
	            
	        
	           <c:forEach items="${pages}" var="item">
	           	   &nbsp;	
	               <a href="javascript: goToList('certs.do?page=${item}&pagesize=${vmanager.pagesize}&orderby=${vmanager.orderby}&order=${vmanager.order}');" 
	               <c:if test="${item==vmanager.page}">style="border-style: solid; border-width: 1px;"</c:if>>
	                    ${item}
	               </a>
	           </c:forEach>
                                    &nbsp; (количество сертификатов - ${vmanager.pagecount})
	        </td>   
	        <td style="width:20%;text-align: right">
	              <a href="javascript: goToList('${next_page}');"><img src="resources/images/next_page_24.png" alt="След."></a>
	              <a href="javascript: goToList('${last_page}');"><img src="resources/images/last_page_24.png" alt="Посл."></a>
	        </td>
        </tr>
     </table>  

     <div id="pview" name="pview">   
               
     </div> 
    
</div>



