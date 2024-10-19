package za.customer.business.layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import za.customer.adapters.SapRequestAdapter;
import za.customer.adapters.exceptions.FailedToSendToSapException;
import za.customer.calculators.TaxCalculator;
import za.customer.dtos.*;
import za.customer.service.SapService;

import java.security.InvalidParameterException;

@Component
public class CustomerBusinessProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CustomerBusinessProcessor.class);



    public final TaxCalculator taxCalculator;
    public final SapRequestAdapter adapter;
    public final SapService sapService;
    public CustomerBusinessProcessor( TaxCalculator taxCalculator, SapRequestAdapter adapter,SapService sapService ) {
        this.taxCalculator = taxCalculator;
        this.adapter = adapter;
        this.sapService = sapService;
    }

    public CustomerResponse process(CustomerRequest customerRequest) throws InvalidParameterException, FailedToSendToSapException {
        logger.info("Processing customer request: {}", customerRequest);
        CustomerResponse response = new CustomerResponse();

        try {
            CustomerDto dto = taxCalculator.calculateTax(customerRequest);
            SapRequest request = adapter.convert(dto);
            SapResponse sapResponse = sapService.sendToSap(request);
            logger.info("Received SAP response: {}", sapResponse);

            response.setStatus(Integer.parseInt(sapResponse.getStatus()));
            response.setMessage(sapResponse.getMessage());

        }catch (Exception e) {
            logger.error(e.getMessage());
            response.setStatus(500);
            response.setMessage("Exception caught calling SAP");
        }
        return response;
    }
}
