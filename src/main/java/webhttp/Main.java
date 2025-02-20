package webhttp;

public class Main {
    public static void main(String[] args) throws Exception {
        Router.get("/", req -> new Message("Hello, World!"));
        Router.get("/message", req -> new Message("Hello, World!"));
        Router.post("/message", req -> new Message("Hello, World!"));
        Router.get("/routes", req -> HttpRouter.getRoutes());

        WebHttpApplication.run(8080);
    }
}