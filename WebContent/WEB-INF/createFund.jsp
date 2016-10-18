<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
                
                <div class="row">
                    <div class="col-lg-12">
                        <h2 class="page-header">
                            Create Fund
                        </h2>
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

                <form role="form" method="POST">
                	<div class="form-group">
                    	<label>Fund Name:</label>
                        <input name="fundName" required class="form-control" placeholder="Max. 20 characters" maxlength="20">
                    </div>
                    
                    <div class="form-group">
                    	<label>Ticker:</label>
                        <input name="ticker" required class="form-control" placeholder="Capital letters ex. GOOG" maxlength="6">
                    </div>
                    
                    
                     <button type="submit" name="action" value="create" class="btn btn-primary">Create Fund</button>
               </form>

               
<jsp:include page="template-buttom.jsp" />