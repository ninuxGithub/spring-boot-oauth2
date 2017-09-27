<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue-router/2.7.0/vue-router.min.js"></script>
    <script type="text/javascript" src='http://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.9/vue-resource.min.js'></script>
	<title>Password Oauth</title>
	<meta name="_csrf" content="${_csrf.token}"/>  
	<meta name="_csrf_header" content="${_csrf.headerName}"/>  
</head>
<body>
	<div class="container">
		<div class="panel panel-header">
			<h2>密码的方式获取token</h2>
		</div>
		<div class="panel panel-body">
			<form action="${request.contextPath}/oauth/token"  method="post">
				<div class="form-group">
					<label for="clientId" class="col-sm-2 control-label">ClientId</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="clientId" name="client_id" value="password_auth_mode" placeholder="input ClientId (客户端必须支持授权码方式)....">
					</div>
				</div>
				<div class="form-group">
					<label for="clientSecret" class="col-sm-2 control-label">ClientSecret</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="clientSecret" name="client_secret" value="123456" placeholder="input ClientSecret (客户端必须支持授权码方式)....">
					</div>
				</div>
				
				
				<div class="form-group">
					<label for="grantType" class="col-sm-2 control-label">GrantType</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="grantType" name="grant_type" value="password" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">UserName</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="username" name="username" value="admin" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Password</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="password" name="password" value="password" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Go</button>
					</div>
				</div>
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			</form>
		</div>
	</div>
</body>
</html>