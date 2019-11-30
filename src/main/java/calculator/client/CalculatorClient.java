package calculator.client;

import com.proto.calculator.CalculatorRequest;
import com.proto.calculator.CalculatorResponse;
import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.Operation;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        System.out.println("Hi I am a rpc client");
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        System.out.println("Creating stub");

        CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorClient =
                CalculatorServiceGrpc.newBlockingStub(channel);
        // Prepare request
        Operation operation = Operation
                .newBuilder()
                .setOperator(Operation.Operator.SUM)
                .setOperand1(1)
                .setOperand2(2)
                .build();

        CalculatorRequest calculatorRequest = CalculatorRequest
                .newBuilder()
                .setOperation(operation)
                .build();

        CalculatorResponse response = calculatorClient.operate(calculatorRequest);
        System.out.println(response.getResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
