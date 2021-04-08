package com.marknazareno.grpc.spring.boot.demo;

import com.google.common.base.Strings;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Optional;

/**
 * Stream server
 * Created by mark on 11/3/17.
 */
@SpringBootApplication
public class TimerClient {

  public static void main(String[] args) {
    SpringApplication.run(TimerClient.class, args);
  }

  @Component
  public static class TimerClientRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TimerClientRunner.class);

    @Value("${host:localhost}")
    private String host;

    @Value("${port:6565}")
    private int port;

    public void run(String... strings) throws Exception {
      String name = Optional.ofNullable(strings).filter(a -> a.length > 0).map(a -> a[0]).filter(a -> !Strings.isNullOrEmpty(a)).orElse("Stream");
      ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

      StreamTimerGrpc.StreamTimerBlockingStub stub = StreamTimerGrpc.newBlockingStub(channel);

      Iterator<TockReply> iterator = stub.tick(TickRequest.newBuilder().setName(name).build());

      while (iterator.hasNext()) {
        TockReply reply = iterator.next();
        LOG.info(reply.toString());
      }

      channel.shutdownNow();
    }
  }
}
