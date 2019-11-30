package calculator.server;

import com.proto.calculator.CalculatorRequest;
import com.proto.calculator.CalculatorResponse;
import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.Operation;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void operate(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        Operation operation = request.getOperation();
        float result = -1;
        switch (operation.getOperator()) {
            case SUM:
                result = operation.getOperand1() + operation.getOperand2();
                break;
            case DIFF:
                result = operation.getOperand1() > operation.getOperand2() ?
                        operation.getOperand1() - operation.getOperand2() :
                        operation.getOperand2() - operation.getOperand1();
                break;
            case MUL:
                result = operation.getOperand1() * operation.getOperand2();
                break;
            case DIVIDE:
                result = (float) operation.getOperand1() / operation.getOperand2();
                break;
        }
        CalculatorResponse calculatorResponse = CalculatorResponse
                .newBuilder()
                .setResult(result)
                .build();
        responseObserver.onNext(calculatorResponse);
        responseObserver.onCompleted();
//        super.operate(request, responseObserver);
    }
}
