<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue-router/2.7.0/vue-router.min.js"></script>
    <script type="text/javascript" src='http://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.9/vue-resource.min.js'></script>
	<title>Spring-Security-OAuth2.0</title>
</head>
<body>
<div class="container">
	<div class="panel panel-header">
		<h2>spring-oauth2授权</h2>
	</div>	
	<div class="panel panel-body">
		<ul>
			<li><a href="${request.contextPath}/springOauth/authorizationCode">授权码 (结果携带refresh_token)</a></li>
			<li><a href="${request.contextPath}/springOauth/passwordOauth">密码授权 (结果携带refresh_token)</a></li>
			<li><a href="${request.contextPath}/springOauth/clientOauth">客户端信任授权</a></li>
			<li><a href="${request.contextPath}/springOauth/implicitOauth">隐式授权</a></li>
		</ul>
	</div>	
	<div class="panel panel-footer">
	</div>	
</div>
</body>
</html>