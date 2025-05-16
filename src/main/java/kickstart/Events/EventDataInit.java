package kickstart.Events;

import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EventDataInit implements CommandLineRunner {

    private final EventCatalog eventCatalog;

    public EventDataInit(EventCatalog eventCatalog) {
        this.eventCatalog = eventCatalog;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating and saving events with the existing constructor
        eventCatalog.save(new Event(
            "Tech Conference 2025",
            "A conference bringing together tech enthusiasts and industry leaders.",
            Money.of(99.99, "USD"),
            "2025-06-15",
            "Berlin, Germany",
            "Upcoming",
            "Visitors: 1000",
            "Conference",
            "http://example.com/tech-conference-image.jpg"
        ));

        eventCatalog.save(new Event(
            "Music Festival 2025",
            "An exciting music festival with top bands and performers.",
            Money.of(49.99, "USD"),
            "2025-07-20",
            "Munich, Germany",
            "Upcoming",
            "Visitors: 5000",
            "Festival",
            "http://example.com/music-festival-image.jpg"
        ));

        eventCatalog.save(new Event(
            "Startup Bootcamp",
            "A bootcamp for aspiring entrepreneurs to learn and grow their businesses.",
            Money.of(299.99, "USD"),
            "2025-08-05",
            "Hamburg, Germany",
            "Upcoming",
            "Visitors: 200",
            "Workshop",
            "http://example.com/startup-bootcamp-image.jpg"
        ));

        System.out.println("Sample events added to the database.");
    }
}
