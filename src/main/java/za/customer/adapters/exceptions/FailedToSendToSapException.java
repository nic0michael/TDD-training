package za.customer.adapters.exceptions;

public class FailedToSendToSapException extends Exception {
    String message;
    public FailedToSendToSapException() {
        super();
    }
    public FailedToSendToSapException(String message) {
        super(message);
        this.message = message;
    }
}
