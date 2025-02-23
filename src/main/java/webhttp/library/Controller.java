package webhttp.library;

import io.netty.handler.codec.http.HttpRequest;

public abstract class Controller {
    protected HttpRequest request;

    public void setRequest(HttpRequest request) {
        this.request = request;
    }
}