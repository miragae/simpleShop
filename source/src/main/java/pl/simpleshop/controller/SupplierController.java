package pl.simpleshop.controller;

import pl.simpleshop.dao.ProductDaoLocal;
import pl.simpleshop.dao.SupplierDaoLocal;
import pl.simpleshop.model.Product;
import pl.simpleshop.model.Supplier;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Named("supplierController")
@SessionScoped
public class SupplierController implements Serializable {

    @EJB
    private ProductDaoLocal productDao;

    @EJB
    private SupplierDaoLocal supplierDao;

    @Inject
    private ProductController productController;

    private Supplier selectedSupplier;
    private List<Supplier> supplierList;
    private List<Product> supplierProducts;
    private boolean showAll = false;

    public boolean isShowAll() {
        return showAll;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public List<Product> getSupplierProducts() {
        return supplierProducts;
    }

    public void switchBetweenAllAndSelected() {
        if(showAll) {
            supplierList = singletonList(selectedSupplier);
        } else {
            supplierList = supplierDao.getAll();
        }
        showAll = !showAll;
    }

    public void updateProductList(Supplier supplier){
        supplierProducts = productDao.getAllProductsForSupplier(supplier);
    }

    public void selectSupplier(Supplier supplier) {
        selectedSupplier = supplier;
        supplierList = singletonList(supplier);
        showAll = false;
    }
}
