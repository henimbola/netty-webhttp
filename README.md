# Web server using Netty

This is a simple web server using Netty. It is a simple implementation of a web server that can serve a minimalistic rest api.
This is a project created for learning purpose.

I'd like to create a little framework from this project to make it easier to create a web server using Netty.

Specifications will be added over time. And we will try to improve this and make it become a library that can be used by anyone.
The goal is also to improve the developer experience of Java developers when creating a web server. We want a web server that can work without Jakarta EE and Spring.

I am inspired by the Laravel style of coding and that is how I want to create this library. To copy the Laravel style of coding.

## Features
- [x] Get request
- [x] Post request
- [ ] Handle routes from a class that extends a super class Controller
- [ ] Handle routes from a class that has a method annotated with @Route
- [ ] Handle url parameters
- [ ] Handle query parameters
- [ ] Middlewares
- [ ] Security : Session
- [ ] Security : Basic Auth
- [ ] Security : JWT
- [ ] Security : CORS
- [ ] Endpoint registering