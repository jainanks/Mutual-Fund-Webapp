<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
             
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Change password
                        </h1>
                   </div>
                </div>
                <p>
                
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
                    	<label>Current password</label>
                        <input required class="form-control" name="currPwd" value=" ${ form.currPwd } ">
                    </div>
                    
                    <div class="form-group">
                    	<label>New password </label>
                        <input required class="form-control" name="newPwd" value=" ${ form.newPwd } ">
                    </div>
                    
                    <div class="form-group">
                    	<label>Confirm new password: </label>
                        <input required class="form-control" name="confPwd" value=" ${ form.confPwd } ">
                    </div>

                    <button type="submit" name="action" value="change" class="btn btn-default">Submit</button>

               </form>

                </p>
                <p><br>
                </p>
                
<jsp:include page="template-buttom.jsp" />