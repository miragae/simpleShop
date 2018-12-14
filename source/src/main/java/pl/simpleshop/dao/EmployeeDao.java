package pl.simpleshop.dao;

import pl.simpleshop.model.Employee;
import pl.simpleshop.model.Employee_;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmployeeDao extends AbstractDao<Employee> implements EmployeeDaoLocal {
    @Override
    public Class getEntityClass() {
        return Employee.class;
    }

    @Override
    public List<Employee> findByQuery(String stringQuery) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> from = query.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();
        for(String subQuery : stringQuery.split(" ")) {
            predicates.add(cb.like(from.get(Employee_.firstName), subQuery + '%'));
            predicates.add(cb.like(from.get(Employee_.lastName), subQuery + '%'));
            predicates.add(cb.like(from.get(Employee_.country), subQuery + '%'));
        }
        query.where(cb.or(predicates.toArray(new Predicate[0])));
        query.orderBy(cb.asc(from.get(Employee_.id)));
        query.select(from);
        return getEntityManager().createQuery(query).getResultList();
    }
}
