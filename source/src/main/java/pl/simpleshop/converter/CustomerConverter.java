package pl.simpleshop.converter;

import pl.simpleshop.dao.CustomerDaoLocal;
import pl.simpleshop.model.Customer;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("customerConverter")
@RequestScoped
public class CustomerConverter implements Converter {

    @Inject
    private CustomerDaoLocal customerDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return customerDao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Customer) value).getId();
    }
}
