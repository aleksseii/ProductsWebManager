package ru.aleksseii.service;

import com.google.inject.Inject;
import generated.tables.pojos.Company;
import generated.tables.pojos.Product;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.dao.ProductDAO;

import java.util.List;

public final class ProductServiceImpl implements ProductService {

    private final @NotNull ProductDAO productDAO;

    @Getter
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
        final Product product = new Product(productName, amount, company.getCompanyId());
        productDAO.save(product);
    }
}
