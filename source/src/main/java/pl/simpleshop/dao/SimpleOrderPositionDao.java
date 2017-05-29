package pl.simpleshop.dao;

import javax.ejb.Stateless;
import pl.simpleshop.model.SimpleOrderPosition;

/**
 *
 * @author Michał Lal
 */
@Stateless
public class SimpleOrderPositionDao extends AbstractDao<SimpleOrderPosition> implements SimpleOrderPositionDaoLocal {
    
    @Override
    public Class getEntityClass() {
        return SimpleOrderPosition.class;
    }
}
