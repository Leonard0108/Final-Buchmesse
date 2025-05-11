package kickstart.Customer;

import jakarta.servlet.http.HttpSession;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Transactional
public class CustomerService {

	public static final Role CUSTOMER_ROLE = Role.of("CUSTOMER");

	private final CustomerRepository customers;
	private final UserAccountManagement userAccounts;
	private final PasswordEncoder encoder;

	public CustomerService(CustomerRepository customers, UserAccountManagement userAccounts, PasswordEncoder encoder) {
		Assert.notNull(customers, "CustomerRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManagement must not be null!");
		Assert.notNull(encoder, "PasswordEncoder must not be null!");

		this.customers = customers;
		this.userAccounts = userAccounts;
		this.encoder = encoder;
	}

	public boolean registerCustomer(String email, String password, String forname, String surname) {
		/*Validation*/
		if (userAccounts.findByUsername(email).isPresent()) {
			return false; // Email already exists
		}

		var userAccount = userAccounts.create(email, UnencryptedPassword.of(password),email, CUSTOMER_ROLE);

		Customer customer = new Customer(userAccount, forname, surname);

		customers.save(customer);
		System.out.println("E-Mail: " + customer.getUserAccount().getEmail());
		System.out.println("Password: " + customer.getUserAccount().getPassword());
		return true;
	}

	public String loginCustomer(String email, String password) {
		Optional<UserAccount> userOpt = userAccounts.findByUsername(email);

		if (userOpt.isPresent()) {
			UserAccount user = userOpt.get();
			if (encoder.matches(password, String.valueOf(user.getPassword()))) {
				//Password matched the given Password
				return "success";
			}
			else {
				return "PasswordError";		//Password dont match
			}
		}
		return "EmailError";		//Email doesnt exist/not found
	}
}
