package za.customer.adapters;

import org.springframework.stereotype.Component;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.enums.Gender;

import java.security.InvalidParameterException;

@Component
public class CustomerAdapter {
    public CustomerDto convert(CustomerRequest request) throws InvalidParameterException {

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
    }

}
