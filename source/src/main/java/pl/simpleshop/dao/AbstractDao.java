package pl.simpleshop.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDao<T> {
    
    @PersistenceContext
    private EntityManager em;

    public void save(T item) {
        em.persist(item);
    }

    public void update(T item) {
        em.merge(item);
    }

    public void remove(T item) {
        em.remove(
                em.contains(item)
                ? item
                : em.merge(item)
        );
    }
    public List<T> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        query.select(from);
        return em.createQuery(query).getResultList();
    }
    
    public T find(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        query.where(cb.equal(from.get("id"), id));
        query.select(from);
        List<T> resultList = getEntityManager().createQuery(query).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }
    
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public abstract Class getEntityClass();
}
