package kickstart.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository <Customer, Long>{
	@Override
	Streamable<Customer> findAll();
}
