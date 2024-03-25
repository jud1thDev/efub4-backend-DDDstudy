package ewhabackendDDDstudy.Repository;
import ewhabackendDDDstudy.order.command.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findById(String customerId);
}
