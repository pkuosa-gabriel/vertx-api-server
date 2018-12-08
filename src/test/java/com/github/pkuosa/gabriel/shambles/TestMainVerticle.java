package com.github.pkuosa.gabriel.shambles;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    RxHelper
      .deployVerticle(vertx, new MainVerticle())
      .subscribe(id -> testContext.completeNow(), testContext::failNow);
  }

  @Test
  @DisplayName("🚀 Test the / route.")
  @Timeout(value = 1, timeUnit = TimeUnit.SECONDS)
  void test_root(Vertx vertx, VertxTestContext testContext) {
    var checkpoints = testContext.checkpoint(10);
    var client = WebClient.create(vertx);
    var request =
      client.get(8080, "localhost", "/").as(BodyCodec.string());

    request
      .rxSend()
      .repeat(10)
      .subscribe(
        response ->
          testContext.verify(
            () -> {
              assertTrue(response.statusCode() == 200);
              assertTrue(response.body().contains("Hello World from Vert.x-Web!"));
              checkpoints.flag();
            }),
        testContext::failNow);
  }

  @Test
  @DisplayName("🚀 Test the /about route.")
  @Timeout(value = 1, timeUnit = TimeUnit.SECONDS)
  void test_about(Vertx vertx, VertxTestContext testContext) {
    var checkpoints = testContext.checkpoint(10);
    var client = WebClient.create(vertx);
    var request =
      client.get(8080, "localhost", "/about").as(BodyCodec.string());

    request
      .rxSend()
      .repeat(10)
      .subscribe(
        response ->
          testContext.verify(
            () -> {
              assertTrue(response.statusCode() == 200);
              assertTrue(response.body().contains("With best regards from Gabriel@PKUOSA!"));
              checkpoints.flag();
            }),
        testContext::failNow);
  }
}
