package kickstart.Equipments;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipments") // corrected from /equipments for consistency
public class EquipmentCatalogController {

    private final EquipmentCatalog equipmentCatalog;

    public EquipmentCatalogController(EquipmentCatalog equipmentCatalog) {
        this.equipmentCatalog = equipmentCatalog;
    }

    @GetMapping
    public String showEquipmentPage(Model model) {
        model.addAttribute("equipments", equipmentCatalog.findAll());
        return "equipment_page";
    }
}
