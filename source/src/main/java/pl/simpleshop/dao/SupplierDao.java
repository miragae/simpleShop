package pl.simpleshop.dao;

import pl.simpleshop.model.Supplier;

import javax.ejb.Stateless;

@Stateless
public class SupplierDao  extends AbstractDao<Supplier> implements SupplierDaoLocal {
    @Override
    public Class getEntityClass() {
        return Supplier.class;
    }
}
