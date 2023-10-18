package ma.fstt.bean;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ma.fstt.model.Product;
import ma.fstt.model.StoreFront;

/**
 * StoreFrontBean
 */
@Named
@RequestScoped
public class StoreFrontBean {
  private long id;
  private String name;
  private List<Product> availableProducts;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public StoreFrontBean() {
  }

  public List<Product> getAvailableProducts() {
    return availableProducts;
  }

  public void setAvailableProducts(List<Product> availableProducts) {
    this.availableProducts = availableProducts;
  }

  public StoreFrontBean(long id, List<Product> availableProducts) {
    this.id = id;
    this.availableProducts = availableProducts;
  }

  public StoreFront save() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    StoreFront storeFront = new StoreFront();
    storeFront.setName(name);
    storeFront.setAvailableProducts(availableProducts);
    em.getTransaction().begin();
    em.persist(storeFront);
    em.getTransaction().commit();
    em.close();
    emf.close();
    return storeFront;
  }

  public void update() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    StoreFront storeFront = em.find(StoreFront.class, id);
    storeFront.setName(name);
    em.getTransaction().begin();
    em.merge(storeFront);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public void delete() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    StoreFront storeFront = em.find(StoreFront.class, id);
    em.getTransaction().begin();
    em.remove(storeFront);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public StoreFront find() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    StoreFront storeFront = em.find(StoreFront.class, id);
    em.close();
    emf.close();
    return storeFront;
  }

  public List<StoreFront> findAll() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();
    List<StoreFront> storeFronts = em.createQuery("select s from StoreFront s", StoreFront.class).getResultList();
    em.close();
    emf.close();
    return storeFronts;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
