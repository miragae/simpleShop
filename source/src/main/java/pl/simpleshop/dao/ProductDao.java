package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.simpleshop.model.Category;
import pl.simpleshop.model.Product;
import pl.simpleshop.model.Product_;

/**
 *
 * @author Michał Lal
 */
@Stateless
public class ProductDao extends AbstractDao<Product> implements ProductDaoLocal {
    
    
    @Override
    public List<Product> getAllProductsForCategory(Category category) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> from = query.from(Product.class);
        query.where(cb.equal(from.get(Product_.category),category));
        query.orderBy(cb.asc(from.get(Product_.id)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Class getEntityClass() {
        return Product.class;
    }
    
}
