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
	<title>Authorization Code</title>
	<meta name="_csrf" content="${_csrf.token}"/>  
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<div class="container">
		<div class="panel panel-header">
			<h2>Authorization Code</h2>
		</div>
		<div class="panel panel-body">
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
						<input type="text" class="form-control" id="response_type" name="response_type" value="code" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button"  class="btn btn-primary" id="subBtn">Go</button>
					</div>
				</div>
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				<input id="oauthUrl" type="hidden" value="${oauthUrl}"> 
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$('#submit').click(function(){
			var requestData = {
				"client_id":$('#client_id').val(),
				"response_type":$('#response_type').val(),
				"redirect_uri":$("#redirect_uri").val(),
// 				"${_csrf.parameterName}":"${_csrf.token}"
			};
			var tokenData = authorizaitonCode(requestData);
			console.dir(tokenData)
			
		});
	});
	
	//在前台获取到token
	function authorizaitonCode(requestData){
		var tokenData = null;
		$.ajax({
			url:'http://localhost/oauth/authorize',
			type:'get',
			dataType:'json',
			data:requestData,
			contentType:"application/x-www-form-urlencoded",
			async:false,
			beforeSend:function(xhr){  
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
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
		var redirectUrl = oauthUrl+"?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUrl;
		console.dir(redirectUrl)
		//重定向到这个指定的页面
		window.location.href= redirectUrl;
	}
	
</script>
</html>