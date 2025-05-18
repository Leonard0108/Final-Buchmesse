package kickstart.Service;

import kickstart.Cart.CartItem;
import kickstart.Equipments.Equipment;
import kickstart.Events.Event;
import kickstart.Exception.All_Equipment_Booked;
import kickstart.Exception.Event_Full_Exception;
import kickstart.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private Map<CartItem, Integer> cart = new HashMap<>();
    private long orderIdCounter = 1;

    public void addProduct(CartItem item){
        cart.put(item, cart.getOrDefault(item, 0) + 1);
    }

    public void removeProduct(CartItem item) {
        cart.remove(item);
    }

    public Map<CartItem, Integer> getProductsInCart(){
        return cart;
    }

    public void checkout() throws Event_Full_Exception, All_Equipment_Booked {
        // Implement logic for checkout
    }

    public BigDecimal getTotal() {
        double sum = cart.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().getNumber().doubleValue() * entry.getValue())
                .reduce(0.0, Double::sum);
        return BigDecimal.valueOf(sum);
    }

    // Helper method to extract events from the cart
    public List<Event> getEventsFromCart() {
        List<Event> eventList = new ArrayList<>();
        for (Map.Entry<CartItem, Integer> entry : cart.entrySet()) {
            CartItem item = entry.getKey();
            if (item instanceof Event) {
                eventList.add((Event) item); // Cast CartItem to Event
            }
        }
        return eventList;
    }

    // Helper method to extract equipment from the cart
    public List<Equipment> getEquipmentFromCart() {
        List<Equipment> equipmentList = new ArrayList<>();
        for (Map.Entry<CartItem, Integer> entry : cart.entrySet()) {
            CartItem item = entry.getKey();
            if (item instanceof Equipment) {
                equipmentList.add((Equipment) item); // Cast CartItem to Equipment
            }
        }
        return equipmentList;
    }

    // Save order logic
    public String saveOrder(User user) {
        // Convert cart to events and equipment
        List<Event> events = getEventsFromCart();
        List<Equipment> equipment = getEquipmentFromCart();

        String orderDetails = "Order ID: " + orderIdCounter + "\n" +
                              "User ID: " + user.getId() + "\n" +
                              "Total: " + getTotal().toString() + "\n" +
                              "Events: " + events.toString() + "\n" +
                              "Equipment: " + equipment.toString();

        orderIdCounter++;
        return orderDetails;
    }

}
