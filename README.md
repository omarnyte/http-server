# HTTP Server
This is a simple server built with Java 10. It can receive, parse, handle, and respond to HTTP 1.1 requests. For more detailed functionality and limitations, see [Supported Functionality](#supported-functionality).


## Dependencies 
* [Cucumber](https://cucumber.io) 
* [Gradle 4.12](https://gradle.org) 
* [Java 10](http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html)


## Starting the Server 

### On Default Port and Directory
1. Navigate to the root directory 
2. Run the following gradle tasks 
    ```
    gradle assemble
    gradle startDemoServer
    ```
* The default port is 8888
* The default directory from which to serve files is the following directory: `src/test/resources/demoFiles` 
    
### On Custom Port and Custom Data Store 
1. Navigate to the root directory.
2. Create a JAR file using Gradle. This will create the `build` directory.
    ```
    gradle jar
    ```

3. Navigate to the directory where the jar file is located.
    ```
    cd build/libs
    ```
4. Run the JAR file with a port flag (`-port`) and number as well as a directory flag (`-dir`) and a directory path. 
    ```
    java -jar http-server.jar -port 4444 -dir src/test/resources/demoFiles 
    ```
5. Fire away at the server! Using your browser, cURL, Postman, etc., navigate to http[]()://localhost:*PORT_NUMBER*


## Running Tests 
Unit tests covering this project are written with the [JUnit](https://junit.org/junit4/) testing framework. Acceptance tests are written using [Cucumber](https://docs.cucumber.io). To run acceptance tests, the server must be running on port 8888 and serving content from `src/test/resources/testFiles`:
1. Navigate to the root directory.
2. Run the following gradle tasks:
    ```
    gradle assemble
    gradle startTestServer
    ```
2. Run `gradle test`. 
3. To automatically rerun tests after changing the code, run `gradle -t test` to simultaneously build the output and run all unit tests. 

## Supported Functionality 

### Endpoints 
| URL         | Supported Methods | Supported Media                    |
|:----------- |:----------------- |:---------------------------------- |
| /*          | OPTIONS           | N/A                                |
| /api/form   | OPTIONS, POST     | application/x-www-url-form-encoded |
| /api/people | OPTIONS, POST     | application/JSON, text/plain       |
| /api/query  | GET, OPTIONS      | N/A                                |
| /echo       | GET, OPTIONS      | N/A                                |

### Static Files
To determine which HTTP methods can be applied to a static resource, you can make an OPTIONS request to that resource. 

#### OPTIONS 
All static files and endpoints can handle OPTIONS requests.  

#### GET 
All static files can handle GET requests.  

#### HEAD 
All static files can handle HEAD requests.  

#### PUT
A PUT request to an existing static resource will overwrite the resource using the request entity. A PUT request to a nonexistent resource will create that resource using the request entity. 

#### PATCH
Currently, only JSON resources can be PATCHed, and the patch document must be of type `application/json-patch+json`. 

The server's handling of [JSON Patch](http://jsonpatch.com) is limited. Arrays cannot be added as values or modified. Additionally, nested JSON objects cannot be added as values, but individual key/value pairs in an existing, nested JSON object can be manipulated using any of the JSON Patch operations. 

#### DELETE 
All static files can handle DELETE requests.  


### Middleware 
The server makes use of a chain of middleware classes to operate on requests and responses. All Middleware classes  extend the `Middleware` class. The middleware chain can be configured in the `#getMiddlewareChain` method of the `MiddlewareConfig` class. 

The middleware chain currently consists of the following classes:
1. CorsMiddleware
2. Logger 
3. Authenticator 


### Protected Routes 
Routes can optionally be protected with [HTTP authentication](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication). Credentials for a protected route are set in `#setUpAuthenticator` in the `MiddlewareConfig` class. Routes that should be protected can also be added within this same method. 

## Contributing
1. Fork the repository.
2. Create a feature branch using Git.
3. Write your feature in the ```src/main/java``` directory.
4. Write a unit test (covering every new public method) in the ```src/test/java``` directory. 
5. Run `gradle build` to ensure that the application successfully builds and passes all tests. 
6. Create a pull request. 
