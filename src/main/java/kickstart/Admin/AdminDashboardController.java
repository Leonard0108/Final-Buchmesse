package kickstart.Admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

	private final FairCatalog fairCatalog;

	public AdminDashboardController(FairCatalog fairCatalog) {
		this.fairCatalog = fairCatalog;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/Admin")
	public String showDashboard(Model model) {
		model.addAttribute("fairs", fairCatalog.findAll());
		model.addAttribute("ordersCount", 128);
		model.addAttribute("balanceTotal", "€42,300");
		return "dashboard";
	}
}

