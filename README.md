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

 Step 6) Add users via Bootstrap.groovy -- from @jnunderwood

 Step 7) Restart and test `api/login` endpoint

 Works as expected with return status 200 and JWT
 ```
 $ curl -v -s -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}' http://localhost:8181/api/login
 {"username":"admin","roles":["ROLE_ADMIN"],"token_type":"Bearer","access_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTQ1OTE5MTczOSwiaWF0IjoxNDU5MTg4MTM5fQ.gLtT9nerl0tkPhJsj0NwcfMw-LcFDewjyq6xBsjL0RA","expires_in":3600,"refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUDBcL2JRQlJcL0RvbW9RS0tBMUVvZFlBRTI1RWhsek1TXC9Wa1VtVkUyelVBbDBzUlwvbTRIem4zcDBoV1ZBbUdCaEFiWkdRK2hYNEpyRDBBeUE2ZEdYdXlqdERjTm9GY1pQOTd1ZmZ2K2VMVzZnWURYT3habHdZUHhWWnpLVnZVczFsYkRETU5MY2RQek9vSTdRNTRuME9iTklFN285WEFpK0FFbzhzakFjN2JJOVZCWk54ZGEyMWc2R3R0VFc4VlRwK1lOelNMTUY5cFhmOVIrNVFhZnhIb0tEMlRrc3d1QTVqTEF4VkptMWR5ZVYyeWpWRzZ6QmF6QUlWN3JyUnE1QnVVRnJPaE9tSERxSmtMWUZSQU1Nc3M5dUtWRGthQ3lcL3Z6V2FXaTJvRGJTMkFGeWt6aHR6OWw2UmhuWFYzNzJ4S1N2QVZEcURjVGowNjFOMk1nXC9xT3gxOVVRbEJxcnFTWmJzcEVSWHlMTzNIaTcwNTgrM1h5czlzc0FWQW5zMDlcL1U4emZMRUQzY3VQdlpGNjBGMXA0M1dlOWdOWGFLYmtaSzVnXC9hM1RLMStjZnY1XC9kSG4wWklHV0hlUGY4ZlV6UFB6VFhXVlJKeWpTenFtOUhSTHRmZHM5RXZ2QTBlVzhMSGJcL0JrMVFnXC9WSFNZdlFvVVJCVDNMSldvdGUzaGFGUGE4SHk1dnpTNm9lNmU2MndLT0dTVkVmeTBHNWJmcUJvVjhkXC9UcTlPcG02SVlRVXFlMHhrU0oyUEZxQjZsclJRSDE2Y1RRelwvK0gyY0orajl6WGZLODF4bEVRTUFBQT09Iiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlhdCI6MTQ1OTE4ODEzOX0.GHr-jGZgctIRlohfx7dSGCQ1FEc7Wpc1g461KeprYKE"}* timeout on name lookup is not supported
 *   Trying ::1...
 * Connected to localhost (::1) port 8181 (#0)
 > POST /api/login HTTP/1.1
 > Host: localhost:8181
 > User-Agent: curl/7.47.1
 > Accept: */*
 > Content-Type: application/json
 > Content-Length: 42
 >
 } [42 bytes data]
 * upload completely sent off: 42 out of 42 bytes
 < HTTP/1.1 200 OK
 < Server: Apache-Coyote/1.1
 < Cache-Control: no-store
 < Pragma: no-cache
 < Content-Type: application/json;charset=UTF-8
 < Content-Length: 2159
 < Date: Mon, 28 Mar 2016 18:02:19 GMT
 <
 { [2159 bytes data]
 * Connection #0 to host localhost left intact

 ```