package za.customer.adapters;

import org.springframework.stereotype.Component;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.enums.Gender;

import java.security.InvalidParameterException;

@Component
public class CustomerAdapter {
    public CustomerDto convert(CustomerRequest request) throws InvalidParameterException {

        return null;
    }

}
