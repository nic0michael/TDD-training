package za.customer.calculators;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;
import za.customer.adapters.CustomerAdapter;
import za.customer.dtos.CustomerDto;
import za.customer.dtos.CustomerRequest;
import za.customer.enums.Gender;

import java.math.RoundingMode;
import java.security.InvalidParameterException;

/**
  *  you need to use TDD to create this code
  */
@Component
public class TaxCalculator {
    private final CustomerAdapter adapter;

    public TaxCalculator(CustomerAdapter adapter) {
        this.adapter = adapter;
    }


    public CustomerDto calculateTax(CustomerRequest customerRequest) throws InvalidParameterException {
     return null;
    }

}
