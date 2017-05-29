package pl.simpleshop.dao;

import javax.ejb.Local;
import pl.simpleshop.model.SimpleOrder;

/**
 *
 * @author Michał Lal
 */
@Local
public interface SimpleOrderDaoLocal {
    public void save(SimpleOrder order);
}
