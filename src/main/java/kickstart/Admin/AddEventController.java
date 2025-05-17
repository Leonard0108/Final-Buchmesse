package kickstart.Admin;

import kickstart.Events.Event;
import kickstart.Events.EventCatalog;
import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import javax.money.Monetary;

@Controller
public class AddEventController {

	private final EventCatalog eventCatalog;

	public AddEventController(EventCatalog eventCatalog) {
		this.eventCatalog = eventCatalog;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/add-event")
	public String showForm(Model model) {
	model.addAttribute("event", new Event());
	model.addAttribute("eventList", eventCatalog.findAll());
	return "add_event_form";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add-event")
	public String saveEvent(@ModelAttribute Event event,
							@RequestParam("priceAmount") double priceAmount){
		event.setPrice(priceAmount);
		eventCatalog.save(event);
		return "redirect:/add-event";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete-event/{id}")
	public String deleteEvent(@PathVariable("id") UUID id) {
		eventCatalog.deleteById(id);
		return "redirect:/add-event";
	}

}
