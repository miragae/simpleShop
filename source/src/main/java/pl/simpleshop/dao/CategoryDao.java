package pl.simpleshop.dao;

import javax.ejb.Stateless;
import pl.simpleshop.model.Category;


@Stateless
public class CategoryDao extends AbstractDao<Category> implements CategoryDaoLocal {

    @Override
    public Class getEntityClass() {
        return Category.class;
    }
    
}
