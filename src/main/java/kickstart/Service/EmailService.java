/*package kickstart.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import kickstart.Cart.CartItem;
import kickstart.user.User;
import org.javamoney.moneta.Money;

import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(User user, Map<CartItem, Integer> cartItems) {
        String to = user.getEmail();
        String subject = "Order Confirmation";

        StringBuilder text = new StringBuilder();
        text.append("Dear ").append(user.getForename()).append(",\n\n");
        text.append("Thank you for your purchase! Your order has been successfully placed.\n\n");

        
        text.append("Items in your order:\n");

        Money totalAmount = Money.of(0, "USD");  // Initialize the total amount

        for (Map.Entry<CartItem, Integer> entry : cartItems.entrySet()) {
            CartItem item = entry.getKey();
            int quantity = entry.getValue();
            Money itemTotal = item.getPrice().multiply(quantity);  // Price * quantity
            totalAmount = totalAmount.add(itemTotal);  // Add item total to the overall total

            text.append("- ").append(item.getName()).append(" x").append(quantity).append(" = ").append(itemTotal).append("\n");
        }

        text.append("\nTotal: ").append(totalAmount).append("\n");
        text.append("We will process your order shortly.\n\nBest regards,\nYour Company");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text.toString());

        mailSender.send(message);
    }
}
*/