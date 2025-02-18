// src/main/java/webhttp/Route.java
package webhttp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.function.BiConsumer;

public class HttpRoute {
    private final String path;
    private final BiConsumer<ChannelHandlerContext, HttpRequest> handler;

    public HttpRoute(String path, BiConsumer<ChannelHandlerContext, HttpRequest> handler) {
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public BiConsumer<ChannelHandlerContext, HttpRequest> getHandler() {
        return handler;
    }
}
