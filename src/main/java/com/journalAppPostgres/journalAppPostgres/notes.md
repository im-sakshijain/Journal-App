spring security is a powerful and higly
customizable security frameword that is often used in Springbott applications to hadnle 
authentication and authorization.

like ek api hai usko access krr skte ? usko authetication bolte
Authorization agar access hai toha ap kya kya krr skte ? 
Authorization the process of granting or denyin access to sepcific resoiurces or actions based
on the authenticated users roles and permsissions.

 Once the depencdy is added, Spring Boots auto configuration feature will automatically
apply security to be application
 By default, spring security uses http basic authetication.

the client send an authorization header
Authorization: Basic <encoded-string> the server decodes the string, extracts the username and apssword and verifies them. if they'are 
correct . if they are correct , access is granted. Otherwise, an Unauthorized response is sent back 


encodign
credentais  usersname:password 
which is then encodign using Base64
By default, all endpoitns will be secured. Spring Security will generate a default user with a 
randowm passsword that is printed in the console logs on startup

you can also configure username & password in your application.properties
 
spring.security.user.name = user
spring.secutiry.usr.password = password


Thsi annotation signals Spring to enables its web security supports. This
is what makes your application secured. Its used in conjunction with 
@Configuration

WebSecurtyConfigurerAdapter is a utitly class in the Spring Security frameword that provides
default ccondfiguratiosn and allows customization of certain features. By extending it, you can confgure and customize
Spring Security for your application needs


//configure
This method provides a way to configure how requests are secured. 
it defines how requests matchng should be done and what securtiy ations should be applied. 

http.authroizeRequests(): THis tells Srping securty to start authorizing the requests.

.anyMatchers("/hell").permitAll(): this part secifies that HTTp requests mathcing the path
/hello should be permitted(allowed) for all users, whether they are authenticated or not.

.anyRequest().authenticated(): this is a more general mathcer that speccicifes any request(
not already atched by prev matchers) should be autheticated, meaning usrrs have to provide valid creds to access
these endpoints.

.and(): This is a method to join several configurations. it helps to continue the configuration
from the root(HttpSecurity).

.formLogin(): This enables form based authentication. By default, it will provide a form for the
user to enter their username and password. If the user is not authenticated 
and they try to access 
a secured endpoint, they will be redirected to the default login form.

By default, Spring Security also provides logout functionality.
When .logout() is configured, a POST request to /logout will log the
user out and invalidate their session.

when you log in with spring security, it manages your authentication across multiple requests, despite
HTTP being stateless.

1. Session Creation: After successfull authetication, an HTTP session is formed. Your authentication
2. Session Cookies: A JSESSIONID cookie is sent to your brwoser, whicch gets sent back with 
subsequent requests, helping the server recognize your session.
3. SecurityContext: Using the JSESSIONID, spring security fetches your authentication details for 
each requests.
4. Session Timeout: Sessions have a limited life. If you are inactive past this limit, you are logged out.
5. Logout: When logging out, your session ends, and the related cookie is removed.
6. Remember-Me: Springn Seccurity can remember you even after the session ends using a different
7. persistent ccooktypically have a longer lifespan).
8. in essence spring security leverages sessions and cookies, mainly JSESSIONID, to ensure you remain
9. autheticated across requests)


application.properties -> src/main/resources
now springbot k andar src/main/rescourses ka jo container hai woh classPath mai add 
hojatta hai toh kuch krna nhi pdta isliye springboot khud dhhond lete agar khi aur rkhenge toh 
kch manually likhna pdta hai

yaml -> aint markup language
prirotites denge application.properties ko instead of application.yml if same prop
erties ho toh ok

command line ko bhi dete phir application properties then yml
mvn package bnao then java -jar snapshot.jar then --server.port=9090 aise bhi likh skte ok 


#LOGGING
logging is an essential aspect of application development that
allows developers to monitor and troubleshoot their applications.

Spring Boot supports various 
logging frameworks, such as LogBack, 
log4j2, and Java Util Logging (JUL).

1. LogBack: A popular logging framework that serves as the default in many Springboot applications.
It offers a flexible configuration and good performance.

2. Log4j: Another widely used logging framework with features such as asynchronous loggin and support
for various output formats.

3. Java Util Login (JUL): the default loggin framework included in the java Standard Edition. While its less feature rich thant 
some third party frameworks, it is straightforward and is part of the Java Platform

Spring Boot comes with a default logging configuration that uses LogBack as the default logging 
implementation. It provides a good balance between simplicity and flexiblity. 

The default configuration is embedded within the springboot libraries, and it may not be visible in your project source code

if you want to customize the logging configruation, you can create your own logback-spring.xml or logback.xml file in the src/main/resources
directory. When Spring boot detects this file in your project it will use it instead of the default configuration.

Logging Levels: 
Logging levels help in categorizing log statements based on their severity. THe common
logging levels are:
1. TRACE 
2. DEBUG
3. INFO
4. WARN
5. ERROR


We can set the desired logging level for specific packages  or classes, allowing them 
to control the amount of information logged at runtime.

By default logging is enabled for INFO WARN ERROR

Spring boot provides annotations like @Slf4j & @Log4j2 
that you can use go automatically inject logger instances into your classes. 

userEntryService mai bhi thoda likha hua hai logger ka

Spring Boot allows us to configure logging using properties or YAML files.

pan logback.xml file bhi likh skte hai usme sab bta skte formatting of loggign and all ok


Sonarqube:
