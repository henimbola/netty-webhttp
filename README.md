# Web Server using Netty

This is a simple web server using Netty. It is a minimalistic implementation of a web server that can serve a REST API. This project is created for learning purposes and aims to provide a framework for creating web servers using Netty without relying on Jakarta EE or Spring.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contribution](#contribution)
- [License](#license)

## Introduction

Netty is an asynchronous event-driven network application framework for rapid development of maintainable high-performance protocol servers and clients. This project aims to simplify the process of creating web servers using Netty by providing a framework inspired by the Laravel style of coding.

## Features
- [x] GET request
- [x] POST request
- [ ] Handle routes from a class that extends a superclass `Controller`
- [ ] Handle routes from a class that has a method annotated with `@Route`
- [ ] Handle URL parameters
- [ ] Handle query parameters
- [ ] Middlewares
- [ ] Security: Session
- [ ] Security: Basic Auth
- [ ] Security: JWT
- [ ] Security: CORS
- [ ] Endpoint registering

## Installation

To set up the project, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/henimbola/netty-webhttp.git
    cd netty-webhttp
    ```

2. Build the project using Gradle:
    ```sh
    ./gradlew build
    ```

## Usage

Here are some examples of how to use the web server:

### Define Routes

```java
package webhttp;

import webhttp.library.Router;

public class Main {
    public static void main(String[] args) throws Exception {
        Router.get("/", req -> new Message("Hello, World!"));
        Router.get("/message", req -> new Message("Hello, World!"));
        Router.post("/message", req -> new Message("Hello, World!"));
        Router.get("/routes", req -> HttpRouter.getRoutes());
        Router.get("/testmap", req -> Map.of("message", "Hello, World!", "status", 200));

        WebHttpApplication.run(8080);
    }
}
```