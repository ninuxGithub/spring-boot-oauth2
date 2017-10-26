<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.js"></script>
<!--     <script src="http://cdnjs.cloudflare.com/ajax/libs/vue/1.0.24/vue.min.js"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/vue-router/2.7.0/vue-router.min.js"></script> -->
<!--     <script type="text/javascript" src='http://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.9/vue-resource.min.js'></script> -->
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
			<li><a href="${request.contextPath}/api/users/" id="protectedResource">受保护的资源</a></li>
			<li><button id="btn">受保护的资源</button></li>
			<li><div id="users"></div></li>
		</ul>
	</div>	
	<div class="panel panel-footer">
	</div>	
</div>
</body>
<script type="text/javascript">
	$(function(){
		$('#btn').click(function(){
			var authorization = loadToken();
			if(authorization==null || authorization=='null'){
				return;
			}
			
			$.ajax({
				url:'${request.contextPath}/api/users/',
				type:'POST',
				dataType:'json',
				data:{},
				contentType:"application/x-www-form-urlencoded",
				async:false,
				beforeSend:function(xhr){  
		            xhr.setRequestHeader("Authorization", authorization);
		        },  
				error: function(XMLHttpRequest, textStatus) {
					console.dir("[passwordLogin error]"+ textStatus)
				},
				complete: function(XMLHttpRequest, textStatus) {
					console.dir("[passwordLogin complete]"+ textStatus)
				},
				success: function(data){
					$('#users').html(JSON.stringify(data))
				}
			});
		});
		
		
		function loadToken(){
			var token = null;
			$.ajax({
				url:'${request.contextPath}/springOauth/passwordAuth',
				type:'POST',
				dataType:'json',
				data:{},
				contentType:"application/x-www-form-urlencoded",
				async:false,
				beforeSend:function(xhr){  
		        },  
				error: function(XMLHttpRequest, textStatus) {
					console.dir("[loadToken error]"+ textStatus)
				},
				complete: function(XMLHttpRequest, textStatus) {
					console.dir("[loadToken complete]"+ textStatus)
				},
				success: function(data){
					console.dir(data)
					token = data['access_token'];
				}
			});
			return token;
		}
	});
</script>
</html>