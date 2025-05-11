package kickstart.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.salespointframework.useraccount.Role;

import java.util.UUID;

@Entity
public class User {
	@Id
	private UUID id = UUID.randomUUID();

	private String email;
	private String password;
	private String forename;
	private String surname;
	private Role role;

	public User(String email, String password, String forename, String surname, Role role) {
		this.email = email;
		this.password = password;
		this.forename = forename;
		this.surname = surname;
		this.role = role;
	}

	public User () {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
