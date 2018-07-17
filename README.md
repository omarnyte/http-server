# HTTP Server
This is a simple server built with Java 10. It can receive, parse, handle, and respond to HTTP 1.1 requests. 

## Dependencies 
* [Gradle](https://gradle.org) 4.12
* Java 10

## Starting the Server 
1. Navigate to the root directory.
2. Create a jar file using Gradle. This will create the `build` directory.
```gradle jar```
3. Navigate to the directory where the jar file is located.
```cd build/libs```
4. Run the JAR file with a port number provided as the one and only argument. No default port is provided.  
```java -jar http-server.jar 4444```
5. Fire away at the server! Using your browser, cURL, Postman, etc., navigate to http://localhost:4444/. 

## Running Tests 
Unit tests covering this project are written with the [JUnit](https://junit.org/junit4/) testing framework. 
1. Navigate to the root directory.
2. Run `gradle test`. 
3. To rerun tests after creating a change in the code, run `gradle build` to simultaneously build the output and run all unit tests. 

## Contributing
1. Fork the repository.
2. Create a feature branch using Git.
3. Write your feature in the ```src/main/java``` directory.
4. Write a unit test (covering every new public method) in the ```src/test/java``` directory. 
5. Run `gradle build` to ensure that the application successfully builds and passes all tests. 
6. Create a pull request. 

## TODO 
1. Can handle multiple, simultaneous requests. 
2. GET requests to the `/echo` URI returns the current time.  