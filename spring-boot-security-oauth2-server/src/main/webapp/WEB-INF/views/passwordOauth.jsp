<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.js"></script>
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
						<button type="button" id="submit" class="btn btn-primary">Go</button>
					</div>
				</div>
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$('#submit').click(function(){
			var requestData = {
				"client_id":$('#clientId').val(),
				"client_secret":$('#clientSecret').val(),
				"grant_type":$('#grantType').val(),
				"username":$('#username').val(),
				"password":$('#password').val()
			};
			
			//ajax 获取token
// 			var tokenData = passwordToken(requestData);
// 			var token = tokenData['access_token'];
// 			console.dir(token)
// 			alert(token)
			
// 			var loginData = passwordLogin(requestData);
// 			console.dir(loginData.postData)
// 			var loginJson = JSON.parse(loginData.postData);
// 			console.dir(loginJson)
// 			console.dir(loginJson['access_token'])

			//获取登录状态
			var loginData = passwordLogin(requestData);
			var loginStatus = loginData.loginStatus;
			console.dir(loginStatus)
			if(loginStatus){
				window.location.href = "http://localhost/springOauth/index";
			}else{
				alert("登陆失败")
			}
		});
		
		function passwordLogin(requestData){
			var loginData=null;
			$.ajax({
				url:'http://localhost/springOauth/passwordLogin',
				type:'POST',
				dataType:'json',
				data:requestData,
				contentType:"application/x-www-form-urlencoded",
				async:false,
				beforeSend:function(xhr){  
		            console.log(this);  
		            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		        },  
				error: function(xhr, textStatus) {
					console.dir("[passwordLogin error]"+ textStatus)
				},
				complete: function(xhr, textStatus) {
					console.dir("[passwordLogin complete]"+ textStatus)
				},
				success: function(data){
					loginData = data;
				}
			});
			return loginData;
		}
		
		
		
		//在前台获取到token
		function passwordToken(requestData){
			var tokenData = null;
			$.ajax({
				url:'http://localhost/oauth/token',
				type:'POST',
				dataType:'json',
				data:requestData,
				contentType:"application/x-www-form-urlencoded",
				async:false,
				beforeSend:function(xhr){  
		            console.log(this);  
		        },  
				error: function(xhr, textStatus) {
					console.dir("[error]"+ textStatus)
				},
				complete: function(xhr, textStatus) {
					console.dir("[complete]"+ textStatus)
				},
				success: function(data){
					tokenData = data;
				}
			});
			return tokenData;
		}
	});
</script>
</html>