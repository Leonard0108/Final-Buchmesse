package kickstart.Events;

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
            "Gardening Fair 2025",
            "Discover inspiring reads, green ideas, and expert knowledge from the world of horticulture",
           60.90,
            "2025-06-15",
            "Leifurter Exhibition Grounds, Hall A & B",
            "Upcoming",
            "Visitors: 1000",
            "Gardening",
            "Gardening_Event_2025.png"
        ));

        eventCatalog.save(new Event(
            "Manga Fair 2025",
            "Dive into captivating worlds, unforgettable characters, and thrilling stories from the realm of Japanese comics!",
            50.00,
            "2025-07-20",
			"Leifurter Exhibition Grounds, Hall C",
            "Upcoming",
            "Visitors: 5000",
            "Manga Comic con",
            "Manga_2025.png"
        ));

        eventCatalog.save(new Event(
            "Politics Fair 2025",
            "Explore insightful analyses, lively debates, and visionary ideas shaping our society and future!",
            30.10,
            "2025-08-05",
			"Leifurter Exhibition Grounds, Hall B & C",
            "Upcoming",
            "Visitors: 200",
            "Politics",
            "Politics_2025.png"
        ));

        System.out.println("Sample events added to the database.");
    }
}
