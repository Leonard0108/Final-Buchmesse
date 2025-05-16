package kickstart.Events;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface EventCatalog extends CrudRepository<Event, UUID> {
}
