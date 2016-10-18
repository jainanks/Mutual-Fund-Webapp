<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />

<c:choose>
	<c:when test="${ (empty msg) }">
	</c:when>
	<c:otherwise>
		<h3 style="color: blue">${msg}</h3>
	</c:otherwise>
</c:choose>

<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
</c:forEach>
<form role="form" action="reset.do" method="POST">
	<div class="form-group">
		<h3>
			Reset password for: <font color="blue">${customer} </font>
		</h3>
		<br> <label>New Password</label> <input class="form-control"
			name="newPwd" type="password" placeholder="Case sensitive field">
	</div>

	<div class="form-group">
		<label>Confirm Password</label> <input class="form-control"
			name="confPwd" type="password" placeholder="Case sensitive field">
			<input type="hidden" name="customer" value="${customer}">
	</div>
	<button type="submit" name="action2" value="reset"
		class="btn btn-default">Set New Password</button>
</form>
<p>
	<br>
</p>

<jsp:include page="template-buttom.jsp" />