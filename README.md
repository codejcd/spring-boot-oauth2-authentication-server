# Spring-boot-oauth2-AuthenticationServer
Spring-boot-oauth2-AuthenticationServer

# 내용
스프링 부트 기반 Oauth2 인증 서버.
Spring-security-oauth2 를 이용하지 않고   
JWT와 crypto 라이브러리만 이용하여 Oauth2의 password credential 구현.   
Spring security Oauth2는 매우 편리하고 간편하게 Oauth2를 적용 가능하나   
실제 비즈니스 환경에서 여러가지 요구사항을 가미하려다보면 테이블 네임이나 컬럼 네임 같이 사소한 것      
하나하나 커스터마이징이 필요할 수 있을 것 같아 프로토타입으로 개발을 진행.

# 참고
Spring security Oauth2 2022년 지원 종료 예정.   
상세 설명은 아래 블로그를 참고해주세요.   
https://codejcd.tistory.com/13   

# 개발 환경
Spring Boot 2.1.6 / Maven 4.0.0 / Mybatis 2.1.0 / MySQL 5.6 / JDK 1.8

# Oauth2 인증 방식 
Authorization Code, Implicit, password credential, client credential 중 __password credential__ 사용

# 테스트
### POSTMAN 같은 API TEST 가능한 툴로 아래의 요건으로 호출.

1. Client Id 등록
* URI : http://localhost:8095/client/regist
* HTTP METHOD : POST
* BODY PARAM  : 
  * clientId : Client ID
  * clientSecret : Client Secret key

2. User 등록
* URI : http://localhost:8095/user/regist
* HTTP METHOD : POST
* BODY PARAM  : 
  * userId : USER ID(account)
  * userName : User Name
  * password : User Password

3. token 발급
* URI : http://localhost:8095/oauth2/token
* HTTP METHOD : POST
* HTTP HEADER : Authorization Basic
  * username : Client ID
  * password : Client Secret key
* BODY PARAM  : 
  * userId : User ID(account)
  * password : User Password
 
4. refresh token 발급
* URI : http://localhost:8095/oauth2/token/refresh 
* HTTP METHOD : POST 
* HTTP HEADER : Authorization Basic
  * username : Client ID
  * password : Client Secret key
* BODY PARAM  : 
  * refresh_token : Bearer Token
  
# DB 스키마
<pre>
이 프로그램에 사용된 DB 스키마 파일은 src/resource/db/scheme.sql 을 참고해주세요.

https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
위 URL은 Springboot Oauth2 의 DB 스키마 샘플이며 참고하시기 바랍니다.
</pre>

