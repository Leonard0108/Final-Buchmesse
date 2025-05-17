package kickstart.Events;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.javamoney.moneta.Money;
import kickstart.Cart.CartItem;
import java.util.UUID;

@Entity
public class Event implements CartItem {

	@Id
	private UUID id = UUID.randomUUID();

	private String name;
	private String description;
	private double price;

	private String date;
	private String location;
	private String status;
	private String visitorsQuantity;
	private String type;
	private String imageUrl;

	public Event() {
	}

	public Event(String name, String description, double price,
				 String date, String location, String status,
				 String visitorsQuantity, String type, String imageUrl) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.date = date;
		this.location = location;
		this.status = status;
		this.visitorsQuantity = visitorsQuantity;
		this.type = type;
		this.imageUrl = imageUrl;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public String getStatus() {
		return status;
	}

	public String getVisitorsQuantity() {
		return visitorsQuantity;
	}

	public String getType() {
		return type;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Event setName(String name) {
		this.name = name;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVisitorsQuantity(String visitorsQuantity) {
		this.visitorsQuantity = visitorsQuantity;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event that = (Event) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}	

}
