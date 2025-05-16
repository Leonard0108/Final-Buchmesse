package kickstart.Equipments;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kickstart.Cart.CartItem;
import org.javamoney.moneta.Money;
import java.util.UUID;


@Entity
public class Equipment implements CartItem{
    @Id
	private UUID id = UUID.randomUUID();
    private String name;
    private Money price;
    private String type;
    private String imageUrl;

    public Equipment() {
    }

    public Equipment(String name, Money price, String type, String imageUrl) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.imageUrl = imageUrl;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Equipment that = (Equipment) o;
    return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}