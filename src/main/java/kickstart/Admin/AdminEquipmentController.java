package kickstart.Admin;

import kickstart.Equipments.Equipment;
import kickstart.Equipments.EquipmentCatalog;
import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import java.util.UUID;

@Controller
public class AdminEquipmentController {

	private final EquipmentCatalog equipmentCatalog;

	public AdminEquipmentController(EquipmentCatalog equipmentCatalog) {
		this.equipmentCatalog = equipmentCatalog;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/add")
	public String showForm(Model model) {
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("equipmentList", equipmentCatalog.findAll());
		return "addNewequipment_form";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public String saveEquipment(@ModelAttribute Equipment equipment,
								@RequestParam("priceAmount") double priceAmount) {

		equipment.setPrice(priceAmount);
		equipmentCatalog.save(equipment);
		return "redirect:/add";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete-equipment/{id}")
	public String deleteEquipment(@PathVariable("id") UUID id) {
		equipmentCatalog.deleteById(id);
		return "redirect:/add";
	}
}
