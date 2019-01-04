package pl.simpleshop.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Michał Lal
 */
public abstract class AbstractDao<T> {
    
    @PersistenceContext
    private EntityManager em;

    public void save(T item) {
        getEntityManager().persist(item);
    }
    
    public List<T> getAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public T find(Long id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> from = query.from(getEntityClass());
        query.where(cb.equal(from.get("id"), id));
        query.select(from);
        return getEntityManager().createQuery(query).getSingleResult();
    }
    
    protected EntityManager getEntityManager(){
        return em;
    }
    
    public abstract Class getEntityClass();
}
