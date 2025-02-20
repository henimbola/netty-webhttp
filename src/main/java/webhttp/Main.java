package webhttp;

import webhttp.library.HttpRouter;
import webhttp.library.Router;
import webhttp.library.WebHttpApplication;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Router.get("/", req -> new Message("Hello, World!"));

        Router.get("/message", req -> new Message("Hello, World!"));

        Router.post("/message", req -> new Message("Hello, World!"));

        Router.get("/routes", req -> HttpRouter.getRoutes());

        Router.get("/testmap", req -> Map.of(
                "message", "Hello, World!",
                "status", 200
        ));

        WebHttpApplication.run(8080);
    }
}