package kickstart.Equipment;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface EquipmentCatalog extends CrudRepository<Equipment, UUID> {
    List<Equipment> findByType(String type);
}
