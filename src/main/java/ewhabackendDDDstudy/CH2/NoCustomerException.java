package ewhabackendDDDstudy.CH2;

public class NoCustomerException extends RuntimeException {

    public NoCustomerException() {
        super("Customer not found");
    }

}

