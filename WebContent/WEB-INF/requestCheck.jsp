<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Request Check</h2>
	</div>
</div>
Please notice that your available cash includes all funds purchased and
checks previously requested today.
<br>
It does not include recent funds sold that will post by the end of the
transaction day.
<br>

<div class="col-lg-6">

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
	<br>
	<form action="requestCheck.do" method="post">
		<div class="form-group">
			<table width="70%">
				<tr>
					<td><b>Account Username:</b></td>
					<td>${ user.userName }</td>
				</tr>
				<tbody>
					<tr>
						<td><b>Check Payable To:</b></td>
						<td>${ user.firstName } ${ user.lastName }</td>
					</tr>
					<tr>
						<td><b>Available Cash:</b></td>
						<td>$${ balance }</td>
					</tr>
				</tbody>
			</table>
			<br>
			<label>Amount Requested:</label>
			<div class="form-group input-group">
				<span class="input-group-addon">$</span> <input name="amount"
					type="text" required class="form-control"
					placeholder="Enter amount equal or lesser than available cash.">
			</div>
			<label>Confirm Amount:</label>
			<div class="form-group input-group">
				<span class="input-group-addon">$</span> <input name="confAmount"
					type="text" required class="form-control"
					placeholder="Enter amount equal or lesser than available cash.">
			</div>
			<br>
			<div class="form-group">
				<button type="submit" name="action" value="request"
					class="btn btn-primary">Request Check</button>
			</div>
		</div>
	</form>
</div>

<jsp:include page="template-buttom.jsp" />