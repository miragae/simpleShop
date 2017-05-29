package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Local;
import pl.simpleshop.model.Category;

/**
 *
 * @author Michał Lal
 */
@Local
public interface CategoryDaoLocal{
    public List<Category> getAll();
    public Category find(Long id);
}
