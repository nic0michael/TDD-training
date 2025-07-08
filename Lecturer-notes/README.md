# Lecturer Notes

## 1. CustomerAdapterTest
```java
    @Test
    public void testCustomerAdapter() {
//        String expectedName="John Doe";
//        String expectedGender=Gender.MALE.name();
        adapter = new CustomerAdapter();
        CustomerRequest request = makeCustomerRequest();
        CustomerDto dto = adapter.convert(request);

        Assertions.assertNotNull(dto);
//        Assert.hasText(dto.getName(),expectedName);
//        Assert.hasText(dto.getCustGender().name(),expectedGender);
    }
```
---

## 2. TaxCalculatorTest
## 2.1 TaxCalculatorTest First Test
```java
TaxCalculatorTest {
    private TaxCalculator taxCalculator; 

    /*
     * You need to use TDD to create this code
     */
    @Test
    public void testTaxCalculator() throws Exception {

        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);

        CustomerRequest request = makeCustomerRequest();



        double expectedIncomeTax =123.46;

        CustomerDto dto = taxCalculator.calculateTax(request);
        Assertions.assertNotNull(dto);

        double incomeTax = dto.getIncomeTax();
        Assertions.assertEquals(incomeTax , expectedIncomeTax);

    }


    @Test
    public void testTaxCalculator10k() throws Exception {
        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);
        double expectedIncomeTax =7407.38;
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

}
```
---

### 2.2 TaxCalculatorTest Second Test
```java
TaxCalculatorTest {
    private TaxCalculator taxCalculator; 

    /*
     * You need to use TDD to create this code
     */
    @Test
    public void testTaxCalculator() throws Exception {

        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);

        CustomerRequest request = makeCustomerRequest();



        double expectedIncomeTax =123.46;

        CustomerDto dto = taxCalculator.calculateTax(request);
        Assertions.assertNotNull(dto);

        double incomeTax = dto.getIncomeTax();
        Assertions.assertEquals(incomeTax , expectedIncomeTax);

    }


    @Test
    public void testTaxCalculator10k() throws Exception {
        CustomerAdapter adapter = new CustomerAdapter();
        taxCalculator = new TaxCalculator( adapter);
        double expectedIncomeTax =7407.38;
        CustomerDto dto;
        CustomerRequest request = makeCustomerRequest();
        request.setIncome("9876.5");
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

}
```
---
## 3. CustomerControllerMockClassTest
### 3.1 Test1
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
### 3.2 Test 2
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


### 3.2 Test 3
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
