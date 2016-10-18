<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />
<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Research Funds</h2>
	</div>
</div>

<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<div class="container1">
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
	<form name="researchFundForm" id="researchFundForm" method="post"
		action="researchFund.do">
		<input type="hidden" name="fundId" id="fundId" />
		<h3>Select a Fund</h3>
		<table>
			<tr>
				<td><label>Select the fund you want to search:</label>
					<div class="form-group">
						<!-- Add the account stocks below -->
						<select class="form-control" name="fundName">
							<option></option>
							<c:choose>
								<c:when test="${ (empty funds) }"></c:when>
								<c:otherwise>
									<c:forEach var="u" items="${ funds }">
										<option>${ u.getName() }</option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select><br> <label>Or search it by name:</label> <input name="fund2"
							type="text" class="form-control"
							placeholder="Case sensitive field" value="${form.fund2}">
					</div>
			</tr>
			<tr>
				<td align="right"><input type="submit" name="action"
					class="btn btn-primary" value="Fund History" /></td>
			</tr>
		</table>
		<br>
		<script type="text/javascript">
			google.load("visualization", "1", {
				packages : [ "corechart" ]
			});
			google.setOnLoadCallback(drawChart);
			function drawChart() {
				document.getElementById("chart").style.visibility = 'hidden';
				var source = document.getElementById("chartData").value;
				var dataTable = source.split(",");
				if (source) {
					document.getElementById("chart").style.visibility = 'visible';
					var data = new google.visualization.DataTable();
					data.addColumn('string', 'Date');
					data.addColumn('number', 'Price $');
					data.addRows(parseInt(dataTable.length / 2));
					var num = 0;
					for (var i = 0; i < dataTable.length - 1; i = i + 2) {
						data.setCell(parseInt(num), 0, String(dataTable[i]));
						num = num + 1;
					}
					num = 0;
					for (var i = 1; i < dataTable.length - 1; i = i + 2) {
						data
								.setCell(parseInt(num), 1,
										parseFloat(dataTable[i]));
						num = num + 1;
					}

					var options = {
						title : ''
					};

					var chart = new google.visualization.LineChart(document
							.getElementById('chart_div'));
					chart.draw(data, options);
				}
			}

			function setValues() {
				var radios = document.getElementsByName("radio");
				for (var i = 0; i < radios.length; i++) {
					if (radios[i].checked) {
						document.researchFundForm.fundId.value = radios[i].id;
					}
				}
			}
		</script>
		<br>
		<div class="col-lg-6">
			<div id="chart">
				<b>Displaying results for:</b><br>
				<br>
				<table class="table table-bordered table-hover table-striped">
					<tr>
						<td witdh="30%"><b>Fund Name:</b></td>
						<td>${fundTitle}</td>
					</tr>
					<tr>
						<td><b>Ticker:</b></td>
						<td>${ticker}</td>
					</tr>
				</table>
				<b>Price fluctuation:</b><br>
			</div>
			<input type="hidden" name="chartData" id="chartData"
				value="${chartData}" />
			<div id="chart_div"></div>
			<br>

			<div class="col-lg-6" align="center">

				<table class="table table-bordered table-hover table-striped">
					<colgroup>
						<col span="1" style="width: 25%;">
						<col span="1" style="width: 25%;">

					</colgroup>

					<!-- Create for each loop to fill table -->
					<tbody>
						<c:choose>
							<c:when test="${ (empty fundPriceHistory) }"></c:when>
							<c:otherwise>
								<thead>
									<tr>
										<th>Date:</th>
										<th>Price:</th>
									</tr>
								</thead>
								<c:forEach var="u" items="${ fundPriceHistory }">
									<tr>
										<td>${ u.get("date") }</td>
										<td align="right">$${ u.get("price") }</td>
									</tr>
								</c:forEach>
							</c:otherwise>	
						</c:choose>
					</tbody>
				</table>
			</div>
	</form>
</div>


<jsp:include page="template-buttom.jsp" />