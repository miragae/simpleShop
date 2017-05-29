package pl.simpleshop.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.simpleshop.dao.SimpleOrderDaoLocal;
import pl.simpleshop.dao.SimpleOrderPositionDaoLocal;
import pl.simpleshop.model.Product;
import pl.simpleshop.model.SimpleOrder;
import pl.simpleshop.model.SimpleOrderPosition;

/**
 *
 * @author Michał Lal
 */
@Named("orderController")
@SessionScoped
public class OrderController implements Serializable{
    
    @EJB
    private SimpleOrderPositionDaoLocal simpleOrderPositionDao;
    @EJB
    private SimpleOrderDaoLocal simpleOrderDao;
    
    @Inject
    private ProductController productController;
    private SimpleOrder currentOrder = null;

    public SimpleOrder getCurrentOrder() {
        if(currentOrder == null){
            currentOrder = new SimpleOrder();
        }
        return currentOrder;
    }

    public void setCurrentOrder(SimpleOrder currentOrder) {
        this.currentOrder = currentOrder;
    }
    
    public void order(){
        simpleOrderDao.save(currentOrder);
        Map<Product,Integer> orderedProductsMap = productController.getOrderMap();
        
        orderedProductsMap.forEach((product,count) -> {
            SimpleOrderPosition orderPosition = new SimpleOrderPosition();
            orderPosition.setOrder(currentOrder);
            orderPosition.setProduct(product);
            orderPosition.setCount(count);
            simpleOrderPositionDao.save(orderPosition);
        });
        
        currentOrder = new SimpleOrder();
        productController.setOrderMap(new HashMap<>());
    }
    

}
