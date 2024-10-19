package za.customer.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.customer.adapters.CustomerAdapter;
import za.customer.adapters.SapRequestAdapter;
import za.customer.business.layer.CustomerBusinessProcessor;
import za.customer.calculators.TaxCalculator;
import za.customer.dtos.CustomerRequest;
import za.customer.dtos.CustomerResponse;
import za.customer.enums.Gender;
import za.customer.enums.TestType;
import za.customer.mock.MockSapServiceImpl;

@SpringBootTest
public class CustomerControllerMockClassTest {
    CustomerController customerController;
    CustomerBusinessProcessor customerBusinessProcessor;
    TaxCalculator taxCalculator;
    SapRequestAdapter sapAdapter;
    CustomerAdapter adapter;
    CustomerRequest request = makeCustomerRequest();



    @Test
    public void positiveTest() throws Exception {
        String expectedMessage = "OK";
        int expectedStatus = 200;
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.NORMAL);
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessProcessor(taxCalculator, sapAdapter,sapService);
        customerController = new CustomerController(customerBusinessProcessor);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertNotNull(resp);
        String message = resp.getMessage();
        Assertions.assertEquals(expectedMessage, message);
        int status = resp.getStatus();
        Assertions.assertEquals(expectedStatus, status);

    }

    @Test
    public void failingTest() throws Exception {
        String expectedMessage = "FAILED";
        int expectedStatus = 400;
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.FAILING);
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessProcessor(taxCalculator, sapAdapter,sapService);
        customerController = new CustomerController(customerBusinessProcessor);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertNotNull(resp);
        String message = resp.getMessage();
        Assertions.assertEquals(expectedMessage, message);
        int status = resp.getStatus();
        Assertions.assertEquals(expectedStatus, status);

    }

    @Test
    public void exceptionThrowingTest() throws Exception {
        String expectedMessage = "Exception caught calling SAP";
        int expectedStatus = 500;
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.THROWS_EXCEPTIONS);
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessProcessor(taxCalculator, sapAdapter,sapService);
        customerController = new CustomerController(customerBusinessProcessor);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertNotNull(resp);
        String message = resp.getMessage();
        Assertions.assertEquals(expectedMessage, message);
        int status = resp.getStatus();
        Assertions.assertEquals(expectedStatus, status);

    }



    private CustomerRequest makeCustomerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setAge("21");
        request.setName("John Doe");
        request.setCustGender(Gender.MALE.name());
        request.setIncome("1234.56");
        return request;
    }
}
