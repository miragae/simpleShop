package pl.simpleshop.converter;

import pl.simpleshop.dao.ShipperDaoLocal;
import pl.simpleshop.model.Shipper;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("shipperConverter")
@RequestScoped
public class ShipperConverter implements Converter {

    @Inject
    private ShipperDaoLocal shipperDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return shipperDao.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Shipper) value).getId());
    }
}
