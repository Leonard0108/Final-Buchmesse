package kickstart.Admin;

import kickstart.Events.Event;
import kickstart.Events.EventCatalog;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;

@Controller
public class AddEventController {

	private final EventCatalog eventCatalog;

	public AddEventController(EventCatalog eventCatalog) {
		this.eventCatalog = eventCatalog;
	}

	@GetMapping("/add-event")
	public String showForm(Model model) {
		model.addAttribute("event", new Event());
		return "add_event_form";
	}

	@PostMapping("/add-event")
	public String saveEvent(@ModelAttribute Event event,
							@RequestParam("priceAmount") double priceAmount,
							@RequestParam("priceCurrency") String priceCurrency) {

		event.setPrice(Money.of(priceAmount, Monetary.getCurrency(priceCurrency)));
		eventCatalog.save(event);
		return "redirect:/add-event";
	}
}
