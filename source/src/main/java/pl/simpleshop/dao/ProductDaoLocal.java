package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Local;

import pl.simpleshop.model.*;


@Local
public interface ProductDaoLocal {
    void save(Product product);
    List<Product> getAllProductsForCategory(Category category);
    List<Product> getAllProductsForSupplier(Supplier supplier);
    List<Customer> getAllCustomersForProduct(Product product);
    List<Employee> getAllEmployeesForProduct(Product product);
}
