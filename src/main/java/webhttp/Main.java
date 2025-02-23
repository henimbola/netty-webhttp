package webhttp;

import webhttp.controllers.MessageController;
import webhttp.library.Router;
import webhttp.library.WebHttpApplication;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Router.get("/", req -> new Message("Hello, World!"));

        Router.get("/message", req -> new Message("Hello, World!"));

        Router.post("/message", req -> new Message("Hello, World!"));

        Router.get("/routes", req -> Router.getRoutes());

        Router.get("/testmap", req -> Map.of(
                "message", "Hello, World!",
                "status", 200
        ));

        Router.get("/testmessageget", MessageController.class, "index");

        WebHttpApplication.run(9090);
    }
}