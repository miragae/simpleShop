package pl.simpleshop.dao;

import javax.ejb.Local;

import pl.simpleshop.model.SimpleOrder;
import pl.simpleshop.model.SimpleOrderPosition;

import java.util.List;

/**
 *
 * @author Michał Lal
 */
@Local
public interface SimpleOrderPositionDaoLocal {
    public void save(SimpleOrderPosition order);
    List<SimpleOrderPosition> getPositionsForOrder(SimpleOrder order);
}
