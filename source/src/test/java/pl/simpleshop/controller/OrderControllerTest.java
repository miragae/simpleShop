package pl.simpleshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.simpleshop.dao.SimpleOrderDaoLocal;
import pl.simpleshop.dao.SimpleOrderPositionDaoLocal;
import pl.simpleshop.model.Product;
import pl.simpleshop.model.SimpleOrder;
import pl.simpleshop.model.SimpleOrderPosition;

/**
 * @author Michał Lal
 */
public class OrderControllerTest {

    @Mock
    private SimpleOrderPositionDaoLocal simpleOrderPositionDao;
    @Mock
    private SimpleOrderDaoLocal simpleOrderDao;
    @Mock
    private ProductController productController;

    private Map<Product, Integer> mockOrderedProductsMap;

    private List<SimpleOrderPosition> mockSimpleOrderPostitionList;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockOrderedProductsMap = new HashMap<>();
        Product prod1 = new Product("prod1");
        Product prod2 = new Product("prod2");
        Product prod3 = new Product("prod3");
        mockOrderedProductsMap.put(prod1, 2);
        mockOrderedProductsMap.put(prod2, 4);
        mockOrderedProductsMap.put(prod3, 6);
        when(productController.getOrderMap()).thenReturn(mockOrderedProductsMap);

        mockSimpleOrderPostitionList = new ArrayList<>();
        doAnswer((Answer) (InvocationOnMock i) -> {
            SimpleOrderPosition pos = (SimpleOrderPosition) i.getArguments()[0];
            mockSimpleOrderPostitionList.add(pos);
            return null;
        }).when(simpleOrderPositionDao).save(any());
    }

    @Test
    public void testGetCurrentOrderWhenNullShouldReturnNew() {
        orderController.setCurrentOrder(null);
        SimpleOrder resultOrder = orderController.getCurrentOrder();
        assertNotNull(resultOrder);
    }

    @Test
    public void testOrder() {
        SimpleOrder order = new SimpleOrder();
        order.setAddress("addr1");
        order.setFirstName("fn1");
        order.setLastName("ln1");
        order.setId(1L);

        orderController.setCurrentOrder(order);
        orderController.order();

        assertEquals(mockOrderedProductsMap.size(), mockSimpleOrderPostitionList.size());
        assertTrue(mockSimpleOrderPostitionList.stream().allMatch(pos -> order.equals(pos.getOrder())));
        assertTrue(mockSimpleOrderPostitionList.stream().allMatch(pos -> mockOrderedProductsMap.containsKey(pos.getProduct())));
        assertTrue(mockSimpleOrderPostitionList.stream().allMatch(pos -> pos.getCount().equals(mockOrderedProductsMap.get(pos.getProduct()))));
        assertNotEquals(order, orderController.getCurrentOrder());
        assertNotNull(orderController.getCurrentOrder());
        assertNull(orderController.getCurrentOrder().getId());
        assertNull(orderController.getCurrentOrder().getAddress());
        assertNull(orderController.getCurrentOrder().getFirstName());
        assertNull(orderController.getCurrentOrder().getLastName());
    }

}
