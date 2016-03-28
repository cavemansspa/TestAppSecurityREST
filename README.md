This repo shows the step-by-step changes to configure a Grails "--profile=rest-api" with Spring Security REST.

Using Grails 3.1.4

Step 1) Create the app
```
$ grails create-app TestAppSecurityREST --profile=rest-api
```

Step 2) Added dependencies to build.gradle

Step 3) Run s2-quickstart
```
$ grails s2-quickstart com.testappsecurityrest User Role
```

Step 4) Add filters [http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_plugin_configuration]

Step 5) Run app and test: `grails run-app --port=8181`

Endpoint has permit all, works as expected.
 ```
 $ curl -s -H "Content-Type: application/json" http://localhost:8181/
 {"message":"Welcome to Grails!","environment":"development","appversion":"0.1","grailsversion":"3.1.4","appprofile":"rest-api","groovyversion":"2.4.6","jvmversion":"1.8.0_74","reloadingagentenabled":true,"artefacts":{"controllers":4,"domains":3,"services":4},"controllers":[{"name":"grails.plugin.springsecurity.LogoutController","logicalPropertyName":"logout"},{"name":"grails.plugin.springsecurity.LoginController","logicalPropertyName":"login"},{"name":"grails.plugin.springsecurity.rest.RestOauthController","logicalPropertyName":"restOauth"},{"name":"testappsecurityrest.ApplicationController","logicalPropertyName":"application"}],"plugins":[{"name":"dataBinding","version":"3.1.4"},{"name":"core","version":"3.1.4"},{"name":"restResponder","version":"3.1.4"},{"name":"dataSource","version":"3.1.4"},{"name":"eventBus","version":"3.1.4"},{"name":"codecs","version":"3.1.4"},{"name":"i18n","version":"3.1.4"},{"name":"jsonView","version":"1.0.5"},{"name":"controllers","version":"3.1.4"},{"name":"urlMappings","version":"3.1.4"},{"name":"interceptors","version":"3.1.4"},{"name":"groovyPages","version":"3.1.4"},{"name":"controllersAsync","version":"3.1.4"},{"name":"domainClass","version":"3.1.4"},{"name":"hibernate","version":"5.0.3"},{"name":"mimeTypes","version":"3.1.4"},{"name":"converters","version":"3.1.4"},{"name":"services","version":"3.1.4"},{"name":"cache","version":"3.0.2"},{"name":"springSecurityCore","version":"3.0.2"},{"name":"springSecurityRest","version":"2.0.0.M2"}]}

```


Secured endpoint, returns 401 as expected.
```
$ curl -v -s -H "Content-Type: application/json" http://localhost:8181/application/testSecureJSON
{"timestamp":1459186986981,"status":401,"error":"Unauthorized","message":"No message available","path":"/application/testSecureJSON"}* timeout on name lookup is not supported
*   Trying ::1...
* Connected to localhost (::1) port 8181 (#0)
> GET /application/testSecureJSON HTTP/1.1
> Host: localhost:8181
> User-Agent: curl/7.47.1
> Accept: */*
> Content-Type: application/json
>
< HTTP/1.1 401 Unauthorized
< Server: Apache-Coyote/1.1
< WWW-Authenticate: Bearer
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 28 Mar 2016 17:43:06 GMT
<
{ [4 bytes data]
* Connection #0 to host localhost left intact

 ```