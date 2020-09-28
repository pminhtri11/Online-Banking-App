<%@page import="com.green.bank.database.DatabaseOperations"%>
<%@page import="com.green.bank.database.JDBC_Connect"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.green.bank.model.AccountModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%!AccountModel ac;%>

<%
	ac = (AccountModel) session.getAttribute("userDetails");
	String account_no = ac.getAccountNo();
	DatabaseOperations operations = new DatabaseOperations();
	ac = operations.getAccountDetails(account_no);
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=ac.getFName() + " " + ac.getLName()%></title>
<link rel="shortcut icon" type="image/png" href="image/favicon.png" />
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/profile.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="row">
		<jsp:include page="header.jsp" />
	</div>

	<div class="container-fullwidth">
		<div class="jumbotron col-md-6 col-md-offset-3"
			style="margin-top: 50px">
			<div class="row">
				<div class="profile-head col-md-10 col-md-offset-1">
					<div class="col-md-4 ">
						<img class="img-circle img-responsive" alt="" src="image/user.png">
					</div>


					<div class="col-md-6 ">
						<h2><%=ac.getFName() + " " + ac.getLName()%></h2>
						<ul>
							<li class="navli"><span
								class="glyphicon glyphicon-map-marker"></span> <%=ac.getBranch()%></li>
							<li class="navli"><span class="glyphicon glyphicon-home"></span>
								<%=ac.getAddress()%></li>
							<li class="navli"><span class="glyphicon glyphicon-phone"></span><%=ac.getPNumber()%></li>
							<li class="navli"><span class="glyphicon glyphicon-envelope"></span><%=ac.getEmail()%></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="account_details col-md-10 col-md-offset-1"
					style="margin-top: 30px;">
					<h2>Account Details</h2>
					<hr class="divider">
					<table class="table table-user-information col-md-6">
						<tbody>
							<tr>
								<td><b>First Name:</b></td>
								<td><%=ac.getFName()%></td>
							</tr>
							<tr>
								<td><b>Last Name:</b></td>
								<td><%=ac.getLName()%></td>
							</tr>
							<tr>
								<td><b>Account Number:</b></td>
								<td><%=ac.getAccountNo()%></td>
							</tr>
							<tr>
								<td><b>City</b></td>
								<td><%=ac.getCity()%></td>
							</tr>
							<tr>
								<td><b>Branch Name</b></td>
								<td><%=ac.getBranch()%></td>
							</tr>
							<tr>
								<td><b>Zip</b></td>
								<td><%=ac.getZip()%></td>
							</tr>
							<tr>
								<td><b>UserName</b></td>
								<td><%=ac.getUsername()%></td>
							</tr>
							<tr>
								<td><b>Phone Number</b></td>
								<td><%=ac.getPNumber()%></td>
							</tr>
							<tr>
								<td><b>Email</b></td>
								<td><a href="mailto:" +<%=ac.getEmail()%>><%=ac.getEmail()%></a></td>
							</tr>
							<tr>
								<td><b>Account Type</b></td>
								<td><%=ac.getAccountType()%></td>
							</tr>
							<tr>
								<td><b>Registration Date</b></td>
								<td><%=ac.getRegisterDate()%></td>
							</tr>
							<tr>
								<td><b>Amount</b></td>
								<td><%=ac.getAmount()%>&#2547;</td>
							</tr>
						</tbody>
					</table>
					<%
						
						String Passport = (String) session.getAttribute("Passport");
						String Address = (String) session.getAttribute("Address");
						String SSN = (String) session.getAttribute("SSN");
						String Check = (String) session.getAttribute("Check");
						
						System.out.println("File name: " + Passport);
						System.out.println("File name: " + Address);
						System.out.println("File name: " + SSN);
						System.out.println("File name: " + Check);
						if (!(Passport == null) || !(Address == null) || !(SSN == null) || !(Check == null)){							

					%>
					<table class="table table-user-information col-md-12">
						<tbody>
							<tr>
								<td><b>Please wait while we review your files.</b></td>
							</tr>

							<%
								if (Passport != null){
							%>
							<tr>
								<td>
									<div class="row col-md-12">
										<form action="manageFile" method="get">
											Passport File <input type="hidden" name="fileName" value="<%=Passport%>" />
											<input type="submit" value="Download File" />
										</form>
									</div>
								</td>
							</tr>
							<%
								}
							%>
							<%
								if (Address != null){
							%>
							<tr>
								<td>
									<div class="row col-md-12">
										<form action="manageFile" method="get">
											Address Verification File <input type="hidden" name="fileName" value="<%=Address%>" />
											<input type="submit" value="Download File" />
										</form>
									</div>
								</td>
							</tr>
							<%
								}
							%>
							<%
								if (SSN != null){
							%>
							<tr>
								<td>
									<div class="row col-md-12">
										<form action="manageFile" method="get">
											Social Security Number File<input type="hidden" name="fileName" value="<%=SSN%>" />
											<input type="submit" value="Download File" />
										</form>
									</div>
								</td>
							</tr>
							<%
								}
							%>
							<%
								if (Check != null){
							%>
							<tr>
								<td>
									<div class="row col-md-12">
										<form action="manageFile" method="get">
											Check Information <input type="hidden" name="fileName" value="<%=Check%>" />
											<input type="submit" value="Download File" />
										</form>
									</div>
								</td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>

					<%
						}
					%>
					<table class="table table-user-information col-md-12">
						<tbody>
							<tr>
								<td>Please upload the following Files. Otherwise your
									account will be close within 3 days</td>
							</tr>
							<tr>
								<td><jsp:include page="FileUpload.jsp" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="row"></div>

		<!-- Footer start here -->
		<div class="row" style="margin-top: 50px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>