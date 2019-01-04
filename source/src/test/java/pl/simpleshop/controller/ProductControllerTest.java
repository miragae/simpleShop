package pl.simpleshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import pl.simpleshop.dao.ProductDaoLocal;
import pl.simpleshop.model.Category;
import pl.simpleshop.model.Product;

/**
 *
 * @author Michał Lal
 */
public class ProductControllerTest {
    @Mock
    private ProductDaoLocal productDao;
    

    @InjectMocks
    private ProductController productController;
    
    private List<Product> mockProductList;
    
    private final Category testCategory = new Category(1L, "cat1", "desc1");
    private final Product testProduct = new Product("prod1", testCategory);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockProductList = new ArrayList<>();
        mockProductList.add(testProduct);
        mockProductList.add(new Product("prod2", testCategory));
        mockProductList.add(new Product("prod3", new Category(2L, "cat2", "desc2")));
        
        when(productDao.getAllProductsForCategory(any())).thenAnswer((Answer) i -> {
            return mockProductList.stream().filter(prod -> prod.getCategory().equals((Category)i.getArguments()[0])).collect(Collectors.toList());
        });
        
    }
    
    @Test
    public void testUpdateProductList() {
        productController.setProductList(null);
        productController.updateProductList(testCategory);
        List<Product> productList = productController.getProductList();
        List<Product> expectedList = mockProductList.stream().filter(prod -> prod.getCategory().equals(testCategory)).collect(Collectors.toList());
        assertEquals(expectedList.size(), productList.size());
        assertTrue(expectedList.containsAll(productList));
    }

    @Test
    public void testAddToBasketNullOrderMap() {
        productController.setOrderMap(null);
        productController.addToBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertNotNull(productController.getOrderMap().get(testProduct));
        assertEquals(1, productController.getOrderMap().get(testProduct).intValue());
    }
    
    @Test
    public void testAddToBasketEmptyOrderMap() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        productController.setOrderMap(orderMap);
        productController.addToBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertNotNull(productController.getOrderMap().get(testProduct));
        assertEquals(1, productController.getOrderMap().get(testProduct).intValue());
    }
    
    @Test
    public void testAddToBasketFilledOrderMap() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        int count = 3;
        orderMap.put(testProduct, count);
        productController.setOrderMap(orderMap);
        productController.addToBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertNotNull(productController.getOrderMap().get(testProduct));
        assertEquals(count+1, productController.getOrderMap().get(testProduct).intValue());
    }

    @Test
    public void testRemoveFromBasketNullOrderMap() {
        productController.setOrderMap(null);
        productController.removeFromBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertTrue(productController.getOrderMap().isEmpty());
    }

    @Test
    public void testRemoveFromBasketEmptyOrderMap() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        productController.setOrderMap(orderMap);
        productController.removeFromBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertTrue(productController.getOrderMap().isEmpty());
    }
    
    @Test
    public void testRemoveFromBasketOrderMapNotContainingProduct() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        orderMap.put(new Product("prod2"), 2);
        orderMap.put(new Product("prod3"), 3);
        int initialSize = orderMap.size();
        productController.setOrderMap(orderMap);
        productController.removeFromBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertEquals(initialSize, productController.getOrderMap().size());
    }
    
    @Test
    public void testRemoveFromBasketOrderMapContainingOneProduct() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        orderMap.put(testProduct, 1);
        orderMap.put(new Product("prod2"), 2);
        orderMap.put(new Product("prod3"), 3);
        int initialSize = orderMap.size();
        productController.setOrderMap(orderMap);
        productController.removeFromBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertFalse(productController.getOrderMap().containsKey(testProduct));
        assertEquals(initialSize-1, productController.getOrderMap().size());
    }
    
    @Test
    public void testRemoveFromBasketOrderMapContainingMoreProducts() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        int count = 3;
        orderMap.put(testProduct, count);
        orderMap.put(new Product("prod2"), 2);
        orderMap.put(new Product("prod3"), 3);
        int initialSize = orderMap.size();
        productController.setOrderMap(orderMap);
        productController.removeFromBasket(testProduct);
        assertNotNull(productController.getOrderMap());
        assertTrue(productController.getOrderMap().containsKey(testProduct));
        assertEquals(initialSize, productController.getOrderMap().size());
        assertEquals(count-1, productController.getOrderMap().get(testProduct).intValue());
    }
    
    @Test
    public void testGetBasketListNullOrderMap() {
        productController.setOrderMap(null);
        List<Product> resultList = productController.getBasketList();
        assertNull(resultList);
    }
    
    @Test
    public void testGetBasketListEmptyOrderMap() {
        productController.setOrderMap(new HashMap<>());
        List<Product> resultList = productController.getBasketList();
        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
    }
    
    @Test
    public void testGetBasketListFilledOrderMap() {
        HashMap<Product, Integer> orderMap = new HashMap<>();
        orderMap.put(new Product("prod2"), 2);
        orderMap.put(new Product("prod3"), 3);
        int initialSize = orderMap.size();
        productController.setOrderMap(orderMap);
        List<Product> resultList = productController.getBasketList();
        assertNotNull(resultList);
        assertEquals(initialSize, resultList.size());
        assertTrue(resultList.containsAll(orderMap.keySet()));
    }
    
}
