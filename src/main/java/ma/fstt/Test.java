package ma.fstt;

import java.util.List;

import ma.fstt.bean.CartBean;
import ma.fstt.bean.ProductBean;
import ma.fstt.bean.StoreFrontBean;
import ma.fstt.bean.UserBean;
import ma.fstt.model.Cart;
import ma.fstt.model.Product;
import ma.fstt.model.StoreFront;
import ma.fstt.model.User;

/**
 * Test
 */
public class Test {

  public static void main(String[] args) {

    ProductBean productBean = new ProductBean();
    List<Product> products = productBean.findAll();
    for (Product product : products) {
      System.out.println(product.getName());
    }

    // StoreFrontBean storeFrontBean = new StoreFrontBean();
    // storeFrontBean.setName("Store 1");
    // StoreFront storeFront = storeFrontBean.save();
    //
    // ProductBean productBean = new ProductBean();
    // productBean.setPrice(100.0);
    // productBean.setName("Product 1");
    // productBean.setDescription("Product 1 description");
    // productBean.setImage("url");
    // productBean.setStoreFront(storeFront);
    // Product product = productBean.save();
    // List<Product> products = List.of(product);
    //
    // UserBean userBean = new UserBean();
    // userBean.setName("John Doe");
    // User user = userBean.save();
    //
    // CartBean cartBean = new CartBean();
    // cartBean.setTotal(100.0);
    // cartBean.setUser(user);
    // cartBean.setProducts(products);
    // Cart savedCart = cartBean.save();

  }
}
