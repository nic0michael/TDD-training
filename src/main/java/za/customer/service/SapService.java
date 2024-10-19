package za.customer.service;

import za.customer.dtos.SapRequest;
import za.customer.dtos.SapResponse;

public interface SapService {

    SapResponse sendToSap(SapRequest request) throws Exception;
}
