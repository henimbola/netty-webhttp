package webhttp;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HttpRouter {
    private final List<HttpRoute> routes = new ArrayList<>();

    public Optional<Function<HttpRequest, Object>> getHandler(String path) {
        return routes.stream()
                .filter(route -> route.path().equals(path) && route.method() == HttpMethod.GET)
                .map(HttpRoute::handler)
                .findFirst();
    }

    public Optional<Function<HttpRequest, Object>> postHandler(String path) {
        return routes.stream()
                .filter(route -> route.path().equals(path) && route.method() == HttpMethod.POST)
                .map(HttpRoute::handler)
                .findFirst();
    }

    public void get(String path, Function<HttpRequest, Object> handler) {
        // get the return type of handler
        String outputType = ReflectionsUtil.getReturnTypeAsString(handler);

        routes.add(new HttpRoute(HttpMethod.GET, path, handler, null, outputType));
    }

    public void post(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.POST, path, handler, null, null));
    }

    public List<HttpRoute> getRoutes() {
        return routes;
    }
}
