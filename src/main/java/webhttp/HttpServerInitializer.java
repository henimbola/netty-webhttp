package webhttp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslContext;
    private final HttpRouter router;

    public HttpServerInitializer(SslContext sslContext, HttpRouter router) {
        this.sslContext = sslContext;
        this.router = router;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        if (sslContext != null) {
            pipeline.addLast(sslContext.newHandler(socketChannel.alloc()));
        }

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpContentCompressor());
        pipeline.addLast(new HttpServerExpectContinueHandler());
        pipeline.addLast(new HttpRouterHandler(router));
    }
}
