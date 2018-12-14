package pl.simpleshop.dao;

import pl.simpleshop.model.Order;
import pl.simpleshop.model.OrderDetails;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderDetailsDaoLocal {
    void save (OrderDetails orderDetails);
    void remove (OrderDetails orderDetails);
    List<OrderDetails> getOrderDetailsForOrder(Order order);
}
