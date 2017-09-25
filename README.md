# spring-boot-oauth2
spring-boot oauth2



## 获取token一共分为4种：
		1. 客户端授权 grant_type=client_credentials
		2. 授权码授权



### 客户端授权方式
客户端获取token的方式：
http://127.0.0.1/oauth/token?grant_type=client_credentials
结果：
{
    "access_token": "af220974-e93d-4085-aa20-b4b414f01ba6",
    "token_type": "bearer",
    "expires_in": 1799,
    "scope": "read write"
}


### 密码方式
postman : http://127.0.0.1/oauth/token
选择post, basic oauth
Authorization 界面输入参数username password（其实这个是client_id, client_secrect）
在body界面输入参数：username, password granttype client_id client_secrect scope 

结果：
{
    "access_token": "2a55328c-20f2-445c-b4a8-94ae59ce8f1e",
    "token_type": "bearer",
    "refresh_token": "c18fb0f4-2452-4f42-b455-bee1a67f7748",
    "expires_in": 1799,
    "scope": "read write"
}

### 授权码方式
http://127.0.0.1/oauth/authorize?response_type=code&client_id=password_auth_mode&redirect_uri=http://www.baidu.com

### 隐式授权方式


### 访问受保护的资源
http://127.0.0.1/api/users/?access_token=af220974-e93d-4085-aa20-b4b414f01ba6
结果：
[
    {
        "id": 1,
        "username": "admin",
        "password": "5f4dcc3b5aa765d61d8327deb882cf99",
        "roles": [
            {
                "id": 1,
                "roleName": "admin"
            }
        ]
    }
]










