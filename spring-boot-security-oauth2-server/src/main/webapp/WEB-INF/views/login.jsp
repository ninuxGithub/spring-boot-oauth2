<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>登录</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>

<style>
	.login-container {
		margin: 50px;
		width: 100%;
	}

	.form-container {
		margin: 0px auto;
		width: 50%;
		text-align: center;
		box-shadow: 1px 1px 10px #888888;
		height: 300px;
		padding: 5px;
	}

	input {
		margin-top: 10px;
		width: 350px;
		height: 30px;
		border-radius: 3px;
		border: 1px #E9686B solid;
		padding-left: 2px;

	}


	.btn {
		width: 350px;
		height: 35px;
		line-height: 35px;
		cursor: pointer;
		margin-top: 20px;
		border-radius: 3px;
		background-color: #E9686B;
		color: white;
		border: none;
		font-size: 15px;
	}

	.title{
		margin-top: 5px;
		font-size: 18px;
		color: #E9686B;
	}
</style>
<body>
<div class="login-container">
	<div class="form-container">
		<p class="title">用户登录</p>
		<form name="loginForm" method="post" action="/auth/authorize">
			<input type="text" name="username" placeholder="用户名"/>
			<br>
			<input type="password" name="password" placeholder="密码"/>
			<br>
			<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			<button type="submit" class="btn">登    录</button>
		</form>
	</div>
</div>
</body>
</html>