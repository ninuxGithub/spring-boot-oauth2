# spring-boot-oauth2
spring-boot oauth2

项目:spring-boot-oauth2-server 可以采用任何方式获取token
项目：spring-boot-security-oauth2-server 可以采用任何方式获取token，可以自定义是否采用jwt

这是数据库对的client_details 的两天记录：每个记录拥有不同的获取token的方式观察authorized_token_type 字段  
client_auth_mode	oauth2-resource	e10adc3949ba59abbe56e057f20f883e	read,write	client_credentials,refresh_token		USER	1800	600		
password_auth_mode	oauth2-resource	e10adc3949ba59abbe56e057f20f883e	read,write	refresh_token,password,authorization_code,implicit	https://www.baidu.com	ADMIN	1800	600		



## 获取token一共分为4种：
		1. 客户端授权 grant_type=client_credentials
		2. 授权码授权 grant_type=authorization_code(注意参数名称的正确性)
		3. 密码授权      grant_type=password
		4. 隐式授权	grant_type=implicit
		当然 refresh_token 是采用已经获取的token， 在token过期之后用旧的token换区新的token的一种方式（本身不是获取token的方式）

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
然后采用postman发送请求：（一定要注意参数名称的正确性，我写成了redirect_url 总是报错纠结了好久）
body 部分：
		username:admin
		password:password
		grant_type:authorization_code
		client_id:password_auth_mode
		code:Oy3F7L
		client_secret:123456
		redirect_uri:https://www.baidu.com
		
成功的结果：
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCJdLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW4ifV0sImV4cCI6MTUwNjMwNTk2NywidXNlck5hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6IjI0NjBhNGJkLTZmOWEtNGM0OC1iOTM0LTU2ZTEyNzhlNWU3YSIsImNsaWVudF9pZCI6InBhc3N3b3JkX2F1dGhfbW9kZSJ9.7F40a1o1AQ0lSC8BuOJHFO-3Zt4j2bZxe9UD5dBWT6Q",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCJdLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW4ifV0sImF0aSI6IjI0NjBhNGJkLTZmOWEtNGM0OC1iOTM0LTU2ZTEyNzhlNWU3YSIsImV4cCI6MTUwNjMwNDc2NywidXNlck5hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6ImFiNGZkNDc1LWQ2NGQtNDA1MS1hOTc0LWE5YjcxM2Y2YzY4OCIsImNsaWVudF9pZCI6InBhc3N3b3JkX2F1dGhfbW9kZSJ9.mOG2pmj2QzCZFreo37hxK-1Id1nqNT5MwuW3WD1Wjak",
    "expires_in": 1799,
    "scope": "read",
    "roles": [
        {
            "authority": "admin"
        }
    ],
    "userName": "admin",
    "jti": "2460a4bd-6f9a-4c48-b934-56e1278e5e7a"
}
	
	

### 隐式授权方式
访问地址：http://localhost/oauth/authorize?response_type=token&client_id=password_auth_mode&redirect_uri=https://www.baidu.com

然后点击授权按钮：
得到结果：https://www.baidu.com/#access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInJvbGVzIjpbeyJhdXRob3JpdHkiOiJhZG1pbiJ9XSwiZXhwIjoxNTA2MzA2Mjc1LCJ1c2VyTmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiOWIwMmZlZjYtOTRhYy00MjYyLTllOTYtYzEyZDczODE4Mzc2IiwiY2xpZW50X2lkIjoicGFzc3dvcmRfYXV0aF9tb2RlIn0.NancTpSnku88wLMnJ9SPGDVm9V4bfU25MSVml7NWGaw&token_type=bearer&expires_in=1799&scope=read%20write&roles=%5Badmin%5D&userName=admin&jti=9b02fef6-94ac-4262-9e96-c12d73818376

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



### 对特定页面放行（不经过授权）
我们的案例目前使用的是WebSecurityConfigurerAdapter中默认的HttpSecurity对象的配置，该配置是要求应用中所有url的访问都需要进行验证。我们也可以自定义哪些URL需要权限验证，哪些不需要。只需要在我们的SecurityConfig类中覆写configure(HttpSecurity http)方法即可。
protected void configure(HttpSecurity http ) throws Exception {
             http
                  .authorizeRequests()            1                                                   
                        .antMatchers( "/resources/**", "/signup" , "/about").permitAll()  2
                        .antMatchers( "/admin/**").hasRole("ADMIN" )                    3    
                        .antMatchers( "/db/**").access("hasRole('ADMIN') and hasRole('DBA')")  4
                        .anyRequest().authenticated()        5
                                         
                        .and()
                   // ...
                  .formLogin();
      }
1、http.authorizeRequests()方法有很多子方法，每个子匹配器将会按照声明的顺序起作用。
2、指定用户可以访问的多个url模式。特别的，任何用户可以访问以"/resources"开头的url资源，或者等于"/signup"或about
3、任何以"/admin"开头的请求限制用户具有 "ROLE_ADMIN"角色。你可能已经注意的，尽管我们调用的hasRole方法，但是不用传入"ROLE_"前缀
4、任何以"/db"开头的请求同时要求用户具有"ROLE_ADMIN"和"ROLE_DBA"角色。
5、任何没有匹配上的其他的url请求，只需要用户被验证。










