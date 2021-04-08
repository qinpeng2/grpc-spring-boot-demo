package com.marknazareno.grpc.spring.boot.demo;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author allenq
 * @create 2021/4/8 9:02 上午
 * @modify
 */
@GRpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
