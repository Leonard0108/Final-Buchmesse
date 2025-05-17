package kickstart.Admin;


import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface FairCatalog extends CrudRepository<Fair, UUID> {
}