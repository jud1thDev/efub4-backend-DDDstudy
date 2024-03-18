package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.domain.Customer;

public interface CustomerRepository {
    public Customer findById(String customerId) {
    }
}
