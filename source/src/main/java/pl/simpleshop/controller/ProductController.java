package pl.simpleshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pl.simpleshop.dao.ProductDaoLocal;
import pl.simpleshop.model.Category;
import pl.simpleshop.model.Customer;
import pl.simpleshop.model.Employee;
import pl.simpleshop.model.Product;


@Named("productController")
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private ProductDaoLocal productDao;

    private List<Product> productList;
    private List<Customer> customerList;
    private List<Employee> employeeList;
    private Map<Product, Integer> orderMap = null;
    private boolean productOrderSwitcher = true;

    public List<Product> getProductList() {
        return productList;
    }

    public Map<Product, Integer> getOrderMap() {
        if (orderMap == null) {
            orderMap = new HashMap<>();
        }
        return orderMap;
    }

    public void setOrderMap(Map<Product, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public boolean isProductOrderSwitcher() {
        return productOrderSwitcher;
    }

    public void updateCustomers(Product product) {
        customerList = productDao.getAllCustomersForProduct(product);
    }

    public void updateEmployees(Product product) {
        employeeList = productDao.getAllEmployeesForProduct(product);

    }

    public void updateProductList(Category category) {
        productList = productDao.getAllProductsForCategory(category);
    }

    public void addToBasket(Product product) {
        getOrderMap();

        int count = basketProductCount(product);
        orderMap.put(product, count + 1);
        FacesMessage msg = new FacesMessage("Added to basket", product.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void removeFromBasket(Product product) {
        int count = basketProductCount(product);
        if (count <= 1) {
            orderMap.remove(product);
        } else {
            orderMap.put(product, count - 1);
        }
        FacesMessage msg = new FacesMessage("Removed from basket", product.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Product> getBasketList() {
        return orderMap == null ? null : new ArrayList<>(orderMap.keySet());
    }

    public boolean boughtAll(Product product) {
        return product != null && product.getUnitsInStock() == basketProductCount(product);
    }

    public void switchProductOrder() {
        productOrderSwitcher = !productOrderSwitcher;
    }

    private int basketProductCount(Product product) {
        getOrderMap();
        return orderMap.getOrDefault(product, 0);
    }
}

