// src/main/java/webhttp/Route.java
package webhttp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class HttpRoute {
    private final String path;
    private final Function<HttpRequest, Object> handler;

    public HttpRoute(String path, Function<HttpRequest, Object> handler) {
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public Function<HttpRequest, Object> getHandler() {
        return handler;
    }
}
