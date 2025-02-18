// src/main/java/webhttp/WebHttpApplication.java
package webhttp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WebHttpApplication {

    private static final int PORT = 8080;

    public static void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            HttpRouter router = new HttpRouter();
            initializeRoutes(router);

            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(io.netty.channel.socket.nio.NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer(null, router));

            Channel ch = b.bind(PORT).sync().channel();
            System.out.println("Your server is available at http://localhost:" + PORT + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static void initializeRoutes(HttpRouter router) {
        router.get("/", req -> new Message("Hello, World!"));
        router.get("/message", req -> new Message("Hello, World!"));
    }
}