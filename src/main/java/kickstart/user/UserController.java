package kickstart.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/dashboard")
	public String adminPanel(){
		return "dashboard";
	}

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String registerSubmit(@RequestParam String email, @RequestParam String password, String forname, String surname, Model model, HttpSession session) {
		boolean success = true;
		if (email == null){
			model.addAttribute("ErrorEmail", "Email can't be empty");
			success = false;
		}
		else if (password == null) {
			model.addAttribute("ErrorPassword", "Password can't be empty");
			success = false;
		}

		if (success){
			success = userService.registerUser(email, password, forname, surname);

			if (success) {
				return "login";
			} else {
				model.addAttribute("ErrorEmail", "Email already registered");
			}
		}

		System.out.println("Error with Email or Password");
		return "register";
	}

	@PostMapping("/login")
	public String loginSubmit(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest request){
		boolean success = true;
		if (email == null) {
			model.addAttribute("ErrorEmail", "Email can't be empty");
			success = false;
		}
		else if (password == null) {
			model.addAttribute("ErrorPassword", "Password can't be empty");
			success = false;
		}
		if(success) {
			
			String result = userService.loginUser(email, password, request);
			if (!(result.equals("EmailError")  || result.equals("PaswwordError"))) {
				if(result == "ADMIN") {
					return "redirect:/Admin";
				}
				else if(result == "CUSTOMER") {
					return "redirect:/events";
				}
			}
			else if (result.equals("EmailError")) {
				model.addAttribute("ErrorEmail", "Email doesn't exist");
			}
			model.addAttribute("ErrorPassword", "Password doesn't match");
		}
		return "login";
	}
}
