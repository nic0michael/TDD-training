package za.customer.service.impl;

import org.springframework.stereotype.Service;
import za.customer.dtos.SapRequest;
import za.customer.dtos.SapResponse;
import za.customer.service.SapService;

@Service
public class SapServiceImpl implements SapService {

    @Override
    public SapResponse sendToSap(SapRequest request) throws Exception {
        SapResponse response = new SapResponse();
        response.setMessage("Wot working yet");
        response.setStatus("432");
        return response;
    }
}
