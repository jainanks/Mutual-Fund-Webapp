<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
      
      <div class="row">
        <div class="col-lg-12">
          <h2 class="page-header"> Create New Customer Account </h2>
        </div>
      </div>
      <p> *Required field
      
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
		
      
      <form role="form" method="post">
        <div class="form-group">
          <label>User name:*</label>
          <input name="userName" required class="form-control" value="${form.userName}" placeholder="Not case sensitive">
        </div>
        <div class="form-group">
          <label>Customer's First Name:*</label>
          <input name="firstName" required class="form-control" value="${form.firstName}">
        </div>
        <div class="form-group">
          <label>Customer's Last Name:* </label>
          <input name="lastName" required class="form-control" value="${form.lastName}">
        </div>
        <div class="form-group">
          <label>Password:* </label>
          <input name="password" type="password" required class="form-control" value="${form.password}" placeholder="This is a case sensitive field.">
        </div>
        <div class="form-group">
          <label>Confirm password:* </label>
          <input name="confirmPassword" type="password" required value="${form.confirmPassword}" class="form-control">
        </div>
        <div class="form-group">
          <label>Address line #1: </label>
          <input class="form-control" name="address1" value="${form.address1}">
        </div>
        <div class="form-group">
          <label>Address line #2: </label>
          <input class="form-control" name="address2"  value="${form.address2}" paceholder="optional">
        </div>
        <div class="form-group">
          <label>City: </label>
          <input class="form-control" name="city" value="${form.city}">
        </div>
        <div class="form-group">
          <label>State: </label>
          <input name="state" class="form-control"  value="${form.state}" placeholder="Please write only the abbreviation letters. Ex. PA" maxlength="2">
        </div>
        <div class="form-group">
          <label>Zip code:</label>
          <input name="zipcode" class="form-control" value="${form.zipcode}" placeholder="Use format: 12345" maxlength="5">
        </div>
        <br>
        <button type="submit" name="action" value="create" class="btn btn-primary">Create Customer</button>
      </form>
      </p>
      <p><br>
      </p>
      
<jsp:include page="template-buttom.jsp" />