package kickstart.Cart;

import java.util.UUID;

import org.javamoney.moneta.Money;

public interface CartItem {
    Money getPrice();
    String getName();
    UUID getId();


}
