package pl.simpleshop.dao;

import pl.simpleshop.model.Order;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Stateless
public class OrderDao extends AbstractDao<Order> implements OrderDaoLocal {

    @Override
    public Class getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> getAllSortedDesc(SingularAttribute sortField) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> from = query.from(Order.class);
        query.orderBy(cb.desc(from.get(sortField)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }
}
