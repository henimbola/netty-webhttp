package webhttp.library;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.lang.reflect.Method;

public class Router {
    private static final List<HttpRoute> routes = new ArrayList<>();

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

    public static List<HttpRoute> getRoutes() {
        return routes;
    }

    public Optional<Function<HttpRequest, Object>> putHandler(String uri) {
        return routes.stream()
                .filter(route -> route.path().equals(uri) && route.method() == HttpMethod.PUT)
                .map(HttpRoute::handler)
                .findFirst();
    }

    public Optional<Function<HttpRequest, Object>> deleteHandler(String uri) {
        return routes.stream()
                .filter(route -> route.path().equals(uri) && route.method() == HttpMethod.DELETE)
                .map(HttpRoute::handler)
                .findFirst();
    }

    public Optional<Function<HttpRequest, Object>> patchHandler(String uri) {
        return routes.stream()
                .filter(route -> route.path().equals(uri) && route.method() == HttpMethod.PATCH)
                .map(HttpRoute::handler)
                .findFirst();
    }

    public static void get(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.GET, path, handler));
    }

    public static void post(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.POST, path, handler));
    }

    public static void put(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.PUT, path, handler));
    }

    public static void delete(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.DELETE, path, handler));
    }

    public static void patch(String path, Function<HttpRequest, Object> handler) {
        routes.add(new HttpRoute(HttpMethod.PATCH, path, handler));
    }

    public static void get(String path, Class<? extends Controller> controllerClass, String methodName) {
        routes.add(new HttpRoute(HttpMethod.GET, path, request -> {
            try {
                Controller controller = controllerClass.getDeclaredConstructor().newInstance();
                controller.setRequest(request);
                Method method = controllerClass.getDeclaredMethod(methodName);
                return method.invoke(controller);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller method", e);
            }
        }));
    }

    // Similar methods for post, put, delete, patch
    public static void post(String path, Class<? extends Controller> controllerClass, String methodName) {
        routes.add(new HttpRoute(HttpMethod.POST, path, request -> {
            try {
                Controller controller = controllerClass.getDeclaredConstructor().newInstance();
                controller.setRequest(request);
                Method method = controllerClass.getDeclaredMethod(methodName);
                return method.invoke(controller);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller method", e);
            }
        }));
    }

    public static void put(String path, Class<? extends Controller> controllerClass, String methodName) {
        routes.add(new HttpRoute(HttpMethod.PUT, path, request -> {
            try {
                Controller controller = controllerClass.getDeclaredConstructor().newInstance();
                controller.setRequest(request);
                Method method = controllerClass.getDeclaredMethod(methodName);
                return method.invoke(controller);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller method", e);
            }
        }));
    }

    public static void delete(String path, Class<? extends Controller> controllerClass, String methodName) {
        routes.add(new HttpRoute(HttpMethod.DELETE, path, request -> {
            try {
                Controller controller = controllerClass.getDeclaredConstructor().newInstance();
                controller.setRequest(request);
                Method method = controllerClass.getDeclaredMethod(methodName);
                return method.invoke(controller);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller method", e);
            }
        }));
    }

    public static void patch(String path, Class<? extends Controller> controllerClass, String methodName) {
        routes.add(new HttpRoute(HttpMethod.PATCH, path, request -> {
            try {
                Controller controller = controllerClass.getDeclaredConstructor().newInstance();
                controller.setRequest(request);
                Method method = controllerClass.getDeclaredMethod(methodName);
                return method.invoke(controller);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller method", e);
            }
        }));
    }
}
