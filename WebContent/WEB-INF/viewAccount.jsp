<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">${customer.getFirstName()}  ${customer.getLastName()}</h2>
	</div>
</div>

<div class="col-lg-6">

<div class="table-responsive">
		<table class="table table-hover table-striped">
			<thead>
				<tr>
					<td><b>Account User Name:</b></td>
					<td>${customer.getUserName()}</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><b>Registered Address:</b></td>
					<td>${customer.getAddress1()} ${customer.getAddress2()}</td>
				</tr>
				<tr>
					<td><b>City, State and Zip:</b></td>
					<td>${customer.getCity()}, ${customer.getState()}. ${customer.getZipcode()}</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<h4>Financial Information</h4>
	
	<div class="table-responsive">
		<table class="table table-hover table-striped">
			<thead>
				<tr>
					<td><b>Last Transaction Day:</b> (mm/dd/yyy)</td>
					<td align="right">${lastTradingDay}</td>
					<td width="30%">&nbsp;</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><b>Last Posted Balance:* </b></td>
					<td align="right">$${cash}</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><b>Available Cash:**</b></td>
					<td align="right">$${balance}</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
	<h5>* &nbsp; Accountable balance at the beginning of the day.<br>
	** Available cash considering checks requested and funds bough still pending.</h5>
<br>

<h4>Your Funds</h4>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th>Fund</th>
			<th>Ticker</th>
			<th>Shares</th>
			<th>Total Value</th>

		</tr>
	</thead>
	<tbody>
	             	<c:forEach var="fund" items="${fundList}">
						 <tr>
                    		<td>${ fund.getFundName() }</td>
                    		<td> ${ fund.getTicker() } </td>
                    		<td align="right"> ${ fund.getShares() } </td>
                    		<td align="right"> $${ fund.getTotal() } </td>
                  			</tr>
					</c:forEach>
	</tbody>
</table>
<br>
</div>

<jsp:include page="template-buttom.jsp" />