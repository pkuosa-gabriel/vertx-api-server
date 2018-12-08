package com.github.pkuosa.gabriel.shambles;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainVerticle extends AbstractVerticle {

  final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() throws Exception {
    var server = vertx.createHttpServer();

    var router = Router.router(vertx);

    router.route("/").handler(routingContext ->
      routingContext
        .response()
        .putHeader("content-type", "text/plain")
        .end("Hello World from Vert.x-Web!")
    );

    router.route("/about").handler(routingContext ->
      routingContext
        .response()
        .putHeader("content-type", "text/plain")
        .end("With best regards from Gabriel@PKUOSA!")
    );
    server
      .requestHandler(router)
      .rxListen(8080)
      .subscribe(httpServer -> logger.info("server is running..."));
  }
}
