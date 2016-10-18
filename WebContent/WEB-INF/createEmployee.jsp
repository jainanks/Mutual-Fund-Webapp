<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
      
      <div class="row">
        <div class="col-lg-12">
          <h2 class="page-header"> Create Employee Account </h2>
        </div>
      </div>
      <p> *Required Field
      
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
      
      <form role="form" method="POST">
        <div class="form-group">
          <label>User Name:*</label>
          <input name="userName" required class="form-control" value="${form.userName}">
        </div>
        <div class="form-group">
          <label>First Name:*</label>
          <input name="firstName" required class="form-control" value="${form.firstName}">
        </div>
        <div class="form-group">
          <label>Last Name:*</label>
          <input name="lastName" required class="form-control" value="${form.lastName}">
        </div>
        <div class="form-group">
          <label>Password:*</label>
          <input name="password" type="password" required class="form-control" value="${form.password}" placeholder="This field is case sensitive.">
        </div>
        <div class="form-group">
          <label>Confirm Password:*</label>
          <input name="confirmPassword" type="password" value="${form.confirmPassword}" required class="form-control" placeholder="Retype Password">
        </div>
        <input type="checkbox" required name="agree">
        I am authorized to create an employee account with full access privileges. <br>
        <br>
        <button type="submit" name="action" value="create" class="btn btn-primary">Create</button>
      </form>
      
<jsp:include page="template-buttom.jsp" />