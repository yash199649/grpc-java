package greeting.server;

import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String result = "Hello" + firstName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();
        // Send the response
        responseObserver.onNext(response);

        // Complete the rpc call
        responseObserver.onCompleted();
    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();
        String result = "";
        try{
            for(int i=0;i<20; i++){
                result =  "Hello " + firstName + "for iteration : " + i;
                GreetManyTimesResponse greetManyTimesResponse = GreetManyTimesResponse
                        .newBuilder()
                        .setResult(result)
                        .build();
                responseObserver.onNext(greetManyTimesResponse);
            }
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            responseObserver.onCompleted();
        }
    }

}
