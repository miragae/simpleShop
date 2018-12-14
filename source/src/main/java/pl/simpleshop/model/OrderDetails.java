package pl.simpleshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private int id;
    private Order order;
    private Product product;
    private Float unitPrice;
    private Short quantity;
    private Float discount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "OrderID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "ProductID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}
