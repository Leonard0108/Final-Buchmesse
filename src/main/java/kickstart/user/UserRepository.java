package kickstart.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface UserRepository extends CrudRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	@Override
	Streamable<User> findAll();
}