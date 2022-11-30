package ru.aleksseii.service;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.dao.ProductDAO;
import ru.aleksseii.model.Company;
import ru.aleksseii.model.Product;

import java.util.List;

public final class ProductServiceImpl implements ProductService {

    private final @NotNull ProductDAO productDAO;

    private final @NotNull CompanyDAO companyDAO;

    @Inject
    public ProductServiceImpl(@NotNull ProductDAO productDAO, @NotNull CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    public @NotNull List<@NotNull Product> getAllProducts() {
        return productDAO.all();
    }

    @Override
    public void saveProduct(@NotNull String productName,
                            @NotNull String companyName,
                            int amount) {

        Company company = companyDAO.get(companyName);

        if (company == null) {
            Company newCompany = new Company(companyName);
            companyDAO.save(newCompany);
            company = companyDAO.get(companyName);
        }

        assert company != null;
        final Product product = new Product(productName, amount, company.companyId());
        productDAO.save(product);
    }
}
