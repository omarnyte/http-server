# HTTP Server
This is a simple server built with Java 10. It can receive, parse, handle, and respond to HTTP 1.1 requests. 


## Dependencies 
* [Cucumber](https://cucumber.io) 
* [Gradle 4.12](https://gradle.org) 
* [Java 10](http://www.oracle.com/technetwork/java/javase/downloads/jre10-downloads-4417026.html)


## Starting the Server 

### On Default Port and Data Store
1. Navigate to the root directory 
2. Run the following gradle task 
    ```
    gradle startServer
    ```
* The default port is 8888
* The default data store from which to serve files is the following directory: `src/test/resources/testFiles` 

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
4. Run the JAR file with a port flag (`-port`) and number as well as a data store flag and a source. Currently, only directories (-dir) are supported. 
    ```
    java -jar http-server.jar -port 4444 -dir src/test/resources/testFiles 
    ```
5. Fire away at the server! Using your browser, cURL, Postman, etc., navigate to http://localhost:[*PORT_NUMBER*] 


## Running Tests 
Unit tests covering this project are written with the [JUnit](https://junit.org/junit4/) testing framework. Acceptance tests are written using [Cucumber](https://docs.cucumber.io). To run acceptance tests, the server must be running on port 8888. After starting the server (see above):
1. Navigate to the root directory.
2. Run `gradle test`. 
3. To automatically rerun tests after changing the code, run `gradle -t test` to simultaneously build the output and run all unit tests. 


## Contributing
1. Fork the repository.
2. Create a feature branch using Git.
3. Write your feature in the ```src/main/java``` directory.
4. Write a unit test (covering every new public method) in the ```src/test/java``` directory. 
5. Run `gradle build` to ensure that the application successfully builds and passes all tests. 
6. Create a pull request. 
