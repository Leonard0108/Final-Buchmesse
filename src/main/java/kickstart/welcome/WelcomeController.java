/*
 * Copyright 2014-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kickstart.welcome;

import kickstart.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {


	@GetMapping("/")
	public String index() {
		return "buchmesse";
	}

	@GetMapping("/login")
	public String login() {
	return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public String registerSubmit(@RequestParam String email, @RequestParam String password, String forname, String surname, Model model) {
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
			success = customerService.registerCustomer(email, password, forname, surname);

			if (success) {
				return "redirect:/";
			} else {
				model.addAttribute("ErrorEmail", "Email already registered");
			}
		}

		System.out.println("Fehler bei Email oder Password");
		return "register";
	}

	@PostMapping("/login")
	public String loginSubmit(@RequestParam String email, @RequestParam String password, Model model){
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
			//Checks the Password
			success = customerService.loginCustomer(email, password);
			if (success) {
				return "success";
			}
		}
		return "login";
	}
}
