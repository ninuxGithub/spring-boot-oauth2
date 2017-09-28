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
			<h2>Authorization Code-- 获取到授权码</h2>
		</div>
		<div class="panel panel-body">
<!-- 		不可以采用HttpClient 以post方式发送到 server /oauth/token -->
<%-- 			<form action="${request.contextPath}/springOauth/authToken"  method="post"> --%>
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
					<label for="redirect_uri" class="col-sm-2 control-label">RedirectUri</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="redirect_uri" name="redirect_uri" value="http://localhost/springOauth/authorizationCodePage" placeholder="input RedirectUri (重定向的uri)....">
					</div>
				</div>
				
				
				<div class="form-group">
					<label for="grantType" class="col-sm-2 control-label">GrantType</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="grantType" name="grant_type" value="authorization_code" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="code" class="col-sm-2 control-label">Code</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="code" name="code" value="code" readonly="readonly">
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
<script type="text/javascript">
	$(function(){
		var code = queryString("code");
		if(null != code && "" != code){
			$("#code").val(code);
		}else{
			console.dir("URL没有携带code参数")
		}
		
// 		var token = $("meta[name='_csrf']").attr("content");  
// 		var header = $("meta[name='_csrf_header']").attr("content");  
// 		$(document).ajaxSend(function(e, xhr, options) {  
// 		    xhr.setRequestHeader(header, token);  
// 		}); 
	});
	
	function queryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	
</script>
</html>