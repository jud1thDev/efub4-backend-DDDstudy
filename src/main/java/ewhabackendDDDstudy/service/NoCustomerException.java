package ewhabackendDDDstudy.service;

public class NoCustomerException extends RuntimeException {

    public NoCustomerException() {
        super("Customer not found");
    }

}

