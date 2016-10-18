<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Task 7">
    <meta name="author" content="Team HEX">

    <title>CFS Mutual Funds Website | TEAM HEX </title>

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/sb-admin.css" rel="stylesheet">
    <link href="bootstrap/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="navbar-header">
                <a href="login.do">
                <img src="bootstrap/img/logo.png" alt="CFS Logo" border: 0; position: relative; top: 5px; left: 10px;>
                </a>
            </div>
            <!-- Top Menu Items -->
            
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                
            </div>
        </nav>

<!-- End of Navigation Bar -->
        <div id="page-wrapper">
        <div class="container-fluid">
<!-- End of Top-Employee - Dont Edit Anything Above -->


<h2>Error Page</h2>

<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
</c:forEach>

<p>
	<c:choose>
		<c:when test="${ (empty user) }">
				Click <a href="login.do">here</a> to login.
			</c:when>
	</c:choose>
</p>


                <!-- Bottom Starts Here -->          
         <br><br>   
         </div>    
      </div>
    </div>
   <div class="col-lg-12">
   <p align="center"> <font color="white"> <a href="#">About CFS Mutual Funds Investors</a> | <a href="#">Conctact Us</a> | <a href="#">Technical Support</a> | <a href="#">Legal Disclosure</a> <br>Team HEX | Non-comercial educational website <br> © All rights reserved 2015 CMU </font></p>
   </div>
    <!-- jQuery -->
<script src="bootstrap/js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
<script src="bootstrap/js/bootstrap.min.js"></script>    
</body>
</html>
