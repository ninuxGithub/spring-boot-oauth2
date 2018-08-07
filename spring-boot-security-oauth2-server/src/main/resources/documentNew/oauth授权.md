## oauth2四种授权方式


### 1.授权码方式
	grant_type: authorization_code 
	demo的地址：http://localhost/springOauth/authorizationCode
	result:{"access_token":"aa21640c-36bf-4016-bb8c-273c6209bd56","token_type":"bearer","refresh_token":"fea704c6-9cd1-4614-ba9d-17ce58a20a08","expires_in":907,"scope":"read write"}
	
### 2.密码授权方式
	grant_type:password
	demo地址：http://localhost/springOauth/passwordOauth
	result: 在session
	
	
### 3.客户端信任方式
	grant_type: client_credencail
	demo地址：http://localhost/springOauth/clientOauth
	result:{"access_token":"13e507f7-56af-40d5-ae9a-4d9855b5e7d8","token_type":"bearer","expires_in":289,"scope":"read write"}
	
### 4.隐式授权方式
	grant_type:implicit
	demo地址：http://localhost/springOauth/implicitOauth
	result:http://localhost/springOauth/authorizationCodePage#access_token=aa21640c-36bf-4016-bb8c-273c6209bd56&token_type=bearer&expires_in=676&scope=read%20write
	
	
	