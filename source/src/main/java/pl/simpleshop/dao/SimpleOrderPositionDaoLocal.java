package pl.simpleshop.dao;

import javax.ejb.Local;
import pl.simpleshop.model.SimpleOrderPosition;

/**
 *
 * @author Michał Lal
 */
@Local
public interface SimpleOrderPositionDaoLocal {
    public void save(SimpleOrderPosition order);
}
