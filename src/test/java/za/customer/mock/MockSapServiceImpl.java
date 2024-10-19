package za.customer.mock;

import za.customer.adapters.exceptions.FailedToSendToSapException;
import za.customer.dtos.SapRequest;
import za.customer.dtos.SapResponse;
import za.customer.enums.TestType;
import za.customer.service.SapService;

public class MockSapServiceImpl implements SapService {
    public final TestType testType;
    public MockSapServiceImpl(TestType testType) {
        this.testType = testType;
    }

    private SapResponse sapResponse = null;


    @Override
    public SapResponse sendToSap(SapRequest request) throws Exception{

        String typOfTest = testType.toString();
        switch (typOfTest) {
            case "NORMAL" -> makeGoodSapResponse();
            case "FAILING" -> makeFailedSapResponse();
            case "THROWS_EXCEPTIONS" -> throw new FailedToSendToSapException("Failed to send to SAP");
            default -> throw new IllegalArgumentException("Unexpected test type: " + testType);

        }
        return sapResponse;
    }

    void makeGoodSapResponse(){
        sapResponse = new SapResponse();
        sapResponse.setStatus("200");
        sapResponse.setMessage("OK");
    }

    void makeFailedSapResponse(){
        sapResponse = new SapResponse();
        sapResponse.setStatus("400");
        sapResponse.setMessage("FAILED");
    }
}
