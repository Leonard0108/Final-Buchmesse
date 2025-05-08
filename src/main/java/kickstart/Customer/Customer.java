package kickstart.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private Long id;

	private String forname;
	private String surname;

	@OneToOne
	private UserAccount userAccount;

	private Customer(){}

	public Customer(UserAccount userAccount, String forname, String surname){
		this.userAccount = userAccount;
		this.forname = forname;
		this.surname = surname;
	}

	public String getForname() {
		return forname;
	}

	public String getSurname() {
		return surname;
	}

	public void setForname(String forname) {
		this.forname = forname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}
}
