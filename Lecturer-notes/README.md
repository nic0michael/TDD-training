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
## 2. TaxCalculatorTest First Test
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

## 2. TaxCalculatorTest Second Test
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
## 

```
---
