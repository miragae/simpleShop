package pl.simpleshop.dao;

import pl.simpleshop.model.Supplier;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SupplierDaoLocal {
    List<Supplier> getAll();
}
