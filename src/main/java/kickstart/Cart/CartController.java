package kickstart.Cart;

import kickstart.Equipments.EquipmentCatalog;
import kickstart.Events.EventCatalog;
import kickstart.Exception.All_Equipment_Booked;
import kickstart.Exception.Event_Full_Exception;
import kickstart.Service.ShoppingCartService;
import kickstart.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
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

    // Show the shopping cart with items and total
    @GetMapping
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("cart_page");
        modelAndView.addObject("cartItems", shoppingCartService.getProductsInCart().entrySet());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    // Add equipment to the cart
    @GetMapping("/addEquipment/{id}")
    public ModelAndView addEquipmentToCart(@PathVariable UUID id) {
        equipmentCatalog.findById(id).ifPresent(equipment -> shoppingCartService.addProduct(equipment));
        return shoppingCart();
    }

    // Remove equipment from the cart
    @GetMapping("/removeEquipment/{id}")
    public ModelAndView removeEquipmentFromCart(@PathVariable UUID id) {
        equipmentCatalog.findById(id).ifPresent(equipment -> shoppingCartService.removeProduct(equipment));
        return shoppingCart();
    }

    // Add event to the cart
    @GetMapping("/addEvent/{id}")
    public ModelAndView addEventToCart(@PathVariable UUID id) {
        eventCatalog.findById(id).ifPresent(event -> shoppingCartService.addProduct(event));
        return shoppingCart();
    }

    // Remove event from the cart
    @GetMapping("/removeEvent/{id}")
    public ModelAndView removeEventFromCart(@PathVariable UUID id) {
        eventCatalog.findById(id).ifPresent(event -> shoppingCartService.removeProduct(event));
        return shoppingCart();
    }

    // Handle adding product to cart using POST request
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

    // Handle adding both event and equipment to cart
    @PostMapping("/addEventAndEquipment")
    public ModelAndView addEventAndEquipmentToCart(@RequestParam UUID eventId, @RequestParam UUID equipmentId, @RequestParam int number) {
        eventCatalog.findById(eventId).ifPresent(event -> {
            shoppingCartService.addProduct(event);  
        });

        equipmentCatalog.findById(equipmentId).ifPresent(equipment -> {
            for (int i = 0; i < number; i++) {
                shoppingCartService.addProduct(equipment);
            }
        });

        return shoppingCart();  
    }

    // Show checkout confirmation page
    // Show checkout confirmation page
    @PostMapping("/checkout")
    public ModelAndView checkout(HttpSession session) {
        User user = (User) session.getAttribute("user");

        // If user is not logged in, redirect them to login page
        if (user == null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/login");
            modelAndView.addObject("loginMessage", "You need to log in to proceed with checkout.");
            return modelAndView;
        }

        try {
            shoppingCartService.checkout();
            String orderDetails = shoppingCartService.saveOrder(user);
            
            ModelAndView modelAndView = new ModelAndView("checkout_confirmation");
            modelAndView.addObject("orderDetails", orderDetails);
            modelAndView.addObject("total", shoppingCartService.getTotal().toString());
            modelAndView.addObject("cartItems", shoppingCartService.getProductsInCart().entrySet());
            return modelAndView;
        } catch (All_Equipment_Booked | Event_Full_Exception e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
}


}
