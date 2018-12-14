package pl.simpleshop.dao;

import pl.simpleshop.model.Order;

import javax.ejb.Local;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Local
public interface OrderDaoLocal {
    void save (Order order);
    void remove (Order order);
    List<Order> getAllSortedDesc(SingularAttribute sortField);
}
