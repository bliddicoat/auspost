Auspost Digital Delivery Centre - Brendan Liddicoat
===================================================

## Getting Started

### Minimum requirements

1. Java 1.8 JDK

### Install using Git

1. Clone this repository to an appropriate location:
    ```bash
    $ git clone https://github.com/bliddicoat/auspost.git
    ```

### Build and deploy process instructions

1. Move into auspost directory by executing command:
    ```bash
    cd auspost
    ```

2. Execute the following in auspost directory to build project:
    ```bash
    ./gradlew build

    If you do not have permission then run command:  chmod +x gradlew
    ```
3. Execute the following to run jar:
    ```bash
    java -jar ./build/libs/auspost-0.0.1.jar
    ```

### API Endpoints Interface - Swagger

1. To view swagger API interface go to URL:
    ```bash
    http://localhost:8080/swagger-ui.html

    Change 'localhost' with AWS EC2 IP Address
    ```

### Security

The username is:  user

The password is randomly generated on startup and outputted to the console.
Example of password output in console:
```bash
Using default security password: ffac019d-d6fd-48ea-98d0-0d5aee225bb8
```

### Technology and Libraries

The following technologies and libraries were used to create the Australia Post API:
```bash
- Java 1.8
- Spring Boot
- JPA
- Spring Security
- H2
- Google Guava
- Gradle
- Tomcat
- JUnit
- Mockito
- PMD
- Swagger
```
