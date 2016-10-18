<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
                
                <div>
                 <h3> Hello, ${ user.firstName }  ${ user.lastName }!</h3>
                </div>
                <img src="bootstrap/img/banner.jpg" alt="CFS Welcome Banner" width="90%" border: 0;>
                
<jsp:include page="template-buttom.jsp" />