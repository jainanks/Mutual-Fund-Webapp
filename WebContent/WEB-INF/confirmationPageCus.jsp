<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-12">
<c:choose>
	<c:when test="${ (empty msg) }">
	</c:when>
	<c:otherwise>
		<h3 class="page-header">${msg}</h3>
	</c:otherwise>
</c:choose>
	</div>
</div>
<h5>Click <a href="viewAccount.do">here </a>
to go back to your portfolio or use the navigation bar.</h5>
<br>
<img src="bootstrap/img/success.gif" alt="confirmationImage" />

<jsp:include page="template-buttom.jsp" />