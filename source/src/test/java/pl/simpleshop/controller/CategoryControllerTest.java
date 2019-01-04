package pl.simpleshop.controller;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import pl.simpleshop.dao.CategoryDaoLocal;
import pl.simpleshop.model.Category;

/**
 *
 * @author Michał Lal
 */
public class CategoryControllerTest {

    @Spy
    private CategoryDaoLocal categoryDao;

    @Mock
    private ProductController productController;

    @InjectMocks
    private CategoryController categoryController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Category cat1 = new Category(1L, "cat1", "desc1");
        Category cat2 = new Category(2L, "cat2", "desc2");
        Category cat3 = new Category(3L, "cat3", "desc3");
        List<Category> mockAllCategories = Arrays.asList(cat1, cat2, cat3);
        
        doReturn(mockAllCategories).when(categoryDao).getAll();
        doReturn(cat1).when(categoryDao).find(1L);
        doReturn(cat2).when(categoryDao).find(2L);
        doReturn(cat3).when(categoryDao).find(3L);
        doReturn(null).when(categoryDao).find(longThat(l -> l < 1 || l > 3));
    }

    @Test
    public void testGetCategoryMenuWhenNullShouldReturnNew() {
        categoryController.setCategoryMenu(null);
        MenuModel resultModel = categoryController.getCategoryMenu();
        assertNotNull(resultModel);
    }

    @Test
    public void testGetCategoryMenu() {
        MenuModel menuModel = new DefaultMenuModel();
        categoryController.setCategoryMenu(menuModel);
        MenuModel resultModel = categoryController.getCategoryMenu();
        assertEquals(menuModel, resultModel);
    }

    @Test
    public void testCreateCategoryMenu() {
        MenuModel resultModel = categoryController.createCategoryMenu();
        assertNotNull(resultModel);
        assertNotNull(resultModel.getElements());
        assertEquals(categoryDao.getAll().size(), resultModel.getElements().size());
        resultModel.getElements().forEach(element -> {
            assertEquals(DefaultMenuItem.class, element.getClass());
            assertNotNull(((DefaultMenuItem) element).getValue());
            assertTrue(categoryDao.getAll().stream().anyMatch(category -> ((DefaultMenuItem) element).getValue().equals(category.getName())));
        });
    }

    @Test
    public void testSelectCategoryWithValidId() {
        Long categoryId = 1L;
        categoryController.selectCategory(categoryId);
        assertNotNull(categoryController.getSelectedCategory());
        assertEquals(categoryId, categoryController.getSelectedCategory().getId());
    }
    
    @Test
    public void testSelectCategoryWithInvalidIdShouldSelectNull() {
        Long categoryId = 0L;
        categoryController.selectCategory(categoryId);
        assertEquals(categoryController.getSelectedCategory(), null);
    }
}
