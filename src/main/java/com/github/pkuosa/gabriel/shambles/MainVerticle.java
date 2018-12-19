package com.github.pkuosa.gabriel.shambles;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() throws RuntimeException {
    var server = vertx.createHttpServer();
    var router = Router.router(vertx);
    var port = System.getProperty("port", "8080");

    router.route().handler(BodyHandler.create());

    router.get("/").handler(routingContext -> {
      logger.info("Got a request on /");
      routingContext
        .response()
        .putHeader("content-type", "text/plain")
        .end("Hello World from Vert.x-Web!");
    });

    router.get("/about").handler(routingContext -> {
      logger.info("Got a request on /about");
      routingContext
        .response()
        .putHeader("content-type", "text/plain")
        .end("With best regards from Gabriel@PKUOSA!");
    });

    router.post("/multipart").handler(routingContext -> {
      logger.info("Got a request on /multipart");
      var params = routingContext.request().formAttributes();
      routingContext
        .response()
        .putHeader("content-type", "text/plain")
        .end("Hello, " + (params.get("name") != null? params.get("name") : "Nobody"));
    });

    server
      .requestHandler(router)
      .rxListen(Integer.parseInt(port))
      .subscribe(
        httpServer -> logger.info("server is running on localhost:" + port),
        httpServer -> {
          logger.error("server cannot start");
          throw new RuntimeException("server cannot start");
        });
  }
}
