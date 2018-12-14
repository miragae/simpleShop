package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import pl.simpleshop.model.*;
import pl.simpleshop.model.Order;

/**
 * @author Michał Lal
 */
@Stateless
public class ProductDao extends AbstractDao<Product> implements ProductDaoLocal {

    @Override
    public List<Product> getAllProductsForCategory(Category category) {
        return getAllProductsForObject(category, Product_.category);
    }

    @Override
    public List<Product> getAllProductsForSupplier(Supplier supplier) {
        return getAllProductsForObject(supplier, Product_.supplier);
    }

    @Override
    public List<Customer> getAllCustomersForProduct(Product product) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<OrderDetails> from = query.from(OrderDetails.class);
        Join<OrderDetails, Order> orderJoin = from.join(OrderDetails_.order);
        Join<Order, Customer> customerJoin = orderJoin.join(Order_.customer);
        query.where(cb.equal(from.get(OrderDetails_.product), product));
        query.orderBy(cb.asc(customerJoin.get(Customer_.id)));
        query.select(customerJoin);
        query.distinct(true);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Employee> getAllEmployeesForProduct(Product product) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<OrderDetails> from = query.from(OrderDetails.class);
        Join<OrderDetails, Order> orderJoin = from.join(OrderDetails_.order);
        Join<Order, Employee> employeeJoin = orderJoin.join(Order_.employee);
        query.where(cb.equal(from.get(OrderDetails_.product), product));
        query.distinct(true);
        query.orderBy(cb.asc(employeeJoin.get(Employee_.id)));
        query.select(employeeJoin);
        return getEntityManager().createQuery(query).getResultList();
    }

    private List<Product> getAllProductsForObject(Object object, SingularAttribute attribute) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> from = query.from(Product.class);
        query.where(cb.equal(from.get(attribute), object));
        query.orderBy(cb.asc(from.get(Product_.id)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Class getEntityClass() {
        return Product.class;
    }

}
