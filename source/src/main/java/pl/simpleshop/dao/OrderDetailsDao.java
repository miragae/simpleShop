package pl.simpleshop.dao;

import pl.simpleshop.model.*;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class OrderDetailsDao extends AbstractDao<OrderDetails> implements OrderDetailsDaoLocal {

    @Override
    public Class getEntityClass() {
        return OrderDetails.class;
    }

    @Override
    public List<OrderDetails> getOrderDetailsForOrder(Order order) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<OrderDetails> query = cb.createQuery(OrderDetails.class);
        Root<OrderDetails> from = query.from(OrderDetails.class);
        Join<OrderDetails, Order> orderJoin = from.join(OrderDetails_.order);
        query.where(cb.equal(from.get(OrderDetails_.order), order));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }
}
