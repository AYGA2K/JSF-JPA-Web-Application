package ma.fstt.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import ma.fstt.model.Cart;
import ma.fstt.model.Product;
import ma.fstt.model.User;

/**
 * CartBean
 */
@Named
@RequestScoped
public class CartBean {

  private Long id;
  private List<Product> products;
  private double total;
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public CartBean() {
  }

  public CartBean(Long id, List<Product> products, double total) {
    this.id = id;
    this.products = products;
    this.total = total;
  }

  public String goToCart() {
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
        .getRequest();

    String id = request.getParameter("userId");

    return "cart?faces-redirect=true&userId=" + id;
  }

  public Cart findByUserId(Long id) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    TypedQuery<Cart> query = em.createQuery("SELECT c FROM Cart c WHERE c.user.id = :id", Cart.class);
    query.setParameter("id", id);
    List<Cart> resultList = query.getResultList();
    Cart cart = resultList.get(0);
    em.close();
    emf.close();

    return cart;

  }

  public void addToCart(Product product) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
        .getRequest();

    String id = request.getParameter("userId");
    if (id != null) {
      Long userId = Long.parseLong(id);
      TypedQuery<Cart> query = em.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class);
      query.setParameter("userId", userId);
      List<Cart> resultList = query.getResultList();
      Cart cart = resultList.get(0);
      cart.setTotal(cart.getTotal() + product.getPrice());
      List<Product> products = cart.getProducts();
      if (products == null) {
        products = new ArrayList<>();
      }

      products.add(product);
      cart.setProducts(products);
      em.getTransaction().begin();
      em.merge(cart);
      em.getTransaction().commit();
      em.close();
      emf.close();
    }
  }

  public Cart save() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setTotal(total);
    cart.setProducts(products);
    em.getTransaction().begin();
    em.persist(cart);
    em.getTransaction().commit();
    em.close();
    emf.close();
    return cart;
  }

  public void delete() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Cart cart = em.find(Cart.class, id);
    em.getTransaction().begin();
    em.remove(cart);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public void update() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Cart cart = em.find(Cart.class, id);
    em.getTransaction().begin();
    em.merge(cart);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public Cart find() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    Cart cart = em.find(Cart.class, id);
    em.close();
    emf.close();

    return cart;
  }

  public List<Cart> findAll() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    List<Cart> carts = em.createQuery("SELECT c FROM Cart c", Cart.class).getResultList();
    em.close();
    emf.close();

    return carts;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
