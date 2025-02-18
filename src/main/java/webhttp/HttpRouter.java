// src/main/java/webhttp/Router.java
package webhttp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class HttpRouter {
    private final List<HttpRoute> routes = new ArrayList<>();

    public void addRoute(String path, BiConsumer<ChannelHandlerContext, HttpRequest> handler) {
        routes.add(new HttpRoute(path, handler));
    }

    public Optional<BiConsumer<ChannelHandlerContext, HttpRequest>> getHandler(String path) {
        return routes.stream()
                .filter(route -> route.getPath().equals(path))
                .map(HttpRoute::getHandler)
                .findFirst();
    }
}
