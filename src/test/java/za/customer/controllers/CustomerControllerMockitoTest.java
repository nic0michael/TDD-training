package za.customer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.customer.business.layer.CustomerBusinessProcessor;
import za.customer.dtos.CustomerRequest;
import za.customer.dtos.CustomerResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerMockitoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private CustomerBusinessProcessor customerBusinessProcessor;


    @Test
    void testSendToSap() throws Exception {
        CustomerRequest request = makeCustomerRequest();
        CustomerResponse expectedCustomerResponse = makeExpectedCustomerResponse();

        when(customerBusinessProcessor.process(any())).thenReturn(expectedCustomerResponse);

        mockMvc.perform(post("/v1/sender/sendtosap")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCustomerResponse)));
    }

    private CustomerRequest makeCustomerRequest() {
        CustomerRequest request = new CustomerRequest();
        request.setCustomerIdentity("12345");
        request.setName("John Doe");
        request.setAge("30");
        request.setCustGender("Male");
        request.setIncome("50000");

        return request;
    }

    public CustomerResponse makeExpectedCustomerResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setMessage("Success");
        response.setStatus(200);
        return response;
    }
}
