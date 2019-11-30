package greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello grpd");
        Server server = ServerBuilder
                .forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received shutdown hook");
            server.shutdown();
            System.out.println("Server shutdown");
        }));

        server.awaitTermination();
    }
}
