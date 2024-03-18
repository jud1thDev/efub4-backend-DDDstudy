package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.domain.Customer;
import ewhabackendDDDstudy.domain.Money;
import ewhabackendDDDstudy.order.OrderLine;

import java.util.List;

public class CalculateDiscountService {
    private CustomerRepository customerRepository;
    private RuleDiscounter ruleDiscounter;

    public CalculateDiscountService(CustomerRepository customerRepository, RuleDiscounter ruleDiscounter) {
        this.customerRepository = customerRepository;
        this.ruleDiscounter = ruleDiscounter;
    }

    // DIP
    public Money calculateDiscount(List<OrderLine> orderLines, String customerId){
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLines);
    }

    private Customer findCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) throw new NoCustomerException();
        return customer;
    }
}
