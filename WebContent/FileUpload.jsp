<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FileUpload</title>
<link rel="shortcut icon" type="image/png" href="image/favicon.png" />
<link rel="stylesheet" type="text/css" href="css/deposit.css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<form action="manageFile" method="post" enctype="multipart/form-data">
		<div class="row">
			<div class="col-sm-6 form-group">
				<div class="input-group-btn">
					<select class="form-control btn btn-default" name="fileType"
						required>
						<option>Passport</option>
						<option>Address</option>
						<option>SSN</option>
						<option>Check</option>
					</select>
				</div>
			</div>
			<div class="col-sm-6 form-group">
				<input type="file" class="form-control" name="file1"> 
			</div>
		</div>
		<input type="submit" value="Upload">
	</form>
</body>
</html>
