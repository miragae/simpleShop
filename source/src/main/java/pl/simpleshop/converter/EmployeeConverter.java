package pl.simpleshop.converter;

import pl.simpleshop.dao.EmployeeDaoLocal;
import pl.simpleshop.model.Employee;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employeeConverter")
@RequestScoped
public class EmployeeConverter implements Converter {

    @Inject
    private EmployeeDaoLocal employeeDao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return employeeDao.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Employee) value).getId());
    }
}
