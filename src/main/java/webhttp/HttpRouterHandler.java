// src/main/java/webhttp/RouterHandler.java
package webhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class HttpRouterHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final HttpRouter httpRouter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HttpRouterHandler(HttpRouter httpRouter) {
        this.httpRouter = httpRouter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            String uri = req.uri();

            Optional<Function<HttpRequest, Object>> handler = httpRouter.getHandler(uri);

            if (handler.isPresent()) {
                Object result = handler.get().apply(req);
                sendResponse(ctx, req, result);
            } else {
                handleNotFound(ctx, req);
            }
        }
    }

    private void handleNotFound(ChannelHandlerContext ctx, HttpRequest req) {
        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.NOT_FOUND,
                Unpooled.wrappedBuffer("Not Found".getBytes()));

        ctx.writeAndFlush(response);
    }

    private void sendResponse(ChannelHandlerContext ctx, HttpRequest req, Object dto) throws JsonProcessingException {
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
