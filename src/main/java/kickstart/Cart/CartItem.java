package kickstart.Cart;


import org.javamoney.moneta.Money;

public interface CartItem {
    Money getPrice();
    String getName();

}
