package pl.simpleshop.dao;

import pl.simpleshop.model.*;

import javax.ejb.Stateless;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateless
public class CustomerDao extends AbstractDao<Customer> implements CustomerDaoLocal{

    private static final String KEY_SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWYZ";

    @Override
    public Class getEntityClass() {
        return Customer.class;
    }

    @Override
    public Customer find(String id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(getEntityClass());
        Root<Customer> from = query.from(getEntityClass());
        query.where(cb.equal(from.get("id"), id));
        query.select(from);
        List<Customer> resultList = getEntityManager().createQuery(query).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    @Override
    public List<Customer> findByQuery(String stringQuery) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();
        for(String subQuery : stringQuery.split(" ")) {
            predicates.add(cb.like(from.get(Customer_.companyName), subQuery + '%'));
            predicates.add(cb.like(from.get(Customer_.contactName), subQuery + '%'));
            predicates.add(cb.like(from.get(Customer_.country), subQuery + '%'));
        }
        query.where(cb.or(predicates.toArray(new Predicate[0])));
        query.orderBy(cb.asc(from.get(Customer_.id)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public void saveOrUpdate(Customer customer) {
        if(customer.getId() == null) {
            List<String> existingKeys = getKeys();
            String key;
            do {
                key = generateKey();
            } while (existingKeys.contains(key));

            customer.setId(key);
            save(customer);
        } else {
            update(customer);
        }
    }

    private String generateKey() {
        StringBuilder key = new StringBuilder();
        Random r = new Random();
        for(int i = 0; i < 5; i++) {
            key.append(KEY_SYMBOLS.charAt(r.nextInt(KEY_SYMBOLS.length())));
        }

        return key.toString();
    }

    private List<String> getKeys() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Customer> from = query.from(Customer.class);
        query.select(from.get(Customer_.id));
        return getEntityManager().createQuery(query).getResultList();
    }
}
