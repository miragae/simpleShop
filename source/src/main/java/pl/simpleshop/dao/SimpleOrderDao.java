package pl.simpleshop.dao;

import javax.ejb.Stateless;
import pl.simpleshop.model.SimpleOrder;

/**
 *
 * @author Michał Lal
 */
@Stateless
public class SimpleOrderDao extends AbstractDao<SimpleOrder> implements SimpleOrderDaoLocal {
    
    @Override
    public Class getEntityClass() {
        return SimpleOrder.class;
    }
}
