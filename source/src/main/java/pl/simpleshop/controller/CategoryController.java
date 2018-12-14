package pl.simpleshop.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import pl.simpleshop.dao.CategoryDaoLocal;
import pl.simpleshop.model.Category;


@Named("categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    @EJB
    private CategoryDaoLocal categoryDao;

    @Inject
    private ProductController productController;

    private MenuModel categoryMenu = null;
    private Category selectedCategory = null;

    public MenuModel getCategoryMenu() {
        if (categoryMenu == null) {
            categoryMenu = createCategoryMenu();
        }
        return categoryMenu;
    }

    public void setCategoryMenu(MenuModel categoryMenu) {
        this.categoryMenu = categoryMenu;
    }

    public void selectCategory(int categoryId) {
        selectedCategory = categoryDao.find(categoryId);
        productController.updateProductList(selectedCategory);
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    private MenuModel createCategoryMenu() {
        MenuModel model = new DefaultMenuModel();
        List<Category> categories = categoryDao.getAll();
        categories.forEach(category -> {
            DefaultMenuItem item = new DefaultMenuItem(category.getName());
            item.setCommand("#{categoryController.selectCategory(" + category.getId() + ")}");
            item.setUpdate("centerPanel, centerPanelHeader, menuPanel");
            model.addElement(item);
        });

        return model;
    }
}
