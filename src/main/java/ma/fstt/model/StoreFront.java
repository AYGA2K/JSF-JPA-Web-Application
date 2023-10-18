package ma.fstt.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * StoreFront
 */
@Entity
@Table(name = "storefront")
public class StoreFront {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany
  @JoinColumn(name = "storefront_id")
  private List<Product> availableProducts;

  public StoreFront() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Product> getAvailableProducts() {
    return availableProducts;
  }

  public void setAvailableProducts(List<Product> availableProducts) {
    this.availableProducts = availableProducts;
  }
}
