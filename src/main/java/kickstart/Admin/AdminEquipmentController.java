package kickstart.Admin;

import kickstart.Equipments.Equipment;
import kickstart.Equipments.EquipmentCatalog;
import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.money.Monetary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
								@RequestParam("priceAmount") double priceAmount,
								@RequestParam("priceCurrency") String priceCurrency,
								@RequestParam("image") MultipartFile imageFile) {

		try {
			if (!imageFile.isEmpty()) {
				String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
				Path uploadDir = Paths.get("uploads");
				Files.createDirectories(uploadDir);
				Path filePath = uploadDir.resolve(filename);
				Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
				equipment.setImageUrl("/images/" + filename);
			}
			equipment.setPrice(Money.of(priceAmount, Monetary.getCurrency(priceCurrency)));
			equipmentCatalog.save(equipment);
		} catch (IOException e) {
			throw new RuntimeException("Image upload failed", e);
		}

    return "redirect:/add";
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/delete-equipment/{id}")
	public String deleteEquipment(@PathVariable("id") UUID id) {
		equipmentCatalog.deleteById(id);
		return "redirect:/add";
	}
}
