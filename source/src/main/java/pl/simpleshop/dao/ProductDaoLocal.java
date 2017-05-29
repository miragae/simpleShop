package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Local;
import pl.simpleshop.model.Category;
import pl.simpleshop.model.Product;

/**
 *
 * @author Michał Lal
 */
@Local
public interface ProductDaoLocal {
    public void save(Product product);
    public List<Product> getAllProductsForCategory(Category category);
}
