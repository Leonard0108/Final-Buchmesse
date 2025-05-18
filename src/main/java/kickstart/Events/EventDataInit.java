package kickstart.Events;

import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class EventDataInit implements CommandLineRunner {

	private final EventCatalog eventCatalog;

	public EventDataInit(EventCatalog eventCatalog) {
		this.eventCatalog = eventCatalog;
	}

	@Override
	public void run(String... args) throws Exception {
		createEventIfNotExists("Tech Conference 2025",
			"A conference bringing together tech enthusiasts and industry leaders.",
			Money.of(99.99, "USD"),
			"2025-06-15",
			"Berlin, Germany",
			"Upcoming",
			"Visitors: 1000",
			"Conference",
			"");

		createEventIfNotExists("Music Festival 2025",
			"An exciting music festival with top bands and performers.",
			Money.of(49.99, "USD"),
			"2025-07-20",
			"Munich, Germany",
			"Upcoming",
			"Visitors: 5000",
			"Festival",
			"");

		createEventIfNotExists("Startup Bootcamp",
			"A bootcamp for aspiring entrepreneurs to learn and grow their businesses.",
			Money.of(299.99, "USD"),
			"2025-08-05",
			"Hamburg, Germany",
			"Upcoming",
			"Visitors: 200",
			"Workshop",
			"");
	}

	private void createEventIfNotExists(String name, String description, Money price, String date,
	                                    String location, String status, String visitorsQuantity,
	                                    String type, String imageUrl) {

		boolean exists = StreamSupport.stream(eventCatalog.findAll().spliterator(), false)
			.anyMatch(e -> e.getName().equalsIgnoreCase(name));

		if (!exists) {
			eventCatalog.save(new Event(name, description, price, date, location, status, visitorsQuantity, type, imageUrl));
		}
	}
}
