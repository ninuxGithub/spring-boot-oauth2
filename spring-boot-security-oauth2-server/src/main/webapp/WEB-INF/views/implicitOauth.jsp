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
	<title>Implicit Code</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-header">
			<h2>隐式授权获取token</h2>
		</div>
		<div class="panel panel-body">
			<form >
				<div class="form-group">
					<label for="clientId" class="col-sm-2 control-label">ClientId</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="client_id" name="client_id" value="password_auth_mode" placeholder="input ClientId (客户端必须支持授权码方式)....">
					</div>
				</div>
				<div class="form-group">
					<label for="redirect_uri" class="col-sm-2 control-label">RedirectUri</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="redirect_uri" name="redirect_uri" value="http://localhost/springOauth/authorizationCodePage" placeholder="input RedirectUri (重定向的uri)....">
					</div>
				</div>
				<div class="form-group">
					<label for="response_type" class="col-sm-2 control-label">responseType</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="response_type" name="response_type" value="token" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" class="btn btn-primary" id="subBtn">Go</button>
					</div>
				</div>
				<input id="oauthUrl" type="hidden" value="${oauthUrl}"> 
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#subBtn").click(function(){
			onSubmit();
		});
		
	});

	function onSubmit(){
		var clientId = $("#client_id").val();
		var redirectUrl = $("#redirect_uri").val();
		var responseType = $("#response_type").val();

		if(clientId == null || clientId =="null"){
			alert("请填写clientId")
			return;
		}
		if(redirectUrl == null || redirectUrl =="null"){
			alert("请填写redirectUrl")
			return;
		}
		if(responseType == null || responseType =="null"){
			alert("请填写responseType")
			return;
		}

		var oauthUrl = $("#oauthUrl").val();
		var redirectUrl = oauthUrl+"?response_type=token&client_id="+clientId+"&redirect_uri="+redirectUrl;
		console.dir(redirectUrl)
		//重定向到这个指定的页面
		window.location.href= redirectUrl;
	}
	
</script>
</html>