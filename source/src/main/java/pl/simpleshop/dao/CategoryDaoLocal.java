package pl.simpleshop.dao;

import java.util.List;
import javax.ejb.Local;
import pl.simpleshop.model.Category;


@Local
public interface CategoryDaoLocal{
    List<Category> getAll();
    Category find(int id);
}
