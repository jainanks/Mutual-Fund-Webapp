<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Sell Funds</h2>
	</div>
</div>

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


	<div class="col-lg-6">

		<div class="form-group">

			<label>Select the fund you want to sell:</label>

			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>Fund</th>
						<th>Ticker</th>
						<th>Shares held</th>
						<th>Shares to sell</th>
						<th>Operation</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="fund" items="${fundList}">
						
						<form action="sellFund.do" method="post">
							<tr>
								<td>${ fund.getFundName() }</td>
								<td>${ fund.getTicker() }</td>
								<td>${ fund.getShares() }</td>
								<td>
									 <input name="shares"  value="0"  class="form-control" >
									 <input style="width=30px" name="fundId"  type="hidden" value="${ fund.getFundId() }"  class="form-control" >
								</td>
								<td>
									<button type="submit" name="action" value="sell" 	class="btn btn-primary">Sell</button>
								 </td>
								</tr>
							</form>
						
					</c:forEach>
				</tbody>
			</table>
		</div>









<jsp:include page="template-buttom.jsp" />