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
		if (users.findByEmail("alexanderschuster2020@gmail.com").isPresent()) {
			return;
		}

		users.save(new User("alexanderschuster2020@gmail.com", encoder.encode("123"), "Alexander", "Schuster", ADMIN_ROLE));
		users.save(new User("sebatianschuster2020@gmail.com", encoder.encode("321"), "Sebastian", "Schuster", CUSTOMER_ROLE));
		users.save(new User("test@web.de", encoder.encode("test"), "Frank","Müller", CUSTOMER_ROLE));
	}
}