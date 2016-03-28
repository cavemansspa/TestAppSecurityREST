This repo shows the step-by-step changes to configure a Grails "--profile=rest-api" with Spring Security REST.

Using Grails 3.1.4

Step 1) Create the app [Inital commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/5dcdc15da50d22cd854c0d5b95a2febdf9515fe9)
```
$ grails create-app TestAppSecurityREST --profile=rest-api
```

Step 2) Added dependencies to build.gradle [Commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/71c2869c0ed94b77d82cd3c3a1de98ce768a0313)

Step 3) Run s2-quickstart [Commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/c8adfe4abc595603c6519b2f4111716f80655c06)
```
$ grails s2-quickstart com.testappsecurityrest User Role
```

Step 4) Add filters [http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_plugin_configuration]
[Commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/1e1860379c431d85280efc8fca01e9d887f35dd3)

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

 Step 6) Add users via Bootstrap.groovy -- from @jnunderwood [Commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/b96ac816c5538640be74fda1fdaeadab458a0f95)

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

 Step 8) Annotate endpoints [Commit](https://github.com/cavemansspa/TestAppSecurityREST/commit/acea45d24da0bb8de783c277bb09186978d2dc4a)
 ```
     @Secured(['ROLE_ADMIN', 'ROLE_USER'])
     def testSecureJSON() {
         def ret = [hello: 'world secure']
         println 'in testJSecureSON ' + params
         render ret as JSON
     }
 ```

 Step 9) Test endpoints
 ```
 $ curl -v -s -H "Content-Type: application/json" -d '{"username": "guest", "password": "guest"}' http://localhost:8080/api/login
 {"username":"guest","roles":["ROLE_USER"],"token_type":"Bearer","access_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUFU4VVVSUzlNKzRHRFlrQ0NTWVcySWlkbVUydzNBbzJxNG1aZ0dIZEJoUEoyNW5MK09ETmU4UDdnTjNHYktVRkJVUWxNZkV2OEUrZzRRY1FMV3lwYmJsdldKaUZodkNxbWZ2T25IUHVPWE4wRG5XajRYV21HUmNtS29UTHVJeE1vYm5NRENaT2N6dUluRUdkb2kwUmIwdGdseVp3ZVlJUWdoaENubHFZaVRmWkRtc0lKclBHU204VEU5dnNhMWhRT2hzeGJtaVc0NjdTVzlFMWQ2STAzaENvcUlPREVDYldZSm9saVhMU0xpdlo3aGRjWTdvR1U5VXNWc21XSDgwbWRJUFNjaWJNT0hRQ0plc0pUR09ZWk01K1ZxVEswVmg0Y21uV1dTNGFIYlROR0I0V3pCaHlkMnVUanZYV1wvYjIzS1dtRGJmZ0N0WDRSMEtIc1hucG81SG1pbGhLQ3R1Wkttdm11ekZYS043Z1hKXC83aDNQZlRcL2RcL0RiZ2hBbWJ5Nis1dHFcL213SmhzZWZcL2o4dmd3NFNDMFwvSHJGZXdacjhnTjlNVjh3ZU5Ydm5zMVwvc2ZoK2ZmUGo0Z1pZOTRjXC84KzVoZEh5UTFhS2krWVpsYU5kVVMwdXpYXC9UT1JMZDVOZnRUQ0lPand2Qk5JZkpTMm0xeElWTWExYjAwcGM1VzNoMGVwSzNGN3ZkdHFyXC9xMmVPU3FTUkIrWE9cL3V5b2xoUlZYdlwvRGs3MlhcL3doZ25kUTMySENJVVUrVllHV1hkNURcL2ZYb2NHN3k1OSs5Y29IUnp4eGVBSHFtU0pJUUF3QUEiLCJzdWIiOiJndWVzdCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJleHAiOjE0NTkxOTgxNDYsImlhdCI6MTQ1OTE5NDU0Nn0.okr3Ve-jTOqyrxppPqFCDVOmnyeppxTlmBOgA5T6rZA","expires_in":3600,"refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUFU4VVVSUzlNKzRHRFlrQ0NTWVcySWlkbVUydzNBbzJxNG1aZ0dIZEJoUEoyNW5MK09ETmU4UDdnTjNHYktVRkJVUWxNZkV2OEUrZzRRY1FMV3lwYmJsdldKaUZodkNxbWZ2T25IUHVPWE4wRG5XajRYV21HUmNtS29UTHVJeE1vYm5NRENaT2N6dUluRUdkb2kwUmIwdGdseVp3ZVlJUWdoaENubHFZaVRmWkRtc0lKclBHU204VEU5dnNhMWhRT2hzeGJtaVc0NjdTVzlFMWQ2STAzaENvcUlPREVDYldZSm9saVhMU0xpdlo3aGRjWTdvR1U5VXNWc21XSDgwbWRJUFNjaWJNT0hRQ0plc0pUR09ZWk01K1ZxVEswVmg0Y21uV1dTNGFIYlROR0I0V3pCaHlkMnVUanZYV1wvYjIzS1dtRGJmZ0N0WDRSMEtIc1hucG81SG1pbGhLQ3R1Wkttdm11ekZYS043Z1hKXC83aDNQZlRcL2RcL0RiZ2hBbWJ5Nis1dHFcL213SmhzZWZcL2o4dmd3NFNDMFwvSHJGZXdacjhnTjlNVjh3ZU5Ydm5zMVwvc2ZoK2ZmUGo0Z1pZOTRjXC84KzVoZEh5UTFhS2krWVpsYU5kVVMwdXpYXC9UT1JMZDVOZnRUQ0lPand2Qk5JZkpTMm0xeElWTWExYjAwcGM1VzNoMGVwSzNGN3ZkdHFyXC9xMmVPU3FTUkIrWE9cL3V5b2xoUlZYdlwvRGs3MlhcL3doZ25kUTMySENJVVUrVllHV1hkNURcL2ZYb2NHN3k1OSs5Y29IUnp4eGVBSHFtU0pJUUF3QUEiLCJzdWIiOiJndWVzdCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE0NTkxOTQ1NDZ9.JBXn9Meipc6wVxl-jFygCfkGv3yrMVJQihtUAwToPPI"}* timeout on name lookup is not supported
 *   Trying ::1...
 * Connected to localhost (::1) port 8080 (#0)
 > POST /api/login HTTP/1.1
 > Host: localhost:8080
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
 < Content-Length: 2160
 < Date: Mon, 28 Mar 2016 19:49:06 GMT
 <
 { [2160 bytes data]
 * Connection #0 to host localhost left intact


 ```

 Use the access_token on your requests.
 ```
 $ curl -v -H "Content-Type: application/json" -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUFU4VVVSUzlNKzRHRFlrQ0NTWVcySWlkbVUydzNBbzJxNG1aZ0dIZEJoUEoyNW5MK09ETmU4UDdnTjNHYktVRkJVUWxNZkV2OEUrZzRRY1FMV3lwYmJsdldKaUZodkNxbWZ2T25IUHVPWE4wRG5XajRYV21HUmNtS29UTHVJeE1vYm5NRENaT2N6dUluRUdkb2kwUmIwdGdseVp3ZVlJUWdoaENubHFZaVRmWkRtc0lKclBHU204VEU5dnNhMWhRT2hzeGJtaVc0NjdTVzlFMWQ2STAzaENvcUlPREVDYldZSm9saVhMU0xpdlo3aGRjWTdvR1U5VXNWc21XSDgwbWRJUFNjaWJNT0hRQ0plc0pUR09ZWk01K1ZxVEswVmg0Y21uV1dTNGFIYlROR0I0V3pCaHlkMnVUanZYV1wvYjIzS1dtRGJmZ0N0WDRSMEtIc1hucG81SG1pbGhLQ3R1Wkttdm11ekZYS043Z1hKXC83aDNQZlRcL2RcL0RiZ2hBbWJ5Nis1dHFcL213SmhzZWZcL2o4dmd3NFNDMFwvSHJGZXdacjhnTjlNVjh3ZU5Ydm5zMVwvc2ZoK2ZmUGo0Z1pZOTRjXC84KzVoZEh5UTFhS2krWVpsYU5kVVMwdXpYXC9UT1JMZDVOZnRUQ0lPand2Qk5JZkpTMm0xeElWTWExYjAwcGM1VzNoMGVwSzNGN3ZkdHFyXC9xMmVPU3FTUkIrWE9cL3V5b2xoUlZYdlwvRGs3MlhcL3doZ25kUTMySENJVVUrVllHV1hkNURcL2ZYb2NHN3k1OSs5Y29IUnp4eGVBSHFtU0pJUUF3QUEiLCJzdWIiOiJndWVzdCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJleHAiOjE0NTkxOTgxNDYsImlhdCI6MTQ1OTE5NDU0Nn0.okr3Ve-jTOqyrxppPqFCDVOmnyeppxTlmBOgA5T6rZA'  http://localhost:8181/application/testSecureJSON
 * timeout on name lookup is not supported
 *   Trying ::1...
   % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                  Dload  Upload   Total   Spent    Left  Speed
   0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (::1) port 8181 (#0)
 > GET /application/testSecureJSON HTTP/1.1
 > Host: localhost:8181
 > User-Agent: curl/7.47.1
 > Accept: */*
 > Content-Type: application/json
 > Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUFU4VVVSUzlNKzRHRFlrQ0NTWVcySWlkbVUydzNBbzJxNG1aZ0dIZEJoUEoyNW5MK09ETmU4UDdnTjNHYktVRkJVUWxNZkV2OEUrZzRRY1FMV3lwYmJsdldKaUZodkNxbWZ2T25IUHVPWE4wRG5XajRYV21HUmNtS29UTHVJeE1vYm5NRENaT2N6dUluRUdkb2kwUmIwdGdseVp3ZVlJUWdoaENubHFZaVRmWkRtc0lKclBHU204VEU5dnNhMWhRT2hzeGJtaVc0NjdTVzlFMWQ2STAzaENvcUlPREVDYldZSm9saVhMU0xpdlo3aGRjWTdvR1U5VXNWc21XSDgwbWRJUFNjaWJNT0hRQ0plc0pUR09ZWk01K1ZxVEswVmg0Y21uV1dTNGFIYlROR0I0V3pCaHlkMnVUanZYV1wvYjIzS1dtRGJmZ0N0WDRSMEtIc1hucG81SG1pbGhLQ3R1Wkttdm11ekZYS043Z1hKXC83aDNQZlRcL2RcL0RiZ2hBbWJ5Nis1dHFcL213SmhzZWZcL2o4dmd3NFNDMFwvSHJGZXdacjhnTjlNVjh3ZU5Ydm5zMVwvc2ZoK2ZmUGo0Z1pZOTRjXC84KzVoZEh5UTFhS2krWVpsYU5kVVMwdXpYXC9UT1JMZDVOZnRUQ0lPand2Qk5JZkpTMm0xeElWTWExYjAwcGM1VzNoMGVwSzNGN3ZkdHFyXC9xMmVPU3FTUkIrWE9cL3V5b2xoUlZYdlwvRGs3MlhcL3doZ25kUTMySENJVVUrVllHV1hkNURcL2ZYb2NHN3k1OSs5Y29IUnp4eGVBSHFtU0pJUUF3QUEiLCJzdWIiOiJndWVzdCIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJleHAiOjE0NTkxOTgxNDYsImlhdCI6MTQ1OTE5NDU0Nn0.okr3Ve-jTOqyrxppPqFCDVOmnyeppxTlmBOgA5T6rZA
 >
 < HTTP/1.1 200 OK
 < Server: Apache-Coyote/1.1
 < X-Application-Context: application:development:8181
 < Content-Type: application/json;charset=UTF-8
 < Transfer-Encoding: chunked
 < Date: Mon, 28 Mar 2016 20:09:31 GMT
 <
 { [35 bytes data]
 100    24    0    24    0     0   1600      0 --:--:-- --:--:-- --:--:--  1600{"hello":"world secure"}
 * Connection #0 to host localhost left intact

 ```
