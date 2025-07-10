# Lecturer Notes
## 1. Test1 CustomerAdapterTest 

    @Test
    public void testCustomerAdapter() {
        adapter = new CustomerAdapter();
        CustomerRequest request = makeCustomerRequest();
        CustomerDto dto = adapter.convert(request);

        Assertions.assertNotNull(dto);
    }


    private CustomerRequest makeCustomerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setAge("21");
        request.setName("John Doe");
        request.setCustGender(Gender.MALE.name());
        request.setIncome("1234.56");
        return request;
    }
---
### 1.1 Code 1 CustomerAdapter {
        if (request == null) {
            throw new InvalidParameterException("request is null");
        }
        CustomerDto dto = new CustomerDto();
        try {
            dto.setAge(Integer.parseInt(request.getAge()));
            dto.setIncome(Double.parseDouble(request.getIncome()));
            dto.setName(request.getName());
            dto.setCustomerIdentity(request.getCustomerIdentity());

            if("MALE".equalsIgnoreCase(request.getCustGender())) {
                dto.setCustGender(Gender.MALE);
            } else if("FEMALE".equalsIgnoreCase(request.getCustGender())) {
                dto.setCustGender(Gender.FEMALE);
            }

        }catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
        return dto;
---
## 2. Test 2 TaxCalculatorTest {
    /*
     * You need to use TDD to create this code
     */
    @Test
    public void testTaxCalculator() throws Exception {

    }

### 2.1 Code 2 Dirty

    @Test
    public void testTaxCalculator() throws Exception {
        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);
        double expectedIncomeTax =123.46;
        CustomerDto dto;
        CustomerRequest request = new CustomerRequest();
        request.setAge("21");
        request.setName("John Doe");
        request.setCustGender(Gender.MALE.name());
        request.setIncome("1234.56");
        dto = taxCalculator.calculateTax(request);
        Assertions.assertNotNull(dto);
        double incomeTax = dto.getIncomeTax();
        Assertions.assertEquals(incomeTax , expectedIncomeTax);
    }

### 2.2 Code 2 Refactored

    @Test
    public void testTaxCalculator() throws Exception {
        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);
        double expectedIncomeTax =123.46;
        CustomerDto dto;
        CustomerRequest request = makeCustomerRequest();
        dto = taxCalculator.calculateTax(request);
        Assertions.assertNotNull(dto);
        double incomeTax = dto.getIncomeTax();
        Assertions.assertEquals(incomeTax , expectedIncomeTax);
    }

    private CustomerRequest makeCustomerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setAge("21");
        request.setName("John Doe");
        request.setCustGender(Gender.MALE.name());
        request.setIncome("1234.56");
        return request;
    }
---
## 3. CustomerControllerMockClassTest BEHAVIOURAL TESTING METHODOLOGY
We Mock behaviour, making our Mock Class have Schizophrenic behaviour

### 3.1 Test1 PASSING TEST (The Good)
```java

package za.customer.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.customer.adapters.CustomerAdapter;
import za.customer.adapters.SapRequestAdapter;
import za.customer.business.layer.CustomerBusinessService;
import za.customer.calculators.TaxCalculator;
import za.customer.dtos.CustomerRequest;
import za.customer.dtos.CustomerResponse;
import za.customer.enums.Gender;
import za.customer.enums.TestType;
import za.customer.mock.MockSapServiceImpl;

@SpringBootTest
public class CustomerControllerMockClassTest {
    CustomerController customerController;
    CustomerBusinessService customerBusinessProcessor;
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
        customerBusinessProcessor = new CustomerBusinessService(taxCalculator, sapAdapter,sapService);
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

    }


```
---
### 3.2 Test 2 FAILING TEST (The Ugly)
```java

    @Test
    public void failingTest() throws Exception {
        String expectedMessage = "FAILED";
        int expectedStatus = 400;
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.FAILING);
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessService(taxCalculator, sapAdapter,sapService);
        customerController = new CustomerController(customerBusinessProcessor);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertNotNull(resp);
        String message = resp.getMessage();
        Assertions.assertEquals(expectedMessage, message);
        int status = resp.getStatus();
        Assertions.assertEquals(expectedStatus, status);

    }


```
---


### 3.2 Test 3 EXCEPTION THROWING TEST (The Bad)
```java

    @Test
    public void exceptionThrowingTest() throws Exception {
        String expectedMessage = "Exception caught calling SAP";
        int expectedStatus = 500;
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.THROWS_EXCEPTIONS);
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessService(taxCalculator, sapAdapter,sapService);
        customerController = new CustomerController(customerBusinessProcessor);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertNotNull(resp);
        String message = resp.getMessage();
        Assertions.assertEquals(expectedMessage, message);
        int status = resp.getStatus();
        Assertions.assertEquals(expectedStatus, status);

    }
```
---
