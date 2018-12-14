package pl.simpleshop.dao;

import pl.simpleshop.model.Shipper;

import javax.ejb.Stateless;

@Stateless
public class ShipperDao extends AbstractDao<Shipper> implements ShipperDaoLocal{
    @Override
    public Class getEntityClass() {
        return Shipper.class;
    }
}
