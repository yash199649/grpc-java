package greeting.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    ManagedChannel channel;
    void run(){
        System.out.println("Hi I am a rpc client");
        channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
//        performUnaryCommunication(channel);
        performServerSideStreamingCommunication(channel);
    }

    private void performServerSideStreamingCommunication(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceBlockingStub greetClient =
                GreetServiceGrpc.newBlockingStub(channel);
        GreetManyTimesRequest greetRequest = GreetManyTimesRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("yash").build())
                .build();
        greetClient.greetManyTimes(greetRequest).forEachRemaining(greetManyTimesResponse -> {
            System.out.println("Response : " + greetManyTimesResponse.getResult());
        });
        channel.shutdown();
    }

    void performUnaryCommunication(ManagedChannel channel){
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Prepare request
        Greeting greeting = Greeting
                .newBuilder()
                .setFirstName("Yash")
                .setLastName("Sharma")
                .build();

        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting).build();

        GreetResponse response = greetClient.greet(greetRequest);
        System.out.println(response.getResult());
        channel.shutdown();
    }
    public static void main(String[] args) {
        GreetingClient greetingClient = new GreetingClient();
        greetingClient.run();

    }
}
