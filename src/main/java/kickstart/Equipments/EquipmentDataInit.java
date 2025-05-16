package kickstart.Equipments;

import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDataInit implements CommandLineRunner {

    private final EquipmentCatalog equipmentCatalog;

    public EquipmentDataInit(EquipmentCatalog equipmentCatalog) {
        this.equipmentCatalog = equipmentCatalog;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating and saving events with the existing constructor
        equipmentCatalog.save(new Equipment(
            "Camera",
            Money.of(99.99, "USD"),
            "Lighting",
            "http://example.com/tech-conference-image.jpg"
        ));
        equipmentCatalog.save(new Equipment(
            "Chair",
            Money.of(45, "USD"),
            "Misc",
            "http://example.com/tech-conference-image.jpg"
        ));
        equipmentCatalog.save(new Equipment(
            "Speaker",
            Money.of(199.99, "USD"),
            "Sound",
            "http://example.com/tech-conference-image.jpg"
        ));

        System.out.println("Sample eq added to the database.");
    }
}