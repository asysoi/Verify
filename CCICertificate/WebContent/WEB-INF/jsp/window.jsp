<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
  <jsp:include page="fragments/headTag.jsp" />
 
  <body>
    <jsp:include page="fragments/menu_invers.jsp" />
    <div class="container-fluid">
      <div class="row">
        <jsp:include page="fragments/sidebar.jsp" />
        <jsp:include page="${jspName}" />
      </div>
    </div>
  </body>
  
</html>
