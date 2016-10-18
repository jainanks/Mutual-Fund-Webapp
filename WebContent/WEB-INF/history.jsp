<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-9">
		<h2 class="page-header">Transaction History</h2>
	</div>
</div>

	<c:choose>
		<c:when test="${ (empty msg) }">
		</c:when>
		<c:otherwise>
			<h3 style="color: blue">${msg}</h3>
		</c:otherwise>
	</c:choose>

<div class="table-responsive">
	<table class="table table-bordered table-hover table-striped">
		<thead>
			<tr>
				<th>Transaction Date (mm/dd/yyyy)</th>
				<th>Operation</th>
				<th>Fund Name</th>
				<th>Shares</th>
				<th>Share Price</th>
				<th>Total Amount</th>

			</tr>
		</thead>
		<!-- Create for each loop to fill table -->
		<tbody>
			<c:choose>
				<c:when test="${ (empty transactionList) }"></c:when>
				<c:otherwise>
					<c:forEach var="u" items="${ transactionList }">
						<tr>
							<td>${ u.date }</td>
							<td>${ u.operation }</td>
							<td>${ u.fund }</td>
							<td align="right">${ u.totShares }</td>
							<td align="right">${ u.price }</td>
							<td align="right">${ u.total }</td>
							</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	
	* Pending deposits are available for you to use, but will be reflected on your posted balance at the end of the transaction day.
	<br><br>
	<p align="center">*** End of Registry ***</p>
</div>

<jsp:include page="template-buttom.jsp" />