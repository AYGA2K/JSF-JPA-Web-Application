package ma.fstt.bean;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import ma.fstt.model.Cart;
import ma.fstt.model.User;

/**
 * UserBean
 */
@Named
@RequestScoped
public class UserBean {
  private Long id;
  private String name;
  private Cart cart;

  public UserBean(String name, Cart cart) {
    this.name = name;
    this.cart = cart;
  }

  public UserBean() {
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

  public String findByName() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
    query.setParameter("name", name);
    List<User> resultList = query.getResultList();
    if (resultList.isEmpty()) {
      return "login";
    } else {
      User foundUser = resultList.get(0);

      FacesContext context = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
      session.setAttribute("userId", foundUser.getId());

      return "index?faces-redirect=true&userId=" + foundUser.getId();
    }
  }

  public User save() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    User user = new User();
    user.setName(name);
    cart = new Cart();
    cart.setUser(user);
    em.getTransaction().begin();
    em.persist(cart);
    em.persist(user);
    em.getTransaction().commit();
    em.close();
    emf.close();
    return user;

  }

  public void delete() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    User user = em.find(User.class, id);
    em.getTransaction().begin();
    em.remove(user);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public void update() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    User user = em.find(User.class, id);
    user.setName(name);
    user.setCart(cart);
    em.getTransaction().begin();
    em.merge(user);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public User find() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    User user = em.find(User.class, id);
    em.close();
    emf.close();

    return user;

  }

  public List<User> findAll() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
    em.close();
    emf.close();

    return users;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

}
