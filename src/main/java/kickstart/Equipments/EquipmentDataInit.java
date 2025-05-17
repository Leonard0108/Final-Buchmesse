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
            "Gray upholstered bar stool 180x50x50",
			199.99,
            "Bench",
            "Sitzbank_1.png"
        ));
        equipmentCatalog.save(new Equipment(
            "white bar counter 150x180x100",
			 199.99,
            "Table",
            "Bartresen_1.png"
        ));
        equipmentCatalog.save(new Equipment(
            "Coffe machine X",
            199.99,
            "Sound",
            "Kaffemaschine_1.png"
        ));
		equipmentCatalog.save(new Equipment(
			"Barhocker 60x60x130",
			199.99,
			"Chair",
			"Barhocker_1.png"
		));

        System.out.println("Sample eq added to the database.");
    }
}