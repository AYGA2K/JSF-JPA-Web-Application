package ma.fstt.bean;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ma.fstt.model.Cart;
import ma.fstt.model.Product;
import ma.fstt.model.StoreFront;

/**
 * ProductBean
 */
@Named
@RequestScoped
public class ProductBean {
  private Long id;
  private String name;
  private double price;
  private String description;
  private String image;
  private StoreFront storeFront;
  private List<Cart> carts;

  public String getImage() {
    return image;
  }

  public StoreFront getStoreFront() {
    return storeFront;
  }

  public void setStoreFront(StoreFront storeFront) {
    this.storeFront = storeFront;
  }

  public List<Cart> getCarts() {
    return carts;
  }

  public void setCarts(List<Cart> carts) {
    this.carts = carts;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public ProductBean() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product save() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setDescription(description);
    product.setImage(image);
    product.setStoreFront(storeFront);
    product.setCarts(carts);
    em.getTransaction().begin();
    em.persist(product);
    em.getTransaction().commit();
    em.close();
    emf.close();
    return product;
  }

  public void update() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Product product = em.find(Product.class, id);
    product.setName(name);
    product.setPrice(price);
    product.setDescription(description);
    em.getTransaction().begin();
    em.merge(product);
    em.getTransaction().commit();
    em.close();
    emf.close();
  }

  public void delete() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Product product = em.find(Product.class, id);
    em.getTransaction().begin();
    em.remove(product);
    em.getTransaction().commit();
    em.close();
    emf.close();
  }

  public Product find() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Product product = em.find(Product.class, id);
    em.close();
    emf.close();
    return product;
  }

  public List<Product> findAll() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    em.close();
    emf.close();
    return products;

  }

}
