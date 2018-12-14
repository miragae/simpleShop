package pl.simpleshop.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.simpleshop.dao.*;
import pl.simpleshop.model.*;

@Named("orderController")
@SessionScoped
public class OrderController implements Serializable{
    
    @Inject
    private ProductController productController;
    @EJB
    private CustomerDaoLocal customerDao;
    @EJB
    private EmployeeDaoLocal employeeDao;
    @EJB
    private ShipperDaoLocal shipperDao;
    @EJB
    private OrderDaoLocal orderDao;
    @EJB
    private OrderDetailsDaoLocal orderDetailsDao;

    private Order currentOrder = null;
    private Customer customer = new Customer();
    private Employee employee = new Employee();

    private List<Shipper> shipperList;
    private List<Order> orderList;

    public Order getCurrentOrder() {
        if(currentOrder == null){
            currentOrder = new Order();
            currentOrder.setOrderDate(new Date());
            currentOrder.setEmployee(new Employee());
            currentOrder.setCustomer(new Customer());
        }
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Customer getCustomer() {
        return customer == null ? new Customer() : customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee == null ? new Employee() : employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Shipper> getShipperList() {
        if(shipperList == null) {
            shipperList = shipperDao.getAll();
        }
        return shipperList;
    }

    public List<Order> getOrderList() {
        if(orderList == null) {
            updateOrderList();
        }
        return orderList;
    }

    public void updateOrderList() {
        orderList = orderDao.getAllSortedDesc(Order_.orderDate);
    }

    public void selectShipper(Shipper shipper) {
        currentOrder.setShipVia(shipper);
    }

    public void createNewCustomer() {
        getCurrentOrder().setCustomer(new Customer());
        customer = null;
    }

    public void selectCustomer() {
        getCurrentOrder().setCustomer(customer);
    }

    public List<Customer> completeCustomers(String query) {
        return customerDao.findByQuery(query);
    }

    public List<Employee> completeEmployees(String query) {
        return employeeDao.findByQuery(query);
    }

    public void order(){
        if(customer != null) {
            currentOrder.setCustomer(customer);
        }
        customerDao.saveOrUpdate(currentOrder.getCustomer());

        currentOrder.setEmployee(employee);

        orderDao.save(currentOrder);

        Map<Product,Integer> orderedProductsMap = productController.getOrderMap();

        orderedProductsMap.forEach((product,quantity) -> {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(currentOrder);
            orderDetails.setProduct(product);
            orderDetails.setQuantity(quantity.shortValue());
            orderDetails.setUnitPrice(product.getUnitPrice());

            orderDetailsDao.save(orderDetails);
        });

        currentOrder = null;
        customer = null;
        employee = null;

        productController.setOrderMap(new HashMap<>());
    }

    public void deleteOrder(Order order) {
        List<OrderDetails> orderDetailsList = orderDetailsDao.getOrderDetailsForOrder(order);
        for(OrderDetails orderDetails : orderDetailsList) {
            orderDetailsDao.remove(orderDetails);
        }
        orderDao.remove(order);
        updateOrderList();
    }
}
