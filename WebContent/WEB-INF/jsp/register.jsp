<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<script type="text/javascript" src="<c:url value="resources/js/ajax_functions.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/register.js" />" ></script>
</head>
<body>
	<div class="container">
		<div class="single">
			<div class="form-container">
				<h2>Register</h2>
				<form method="post" id="registerform" name="registerform">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="user_id">Choose your username:</label>
							<div class="col-md-9">
								<input type="text" path="user_id" id="user_id" name="user_id"
									class="form-control input-sm" />
							</div>
						</div>
						<input type="button" id="check" name="check" value="Check" onClick="javascript:onCheck();" />
						<div name="availableDiv" id="availableDiv"></div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<div class="col-md-9">
								<input type="hidden" path="user_typ" id="user_typ" name="user_typ" value="js"
									class="form-control input-sm" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="password">Password:</label>
							<div class="col-md-9">
								<input type="password" path="password" id="password" name="password"
									class="form-control input-sm" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="rpassword">Re-enter Password:</label>
							<div class="col-md-9">
								<input type="password" path="rpassword" id="rpassword"
									class="form-control input-sm" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="email_id">Email</label>
							<div class="col-md-9">
								<input type="text" path="email_id" id="email_id" name="email_id"
									class="form-control input-sm" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<div class="col-md-9">
								<input type="hidden" path="is_actv" id="is_actv" name="is_actv" value="Y"
									class="form-control input-sm" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-actions floatRight">
							<input type="button" value="Register"
								class="btn btn-primary btn-sm" onClick="javascript:validateForm();">
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="messageDiv" name="messageDiv"></div>
	</div>
</body>
</html>