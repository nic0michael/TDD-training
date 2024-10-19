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

@Component
public class TaxCalculator {
    private final CustomerAdapter adapter;

    public TaxCalculator(CustomerAdapter adapter) {
        this.adapter = adapter;
    }


    public CustomerDto calculateTax(CustomerRequest customerRequest) throws InvalidParameterException {

        CustomerDto dto;
        try {
            dto = adapter.convert(customerRequest);
            int age = dto.getAge();
            Gender gender = dto.getCustGender();
            double income = dto.getIncome();
            double taxFactor;
            double incomeTax;
            taxFactor = basicTaxBusinessRule(income);
            taxFactor = taxFactor * ageBusinessRule(age);
            taxFactor = taxFactor * genderBusinessRule(gender);
            incomeTax = income * taxFactor;

            BigDecimal bd = BigDecimal.valueOf(incomeTax);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            incomeTax = bd.doubleValue();

            dto.setIncomeTax(incomeTax);

        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }

        return dto;
    }


    private double basicTaxBusinessRule(double income) throws InvalidParameterException {
        double theTaxFactor = 0;

        if (income < 2000) {
            theTaxFactor = 0.1;
        } else if (income < 10000) {
            theTaxFactor = 0.75;
        } else if (income < 50000) {
            theTaxFactor = 0.80;
        } else{
            theTaxFactor = 0.90;
        }
        return theTaxFactor;
    }

    private double ageBusinessRule(int age) throws InvalidParameterException {
        double theTaxFactor = 1.0;

        if (age < 0) {
            throw new InvalidParameterException("age is negative");
        } else if (age > 70) {
            theTaxFactor = 0.75;
        } else if (age > 65) {
            theTaxFactor = 0.80;
        }
        return theTaxFactor;
    }

    private double genderBusinessRule(Gender gender) throws InvalidParameterException {
        double theTaxFactor = 1.0;

        if (gender == Gender.MALE) {
            theTaxFactor = 1.0;
        } else if (gender == Gender.FEMALE) {
            theTaxFactor = 0.80;
        } else{
            throw new InvalidParameterException("gender is not valid");
        }
        return theTaxFactor;
    }


}
