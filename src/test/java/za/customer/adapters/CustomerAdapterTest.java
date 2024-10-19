package za.customer.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.enums.Gender;

@SpringBootTest
public class CustomerAdapterTest {
    CustomerAdapter adapter;

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
}
