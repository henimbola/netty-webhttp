package webhttp.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.Optional;
import java.util.function.Function;

public class RouteHandlerDispatcher extends SimpleChannelInboundHandler<HttpObject> {

    private final Router httpRouter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RouteHandlerDispatcher(Router httpRouter) {
        this.httpRouter = httpRouter;
        SimpleModule module = new SimpleModule();
        module.addSerializer(HttpMethod.class, new HttpMethodSerializer());
        objectMapper.registerModule(module);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            return;
        }

        HttpRequest req = (HttpRequest) msg;
        HttpMethod method = req.method();
        String uri = req.uri();

        if (HttpMethod.GET.equals(method)) {
            handleGetRequest(ctx, req, uri);
        }

        if (HttpMethod.POST.equals(method) && msg instanceof FullHttpRequest) {
            handlePostRequest(ctx, req, uri);
        }

        // TODO : Add PUT, DELETE, PATCH
        if (HttpMethod.PUT.equals(method) && msg instanceof FullHttpRequest) {
            handlePutRequest(ctx, req, uri);
        }

        if (HttpMethod.DELETE.equals(method)) {
            handleDeleteRequest(ctx, req, uri);
        }

        if (HttpMethod.PATCH.equals(method) && msg instanceof FullHttpRequest) {
            handlePatchRequest(ctx, req, uri);
        }
    }

    private void handleGetRequest(ChannelHandlerContext ctx, HttpRequest req, String uri) throws JsonProcessingException {
        Optional<Function<HttpRequest, Object>> handler = httpRouter.getHandler(uri);

        if (handler.isPresent()) {
            Object result = handler.get().apply(req);
            sendResponse(ctx, req, result);
        } else {
            handleNotFound(ctx, req);
        }
    }

    private void handlePostRequest(ChannelHandlerContext ctx, HttpRequest req, String uri) throws JsonProcessingException {
        Optional<Function<HttpRequest, Object>> handler = httpRouter.postHandler(uri);

        if (handler.isPresent()) {
            Object result = handler.get().apply(req);
            sendResponse(ctx, req, result);
        } else {
            handleNotFound(ctx, req);
        }
    }


    private void handlePutRequest(ChannelHandlerContext ctx, HttpRequest req, String uri) throws JsonProcessingException {
        Optional<Function<HttpRequest, Object>> handler = httpRouter.putHandler(uri);

        if (handler.isPresent()) {
            Object result = handler.get().apply(req);
            sendResponse(ctx, req, result);
        } else {
            handleNotFound(ctx, req);
        }
    }

    private void handleDeleteRequest(ChannelHandlerContext ctx, HttpRequest req, String uri) throws JsonProcessingException {
        Optional<Function<HttpRequest, Object>> handler = httpRouter.deleteHandler(uri);

        if (handler.isPresent()) {
            Object result = handler.get().apply(req);
            sendResponse(ctx, req, result);
        } else {
            handleNotFound(ctx, req);
        }
    }

    private void handlePatchRequest(ChannelHandlerContext ctx, HttpRequest req, String uri) throws JsonProcessingException {
        Optional<Function<HttpRequest, Object>> handler = httpRouter.patchHandler(uri);

        if (handler.isPresent()) {
            Object result = handler.get().apply(req);
            sendResponse(ctx, req, result);
        } else {
            handleNotFound(ctx, req);
        }
    }

    private void handleNotFound(ChannelHandlerContext ctx, HttpRequest req) {
        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.NOT_FOUND,
                Unpooled.wrappedBuffer("Not Found".getBytes()));

        ctx.writeAndFlush(response);
    }

    private <T> void sendResponse(ChannelHandlerContext ctx, HttpRequest req, T dto) throws JsonProcessingException {
        if (dto instanceof Response) {
            Response response = (Response) dto;
            String responseJson;
            responseJson = objectMapper.writeValueAsString(response.body());
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.valueOf(response.status()),
                    Unpooled.wrappedBuffer(responseJson.getBytes()));
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());
            ctx.writeAndFlush(httpResponse);
            return;
        }

        String responseJson;
        responseJson = objectMapper.writeValueAsString(dto);

        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(responseJson.getBytes()));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
