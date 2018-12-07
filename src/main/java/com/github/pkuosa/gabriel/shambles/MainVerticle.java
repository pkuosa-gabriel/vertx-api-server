package com.github.pkuosa.gabriel.shambles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.*;
import io.vertx.ext.web.*;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    router.route().handler(routingContext -> {
      // 所有的请求都会调用这个处理器处理
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain");

      // 写入响应并结束处理
      response.end("Hello World from Vert.x-Web!");
    });

    server.requestHandler(router::accept).listen(8080);
  }
}
