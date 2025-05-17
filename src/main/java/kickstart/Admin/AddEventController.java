package kickstart.Admin;

import kickstart.Events.Event;
import kickstart.Events.EventCatalog;
import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.money.Monetary;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

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
	                        @RequestParam("priceAmount") double priceAmount,
	                        @RequestParam("priceCurrency") String priceCurrency,
	                        @RequestParam("image") MultipartFile imageFile) {

		try {
			if (!imageFile.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
				Path uploadDir = Paths.get("uploads");
				Files.createDirectories(uploadDir);
				Path filePath = uploadDir.resolve(filename);
				Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				event.setImageUrl("/images/" + filename);
			}
			event.setPrice(Money.of(priceAmount, Monetary.getCurrency(priceCurrency)));
			eventCatalog.save(event);
		} catch (IOException e) {
			throw new RuntimeException("Image upload failed", e);
		}

		return "redirect:/add-event";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete-event/{id}")
	public String deleteEvent(@PathVariable("id") UUID id) {
		eventCatalog.deleteById(id);
		return "redirect:/add-event";
	}
}
