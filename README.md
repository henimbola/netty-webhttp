# Web Server using Netty

This is a simple web server using Netty. It is a minimalistic implementation of a web server that can serve a REST API. This project is created for learning purposes and aims to provide a framework for creating web servers using Netty without relying on Jakarta EE or Spring.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Introduction

Netty is an asynchronous event-driven network application framework for rapid development of maintainable high-performance protocol servers and clients. This project aims to simplify the process of creating web servers using Netty by providing a framework inspired by the Laravel style of coding.

## Features
- HTTP Methods
  - [x] GET request
  - [x] POST request
  - [x] PUT request
  - [x] DELETE request
  - [x] PATCH request

- Routing
  - [x] Lambda-based routing
  - [x] Controller-based routing
  - [ ] Route groups
  - [ ] Route prefixes
  - [ ] Route naming
  - [ ] Resource routing

- Request Handling
  - [ ] URL parameters
  - [ ] Query parameters
  - [x] Request body parsing
  - [ ] File uploads
  - [ ] Form data handling
  - [ ] Request validation

- Response
  - [x] JSON responses
  - [ ] Response Class
  - [ ] File responses
  - [ ] Stream responses
  - [ ] Custom response headers
  - [ ] Response compression
  - [ ] Content negotiation

- Security
  - [ ] CORS support
  - [ ] Session management
  - [ ] Basic authentication
  - [ ] JWT authentication
  - [ ] Rate limiting
  - [ ] CSRF protection
  - [ ] XSS protection

- Middleware
  - [ ] Global middleware
  - [ ] Route middleware
  - [ ] Middleware groups
  - [ ] Error handling middleware

- Database
  - [ ] Database connection management
  - [ ] Basic ORM functionality
  - [ ] Query builder
  - [ ] Migrations
  - [ ] Model relationships

- Caching
  - [ ] In-memory cache
  - [ ] File cache
  - [ ] Redis integration
  - [ ] Cache tags
  - [ ] Cache invalidation

- Development Tools
  - [ ] Request logging
  - [ ] Performance monitoring
  - [ ] Hot reload
  - [ ] Configuration management
  - [ ] Environment management

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

### Controller Based Routing

Create a controller class:

```java
package webhttp.controller;

public class MessageController extends Controller {
    
    public Object index() {
        return new Message("Hello, World!");
    }
}
```

```java	
public class Main {
    public static void main(String[] args) throws Exception {
        // Controller routes
        Router.get("/messages", MessageController.class, "index");        
        WebHttpApplication.run(8080);
    }
}
```
