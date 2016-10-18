<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />


      <div class="form-group">
        <h2> Showing Details For: <font color="orange">GOOG</font></h2>
        <br>
        <div class="col-lg-9">
        <div class="panel panel-red">
          <div class="panel-heading">
            <h3 class="panel-title">Information Chart</h3>
          </div>
          <div class="panel-body"> </div>
        </div>
        <form action="buyFund.do" method="POST">
        <button type="submit" class="btn btn-primary">Buy This Fund</button>
        </form><br>
        <a href="researchFund.do">Go Back To Search</button>
      </div>
        </div>
      </div>
      
<jsp:include page="template-buttom.jsp" />