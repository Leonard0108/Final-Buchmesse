package kickstart.Cart;
import kickstart.Equipments.EquipmentCatalog;
import kickstart.Events.EventCatalog;
import kickstart.Exception.All_Equipment_Booked;
import kickstart.Exception.Event_Full_Exception;
import kickstart.Service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@RequestMapping("/cart")
@Controller
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final EquipmentCatalog equipmentCatalog;
    private final EventCatalog eventCatalog;

    @Autowired

    public CartController(ShoppingCartService shoppingCartService, EquipmentCatalog equipmentCatalog, EventCatalog eventCatalog) {
        this.shoppingCartService = shoppingCartService;
        this.equipmentCatalog = equipmentCatalog;
        this.eventCatalog = eventCatalog;
    }

    @GetMapping
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("cart_page");
        modelAndView.addObject("cartItems", shoppingCartService.getProductsInCart().entrySet());
        modelAndView.addObject("total", String.format("%.2f", shoppingCartService.getTotal()));
        return modelAndView;
    }

    @GetMapping("/addEquipment/{id}")
    public ModelAndView addEquipmentToCart(@PathVariable UUID id) {
        equipmentCatalog.findById(id).ifPresent(equipment -> shoppingCartService.addProduct(equipment));
        return shoppingCart();
    }

    @GetMapping("/removeEquipment/{id}")
    public ModelAndView removeEquipmentFromCart(@PathVariable UUID id) {
        equipmentCatalog.findById(id).ifPresent(equipment -> shoppingCartService.removeProduct(equipment));  
        return shoppingCart();
    }

    @GetMapping("/addEvent/{id}")
    public ModelAndView addEventToCart(@PathVariable UUID id) {
        eventCatalog.findById(id).ifPresent(event -> shoppingCartService.addProduct(event));  
        return shoppingCart();
    }

    @GetMapping("/removeEvent/{id}")
    public ModelAndView removeEventFromCart(@PathVariable UUID id) {
        eventCatalog.findById(id).ifPresent(event -> shoppingCartService.removeProduct(event)); 
        return shoppingCart();
    }

    @GetMapping("/checkout")
    public ModelAndView checkout() {
        try {
            shoppingCartService.checkout();
        } catch (All_Equipment_Booked | Event_Full_Exception e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }

    @PostMapping("/checkout")
    public ModelAndView checkoutPost() {
    try {
        shoppingCartService.checkout();
    } catch (All_Equipment_Booked | Event_Full_Exception e) {
        return shoppingCart().addObject("outOfStockMessage", e.getMessage());
    }
    return shoppingCart();
    }

    @PostMapping
    public ModelAndView addToCartPost(@RequestParam UUID pid, @RequestParam int number) {
    boolean found = false;

    if (equipmentCatalog.findById(pid).isPresent()) {
        equipmentCatalog.findById(pid).ifPresent(equipment -> {
            for (int i = 0; i < number; i++) {
                shoppingCartService.addProduct(equipment);
            }
        });
        found = true;
    }

    else if (eventCatalog.findById(pid).isPresent()) {
        eventCatalog.findById(pid).ifPresent(event -> {
            for (int i = 0; i < number; i++) {
                shoppingCartService.addProduct(event);
            }
        });
        found = true;
    }

    if (!found) {
        return shoppingCart().addObject("errorMessage", "Product not found.");
    }

    return shoppingCart().addObject("successMessage", "Added to cart!");
    }

    @PostMapping("/addEventAndEquipment")
    public ModelAndView addEventAndEquipmentToCart(@RequestParam UUID eventId, @RequestParam UUID equipmentId, @RequestParam int number) {
    // Fetch the event and equipment by IDs
    eventCatalog.findById(eventId).ifPresent(event -> {
        shoppingCartService.addProduct(event);  // Add the event to the cart
    });

    equipmentCatalog.findById(equipmentId).ifPresent(equipment -> {
        // Add the selected number of the equipment to the cart
        for (int i = 0; i < number; i++) {
            shoppingCartService.addProduct(equipment);
        }
    });

    return shoppingCart();  // Return to the shopping cart page
}

}
