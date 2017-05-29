package pl.simpleshop.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import static org.primefaces.component.contextmenu.ContextMenu.PropertyKeys.event;
import pl.simpleshop.dao.CategoryDaoLocal;
import pl.simpleshop.dao.ProductDaoLocal;
import pl.simpleshop.model.Category;
import pl.simpleshop.model.Product;

/**
 *
 * @author Michał Lal
 */
@Named("productController")
@SessionScoped
public class ProductController implements Serializable{

    @EJB
    private ProductDaoLocal productDao;
    private List<Product> productList;
    private Map<Product, Integer> orderMap = null;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    
    public void updateProductList(Category category){
        productList = productDao.getAllProductsForCategory(category);
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(HashMap<Product, Integer> orderMap) {
        this.orderMap = orderMap;
    }
    
    public void addToBasket(Product product){
        if(orderMap == null){
            orderMap = new HashMap<>();
        }
        
        int count = orderMap.containsKey(product) ? orderMap.get(product) : 0;
        orderMap.put(product, count + 1);
        FacesMessage msg = new FacesMessage("Added to basket", product.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void removeFromBasket(Product product){
        int count = orderMap.containsKey(product) ? orderMap.get(product) : 0;
        if(count <= 1){
            orderMap.remove(product);
        } else {
            orderMap.put(product, count - 1);
        }
        FacesMessage msg = new FacesMessage("Removed from basket", product.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public List<Product> getBasketList(){
        return orderMap == null ? null : orderMap.keySet().stream().collect(Collectors.toList());
    }
}

