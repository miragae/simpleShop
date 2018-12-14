package pl.simpleshop.dao;

import pl.simpleshop.model.Customer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerDaoLocal {
    void saveOrUpdate(Customer customer);
    Customer find(String id);
    List<Customer> findByQuery(String query);
}
