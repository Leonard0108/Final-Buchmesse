package kickstart.Service;

import kickstart.Exception.Event_Full_Exception;
import kickstart.Exception.All_Equipment_Booked;
import kickstart.Cart.CartItem;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service

public class ShoppingCartService {

    private Map<CartItem, Integer> cart = new HashMap<>();

    public void addProduct(CartItem item){
    cart.put(item, cart.getOrDefault(item, 0) + 1);
    }

    public void removeProduct(CartItem item) {
        cart.remove(item);
    }

    public Map<CartItem, Integer> getProductsInCart(){
        return cart;
    }

    public void checkout() throws Event_Full_Exception, All_Equipment_Booked{

    };


	public double getTotal() {
		return cart.entrySet().stream()
			.mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
			.sum();
	}



}