package za.customer.calculators;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import za.customer.adapters.CustomerAdapter;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.enums.Gender;

import java.security.InvalidParameterException;

@SpringBootTest
public class TaxCalculatorTest {
    private TaxCalculator taxCalculator;

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
}
