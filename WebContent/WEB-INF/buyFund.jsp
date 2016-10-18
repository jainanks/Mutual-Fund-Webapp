<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Buy Funds</h2>
	</div>
</div>
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

<form method="POST">
	<label>Available Balance: ${ balance }</label>
	<br/>
	<label>Select the fund you want to buy:</label>
	<div class="form-group">
		<!-- Add the account stocks below -->
		<select class="form-control" name="fund">
			<option></option>

			<c:choose>
				<c:when test="${ (empty fundList) }"></c:when>
				<c:otherwise>
					<c:forEach var="u" items="${ fundList }">
						<option>${ u.getName() }</option>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</select>
		<br>
		<label>Or search by fund name here:</label> <input name="fund2" type="text"
			class="form-control" value="${form.fund2}">
	</div>
	<label>Amount you want to buy:</label>
	<div class="form-group input-group">
		<span class="input-group-addon">$</span>
		<!-- Add the account amount below -->
		<input type="text" class="form-control" name="amount"
			placeholder="Enter amount above $10.00">
	</div>
	<!-- 		<label>Confirm Amount:</label> -->
	<!-- 		<div class="form-group input-group"> -->
	<!-- 			<span class="input-group-addon">$</span> -->
	<!-- 			<!-- Add the account amount below -->
	<!-- 			<input type="text" class="form-control" name="camount" -->
	<!-- 				placeholder="Type only numbers in format 1000.00"> -->
	<!-- 		</div> -->
	<br>
	<button type="submit" name="action" value="buy" class="btn btn-primary">Buy Fund</button>
</form>

<jsp:include page="template-buttom.jsp" />