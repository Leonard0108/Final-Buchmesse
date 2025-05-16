package kickstart.Events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventCatalogController {

    private final EventCatalog eventCatalog;

    public EventCatalogController(EventCatalog eventCatalog) {
        this.eventCatalog = eventCatalog;
    }

    @GetMapping
    public String showEvent(Model model) {
        model.addAttribute("events", eventCatalog.findAll());  // Changed to match the template variable name
        return "event_page"; 
    }
}
