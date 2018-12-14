package pl.simpleshop.dao;

import pl.simpleshop.model.Employee;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EmployeeDaoLocal {
    Employee find(int id);
    List<Employee> findByQuery(String query);
}
