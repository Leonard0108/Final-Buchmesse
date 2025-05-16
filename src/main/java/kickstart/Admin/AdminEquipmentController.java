package kickstart.Admin;

import kickstart.Equipments.Equipment;
import kickstart.Equipments.EquipmentCatalog;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;

@Controller
public class AdminEquipmentController {

	private final EquipmentCatalog equipmentCatalog;

	public AdminEquipmentController(EquipmentCatalog equipmentCatalog) {
		this.equipmentCatalog = equipmentCatalog;
	}

	@GetMapping("/add")
	public String showForm(Model model) {
		model.addAttribute("equipment", new Equipment());
		return "addNewequipment_form";
	}

	@PostMapping("/add")
	public String saveEquipment(@ModelAttribute Equipment equipment,
								@RequestParam("priceAmount") double priceAmount,
								@RequestParam("priceCurrency") String priceCurrency) {

		equipment.setPrice(Money.of(priceAmount, Monetary.getCurrency(priceCurrency)));
		equipmentCatalog.save(equipment);
		return "redirect:/add";
	}
}