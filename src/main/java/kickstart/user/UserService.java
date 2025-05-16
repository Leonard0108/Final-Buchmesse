package kickstart.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {

	public static final String CUSTOMER_ROLE = "CUSTOMER";
	private final UserRepository users;
	private final BCryptPasswordEncoder encoder;

	public UserService(BCryptPasswordEncoder encoder, UserRepository users) {
		Assert.notNull(encoder, "BCryptPasswordEncoder must not be null!");
		Assert.notNull(users, "UserRepository must not be null!");

		this.users = users;
		this.encoder = encoder;
	}

	public boolean registerUser(String email, String password, String forname, String surname) {
		/*Validation*/
		if (users.findByEmail(email).isPresent()) {
			return false; // Email already exists
		}

		User newUser = new User(email, encoder.encode(password), forname, surname, CUSTOMER_ROLE);
		users.save(newUser);
		return true;
	}

	public String loginUser(String email, String password, HttpServletRequest request) {
		Optional<User> userOpt = users.findByEmail(email);

		if (userOpt.isPresent()) {
			User user = userOpt.get();

			if (encoder.matches(password, user.getPassword())) {
				//Password matched the given Password
				String role = user.getRole();
				List<SimpleGrantedAuthority> authorities =
						List.of(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken auth =
						new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

				SecurityContextHolder.getContext().setAuthentication(auth);
				HttpSession session = request.getSession();
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
				return role;
			}
			else {
				return "PasswordError";		//Password dont match
			}
		}
		return "EmailError";		//Email doesnt exist/not found
	}
}
