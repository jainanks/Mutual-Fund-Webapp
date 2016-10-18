<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
      
      <div class="row">
        <div class="col-lg-12">
          <h2 class="page-header"> Deposit Check </h2>
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
      
      <form action="depositCheck.do" method="post">
        <div class="col-lg-6">
          <div class="form-group">
            <label>Choose customer UserName:</label>
            <select  class="form-control" name="userName1">
              <option></option>            
              <c:choose>
					<c:when test="${ (empty customerList) }"></c:when>
					<c:otherwise>
						<c:forEach var="u" items="${ customerList }">
							<option>${ u.getUserName() }</option>
						</c:forEach>
					</c:otherwise>
				</c:choose>
            </select>
            <label>Or search it here:</label>
            <input name="userName2" type="text"  class="form-control" value="${form.userName2}">
            </div>
            <div class="form-group">
              <label>Amount:</label>
              <input name="amount" type="text" required class="form-control" value="${form.amount}" placeholder="Only numbers in the format 1000.00">
            </div>
            <div class="form-group">
              <label>Confirm Amount:</label>
              <input name="confAmount" type="text" class="form-control" value="${form.confAmount}"  placeholder="Only numbers in the format 1000.00">
            </div>
            <div class="form-group">
              <button type="submit" name="action" value="deposit" class="btn btn-primary">Deposit</button>
            </div>
          </div>
        </form>

      
<jsp:include page="template-buttom.jsp" />