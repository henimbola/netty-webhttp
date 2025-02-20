package webhttp.library;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import java.util.function.Function;

public record HttpRoute(
        HttpMethod method,
        String path,
        Function<HttpRequest, Object> handler,
        String inputType,
        String outputType
) {
}