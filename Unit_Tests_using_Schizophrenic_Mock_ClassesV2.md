# Unit Tests Using Multi-Behavior (Schizophrenic) Mock Classes

We have created a project in GitHub with this code:
**[https://github.com/nic0michael/TDD-training]()**

In this project, we will demonstrate how to use a **multi-behavior mock class** (which we previously coined the phrase *“schizophrenic mock class”*) to improve unit testing.

---

## 1 Motivation

Before diving into code, let’s ask:

* Why invent a “multi-behavior mock class” when frameworks like Mockito already exist?
* Answer: Because a self-contained mock class can encapsulate **different behaviors** (success, failure, exception) in one place, making tests more **predictable, reusable, and easy to maintain**.
* This avoids scattering `when(...).thenReturn(...)` or `when(...).thenThrow(...)` across tests.

---

## 2 Classes in Our Project

### 2.1 The Controller Class

Path: `/src/main/java/za/customer/controllers/CustomerController.java`

```java
@RestController
@RequestMapping("/v1/sender")
public class CustomerController {

    public final CustomerBusinessProcessor customerBusinessProcessor;

    public CustomerController(CustomerBusinessProcessor customerBusinessProcessor) {
        this.customerBusinessProcessor = customerBusinessProcessor;
    }

    @PostMapping("/sendtosap")
    CustomerResponse sendToSap(@RequestBody CustomerRequest request) throws Exception {
        return customerBusinessProcessor.process(request);
    }
}
```

---

### 2.2 The Service Interface

Path: `/src/main/java/za/customer/service/SapService.java`

```java
public interface SapService {
    SapResponse sendToSap(SapRequest request) throws Exception;
}
```

---

### 2.3 The Service Class (Stub Implementation)

Path: `/src/main/java/za/customer/service/impl/SapServiceImpl.java`

```java
/**
 * Temporary stub implementation of SapService.
 * Used for unit testing and local development before
 * actual SAP integration is implemented.
 */
@Service
public class SapServiceImpl implements SapService {

    @Override
    public SapResponse sendToSap(SapRequest request) {
        SapResponse response = new SapResponse();
        response.setMessage("SAP integration not implemented yet");
        response.setStatus("501"); // HTTP 501 Not Implemented
        return response;
    }
}
```

---

## 3 Creating the Multi-Behavior Mock Class

We now create a mock implementation that supports **three behaviors**:

* **NORMAL** → returns a valid response.
* **FAILING** → returns an invalid response.
* **THROWS\_EXCEPTIONS** → throws an exception.

Path: `/src/test/java/za/customer/mock/MockSapServiceImpl.java`

### 3.1 Supporting Enum

```java
public enum TestType {
    NORMAL, FAILING, THROWS_EXCEPTIONS
}
```

### 3.2 Mock Class

```java
public class MockSapServiceImpl implements SapService {
    public final TestType testType;

    public MockSapServiceImpl(TestType testType) {
        this.testType = testType;
    }

    private SapResponse sapResponse = null;

    @Override
    public SapResponse sendToSap(SapRequest request) throws Exception {
        switch (testType) {
            case NORMAL -> makeGoodSapResponse();
            case FAILING -> makeFailedSapResponse();
            case THROWS_EXCEPTIONS -> throw new FailedToSendToSapException("Failed to send to SAP");
            default -> throw new IllegalArgumentException("Unexpected test type: " + testType);
        }
        return sapResponse;
    }

    void makeGoodSapResponse() {
        sapResponse = new SapResponse();
        sapResponse.setStatus("200");
        sapResponse.setMessage("OK");
    }

    void makeFailedSapResponse() {
        sapResponse = new SapResponse();
        sapResponse.setStatus("400");
        sapResponse.setMessage("FAILED");
    }
}
```

---

## 4 Decoupling with a Business Logic Class

Instead of tightly coupling controllers to services, we use a **business processor** class.
This ensures that:

1. Controllers remain thin (no value-changing logic).
2. Service classes focus on infrastructure.
3. Business logic sits in one place and can be easily tested.

Path: `/src/main/java/za/customer/business/layer/CustomerBusinessProcessor.java`

```java
@Component
public class CustomerBusinessProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CustomerBusinessProcessor.class);

    public final TaxCalculator taxCalculator;
    public final SapRequestAdapter adapter;
    public final SapService sapService;

    public CustomerBusinessProcessor(TaxCalculator taxCalculator, SapRequestAdapter adapter, SapService sapService) {
        this.taxCalculator = taxCalculator;
        this.adapter = adapter;
        this.sapService = sapService;
    }

    public CustomerResponse process(CustomerRequest customerRequest) {
        logger.info("Processing customer request: {}", customerRequest);
        CustomerResponse response = new CustomerResponse();

        try {
            CustomerDto dto = taxCalculator.calculateTax(customerRequest);
            SapRequest request = adapter.convert(dto);
            SapResponse sapResponse = sapService.sendToSap(request);
            logger.info("Received SAP response: {}", sapResponse);

            response.setStatus(Integer.parseInt(sapResponse.getStatus()));
            response.setMessage(sapResponse.getMessage());

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setStatus(500);
            response.setMessage("Exception caught calling SAP");
        }
        return response;
    }
}
```

---

## 5 Unit Tests with the Mock Class

Path: `/src/test/java/za/customer/controllers/CustomerControllerMockClassTest.java`

We test three scenarios:

1. **Positive (Normal Response)**
2. **Failing Response**
3. **Exception Handling**

```java
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
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.NORMAL);
        setupProcessor(sapService);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertEquals("OK", resp.getMessage());
        Assertions.assertEquals(200, resp.getStatus());
    }

    @Test
    public void failingTest() throws Exception {
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.FAILING);
        setupProcessor(sapService);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertEquals("FAILED", resp.getMessage());
        Assertions.assertEquals(400, resp.getStatus());
    }

    @Test
    public void exceptionThrowingTest() throws Exception {
        MockSapServiceImpl sapService = new MockSapServiceImpl(TestType.THROWS_EXCEPTIONS);
        setupProcessor(sapService);

        CustomerResponse resp = customerController.sendToSap(request);
        Assertions.assertEquals("Exception caught calling SAP", resp.getMessage());
        Assertions.assertEquals(500, resp.getStatus());
    }

    private void setupProcessor(SapService sapService) {
        adapter = new CustomerAdapter();
        sapAdapter = new SapRequestAdapter();
        taxCalculator = new TaxCalculator(adapter);
        customerBusinessProcessor = new CustomerBusinessProcessor(taxCalculator, sapAdapter, sapService);
        customerController = new CustomerController(customerBusinessProcessor);
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

## 6 Behavior Comparison Table

| TestType           | Behavior                 | Status | Message                      |
| ------------------ | ------------------------ | ------ | ---------------------------- |
| NORMAL             | Returns valid response   | 200    | OK                           |
| FAILING            | Returns invalid response | 400    | FAILED                       |
| THROWS\_EXCEPTIONS | Throws exception         | 500    | Exception caught calling SAP |

---

## 7 Key Takeaways

* Keep controllers and services **decoupled** by introducing a business logic layer.
* Use a **multi-behavior mock class** to centralize mock behavior, making unit tests cleaner.
* This approach complements (not replaces) tools like Mockito — it’s especially helpful for training, demos, or when you want predictable test doubles.

---
