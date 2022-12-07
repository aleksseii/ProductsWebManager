package ru.aleksseii.service;

import com.google.inject.Inject;
import generated.tables.pojos.Company;
import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.dao.ProductDAO;
import ru.aleksseii.rest.dto.ProductDTO;

import java.util.ArrayList;
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
    public @NotNull List<@NotNull ProductDTO> getAllProducts() {
        return productDAO.all()
                .stream()
                .map(p -> {
                    final Company company = companyDAO.get(p.getCompanyId());
                    assert company != null;
                    return new ProductDTO(p, company.getCompanyName());
                }).toList();
    }

    @Override
    public @NotNull List<@NotNull ProductDTO> getProductsByCompany(@NotNull String companyName) {

        final Company company = companyDAO.get(companyName);
        if (company == null) {
            return new ArrayList<>();
        }
        List<@NotNull Product> products = productDAO.getByCompany(company.getCompanyId());
        return products
                .stream()
                .map(p -> new ProductDTO(p, companyName))
                .toList();
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

    /**
     * @param productName name of product to delete entities by
     * @return amount of deleted entities
     */
    @Override
    public int deleteProductsByName(@NotNull String productName) {

        return productDAO.delete(productName);
    }
}
