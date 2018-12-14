package pl.simpleshop.dao;

import pl.simpleshop.model.Shipper;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ShipperDaoLocal {
    Shipper find(int id);
    List<Shipper> getAll();
}
