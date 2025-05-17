package kickstart.Equipments;

import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class EquipmentDataInit implements CommandLineRunner {

	private final EquipmentCatalog equipmentCatalog;

	public EquipmentDataInit(EquipmentCatalog equipmentCatalog) {
		this.equipmentCatalog = equipmentCatalog;
	}

	@Override
	public void run(String... args) throws Exception {
		createEquipmentIfNotExists("Camera", Money.of(99.99, "USD"), "Lighting", "http://example.com/tech-conference-image.jpg");
		createEquipmentIfNotExists("Chair", Money.of(45, "USD"), "Misc", "http://example.com/tech-conference-image.jpg");
		createEquipmentIfNotExists("Speaker", Money.of(199.99, "USD"), "Sound", "http://example.com/tech-conference-image.jpg");
	}

	private void createEquipmentIfNotExists(String name, Money price, String type, String imageUrl) {
		boolean exists = StreamSupport.stream(equipmentCatalog.findAll().spliterator(), false)
			.anyMatch(e -> e.getName().equalsIgnoreCase(name));

		if (!exists) {
			equipmentCatalog.save(new Equipment(name, price, type, imageUrl));
		}
	}
}
