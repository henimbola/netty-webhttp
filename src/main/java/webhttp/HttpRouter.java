// src/main/java/webhttp/Router.java
package webhttp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HttpRouter {
    private final List<HttpRoute> routes = new ArrayList<>();

    public Optional<Function<HttpRequest, Object>> getHandler(String path) {
        return routes.stream()
                .filter(route -> route.getPath().equals(path))
                .map(HttpRoute::getHandler)
                .findFirst();
    }

    public void get(String s, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(s, handler));
    }
}
