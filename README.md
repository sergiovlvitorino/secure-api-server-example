# Secure API Server Example
Example built with the intention show how to authenticate Restful API.

## Getting Started
Open the terminal. Put the commands below to download and start the project:
* $> git clone https://github.com/sergiovlvitorino/secure-api-server-example
* $> cd secure-api-server-example
* $> mvn package
* $> java -jar target/secureapiserverexample-1.0.0.jar

### Prerequisites
* JDK 1.8
* Maven 3

### Method's description
* (POST) - {url}/api/sign-up
* Params(JSON) : username:username_value, password:password_value

* (POST) - {url}/api/sign-in
* Params(JSON) : username:username_value, password:password_value

* (GET) - {url}/api/find/userId_value
* Headers : userId:userId_value, bearer:accessToken_value

### Running tests
Open the terminal. Put the commands below to test:
* $> cd secure-api-server-example
* $> mvn clean test

## Authors

* **Sergio Vitorino** - (https://github.com/sergiovlvitorino)

See also the list of [contributors](https://github.com/sergiovlvitorino/secure-api-server-example/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
