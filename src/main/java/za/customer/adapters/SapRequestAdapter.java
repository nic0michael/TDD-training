package za.customer.adapters;

import org.springframework.stereotype.Component;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.dtos.SapRequest;

import java.security.InvalidParameterException;

@Component
public class SapRequestAdapter {
    public SapRequest convert(CustomerDto dto) throws InvalidParameterException {
        if (dto == null) {
            throw new InvalidParameterException("dto is null");
        }

        SapRequest request = new SapRequest();
        request.setAge(String.valueOf(dto.getAge()));
        request.setIncome(String.valueOf(dto.getIncome()));
        request.setIncomeTax(String.valueOf(dto.getIncomeTax()));
        request.setName(dto.getName());
        request.setCustGender(dto.getCustGender().name());
        request.setCustomerIdentity(dto.getCustomerIdentity());

        return request;
    }
}
