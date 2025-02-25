package webhttp.library;

import java.util.Map;

public class Response {
    private Object body;
    private int status;
    private Map<String, String> headers;

    public Response(Object data, int i) {
        this.body = data;
        this.status = i;
    }

    public static Response ok(Object data) {
        return new Response(data, 200);
    }

    public static Response created(Object data) {
        return new Response(data, 201);
    }

    public static Response notFound(String message) {
        return new Response(Map.of("error", message), 404);
    }

    public int status() {
        return status;
    }

    public Object body() {
        return body;
    }
}