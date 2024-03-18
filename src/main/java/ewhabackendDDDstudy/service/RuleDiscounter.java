package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.domain.Customer;
import ewhabackendDDDstudy.domain.Money;
import ewhabackendDDDstudy.order.OrderLine;

import java.util.List;

public interface RuleDiscounter {
    Money applyRules(Customer customer, List<OrderLine> orderLines);
}
