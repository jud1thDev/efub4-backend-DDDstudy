package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Customer;
import ewhabackendDDDstudy.CH1.Money;
import ewhabackendDDDstudy.CH1.OrderLine;

import java.util.List;

public interface RuleDiscounter {
    Money applyRules(Customer customer, List<OrderLine> orderLines);
}
