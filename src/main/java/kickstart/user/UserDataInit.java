package kickstart.user;

import org.salespointframework.core.DataInitializer;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(10)
public class UserDataInit implements DataInitializer {

	public static final String CUSTOMER_ROLE = "CUSTOMER";
	public static final String ADMIN_ROLE = "ADMIN";

	private final UserRepository users;
	private final BCryptPasswordEncoder encoder;

	UserDataInit(UserRepository users, BCryptPasswordEncoder encoder) {
		Assert.notNull(users, "UserRepository must not be null!");
		Assert.notNull(encoder, "BCryptPasswordEncoder must not be null!");

		this.users = users;
		this.encoder = encoder;
	}

	@Override
	public void initialize() {
		createUserIfNotExists("alexanderschuster2020@gmail.com", "123", "Alexander", "Schuster", ADMIN_ROLE);
		System.out.println("Initializing users Alex");

		createUserIfNotExists("sebatianschuster2020@gmail.com", "321", "Sebastian", "Schuster", CUSTOMER_ROLE);
		createUserIfNotExists("test@web.de", "test", "Frank", "Müller", CUSTOMER_ROLE);
	}

	private void createUserIfNotExists(String email, String password, String firstName, String lastName, String role) {
		if (users.findByEmail(email).isEmpty()) {
			users.save(new User(email, encoder.encode(password), firstName, lastName, role));
		}
	}
}
