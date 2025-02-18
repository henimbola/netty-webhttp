// src/main/java/webhttp/RouterHandler.java
package webhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.Optional;
import java.util.function.BiConsumer;

public class HttpRouterHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final HttpRouter httpRouter;

    public HttpRouterHandler(HttpRouter httpRouter) {
        this.httpRouter = httpRouter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            String uri = req.uri();

            Optional<BiConsumer<ChannelHandlerContext, HttpRequest>> handler = httpRouter.getHandler(uri);

            if (handler.isPresent()) {
                handler.get().accept(ctx, req);
            } else {
                handleNotFound(ctx, req);
            }
        }
    }

    private void handleNotFound(ChannelHandlerContext ctx, HttpRequest req) {
        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.NOT_FOUND,
                Unpooled.wrappedBuffer("404 Not Found".getBytes()));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
