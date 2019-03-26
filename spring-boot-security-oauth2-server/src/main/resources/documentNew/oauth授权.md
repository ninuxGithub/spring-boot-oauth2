## oauth2四种授权方式


### 1.授权码方式
	grant_type: authorization_code 
	demo的地址：http://localhost/springOauth/authorizationCode
	result:{"access_token":"aa21640c-36bf-4016-bb8c-273c6209bd56","token_type":"bearer","refresh_token":"fea704c6-9cd1-4614-ba9d-17ce58a20a08","expires_in":907,"scope":"read write"}
	
### 2.密码授权方式
	grant_type:password
	demo地址：http://localhost/springOauth/passwordOauth
	result: 在session
	
	密码的方式通过postman 发起post 请求获取token
	http://localhost/oauth/token?client_id=password_auth_mode&client_secret=123456&grant_type=password&username=admin&password=password
	
	通过第一步密码的方式获取refresh_token 然后通过postman 刷新token
	http://localhost/oauth/token?grant_type=refresh_token&refresh_token=787f73ca-3ecd-458c-8f00-81492c10390f&client_id=password_auth_mode&client_secret=123456
	
	check token :
	http://localhost/oauth/check_token?token=d01c6bed-043b-47b7-8706-8d8d5e9fe8f4
	参考了：https://blog.csdn.net/bluuusea/article/details/80284458
	
### 3.客户端信任方式
	grant_type: client_credencail
	demo地址：http://localhost/springOauth/clientOauth
	result:{"access_token":"13e507f7-56af-40d5-ae9a-4d9855b5e7d8","token_type":"bearer","expires_in":289,"scope":"read write"}
	
### 4.隐式授权方式
	grant_type:implicit
	demo地址：http://localhost/springOauth/implicitOauth
	result:http://localhost/springOauth/authorizationCodePage#access_token=aa21640c-36bf-4016-bb8c-273c6209bd56&token_type=bearer&expires_in=676&scope=read%20write
	
	
	