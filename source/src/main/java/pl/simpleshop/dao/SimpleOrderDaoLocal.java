package pl.simpleshop.dao;

import javax.ejb.Local;
import pl.simpleshop.model.SimpleOrder;

import java.util.List;

/**
 *
 * @author Michał Lal
 */
@Local
public interface SimpleOrderDaoLocal {
    void save(SimpleOrder order);
    List<SimpleOrder> getAll();
}
