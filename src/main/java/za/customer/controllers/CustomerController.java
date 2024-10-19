package za.customer.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.customer.business.layer.CustomerBusinessProcessor;
import za.customer.dtos.CustomerRequest;
import za.customer.dtos.CustomerResponse;

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
