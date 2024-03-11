package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Customer;

public interface CustomerRepository {
    public Customer findById(String customerId) {
    }
}
