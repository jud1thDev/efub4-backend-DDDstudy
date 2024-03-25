package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.order.command.domain.Customer;
import ewhabackendDDDstudy.order.command.domain.Money;
import ewhabackendDDDstudy.order.command.domain.OrderLine;

import java.util.List;

public interface RuleDiscounter {
    Money applyRules(Customer customer, List<OrderLine> orderLines);
}
