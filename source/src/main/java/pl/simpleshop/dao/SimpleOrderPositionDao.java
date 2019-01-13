package pl.simpleshop.dao;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.simpleshop.model.*;

import java.util.List;

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

    @Override
    public List<SimpleOrderPosition> getPositionsForOrder(SimpleOrder order) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<SimpleOrderPosition> query = cb.createQuery(SimpleOrderPosition.class);
        Root<SimpleOrderPosition> from = query.from(SimpleOrderPosition.class);
        query.where(cb.equal(from.get(SimpleOrderPosition_.order), order));
        query.orderBy(cb.asc(from.get(SimpleOrderPosition_.id)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }
}
