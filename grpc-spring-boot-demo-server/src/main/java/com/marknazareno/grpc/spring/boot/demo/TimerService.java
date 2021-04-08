package com.marknazareno.grpc.spring.boot.demo;

import io.grpc.examples.helloworld.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Server Stream Example
 *
 * @author allenq
 * @create 2021/4/8 9:02 上午
 * @modify
 */
@GRpcService
public class TimerService extends StreamTimerGrpc.StreamTimerImplBase {

    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void tick(TickRequest request, StreamObserver<TockReply> responseObserver) {

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(3l);
                StringBuilder sb = new StringBuilder();
                sb.append("[").append(atomicInteger.getAndIncrement()).append("]")
                        .append(request.getName()).append(" time is ")
                        .append(System.currentTimeMillis());
                TockReply reply = TockReply.newBuilder().setMessage(sb.toString()).build();
                responseObserver.onNext(reply);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (atomicInteger.get() == 10) {
                break;
            }
        }

        responseObserver.onCompleted();
    }
}
