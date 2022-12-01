package ru.aleksseii.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.dao.ProductDAO;
import ru.aleksseii.model.Company;
import ru.aleksseii.model.Product;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

final class ProductServiceImplTest {

    public static final @NotNull List<@NotNull Product> ALL_PRODUCTS = List.of(
            new Product(1,  "product 1", 5, 1),
            new Product(2,  "product 2", 3, 2),
            new Product(3,  "product 3",10, 3),
            new Product(4,  "product 4", 2, 1),
            new Product(5,  "product 5", 1, 2),
            new Product(6,  "product 6", 7, 3),
            new Product(7,  "product 7", 9, 1),
            new Product(8,  "product 8", 4, 2),
            new Product(9,  "same name", 8, 3),
            new Product(10, "same name", 6, 1)
    );

    private static final @NotNull String NON_EXISTING_COMPANY_NAME = "random name";
    private static final @NotNull String EXISTING_COMPANY_NAME = "random existing name";
    private static final @NotNull String NEW_PRODUCT_NAME = "new product name";
    private static final int RANDOM_AMOUNT = 23;

    @Mock
    private @NotNull ProductDAO productDAO;
    @Mock
    private @NotNull CompanyDAO companyDAO;

    private @NotNull ProductService productService;

    @BeforeEach
    void init() {

        productDAO = mock(ProductDAO.class);
        companyDAO = mock(CompanyDAO.class);
        productService = new ProductServiceImpl(productDAO, companyDAO);
    }

    @Test
    void getAllProducts() {

        List<@NotNull Product> expectedProducts = ALL_PRODUCTS;
        when(productDAO.all()).thenReturn(expectedProducts);

        List<@NotNull Product> actualProducts = productService.getAllProducts();

        assertThat(expectedProducts, containsInAnyOrder(actualProducts.toArray()));
    }

    @Test
    void saveProduct() {

        when(companyDAO.get(NON_EXISTING_COMPANY_NAME))
                .thenReturn(null)
                .thenReturn(new Company(4, NON_EXISTING_COMPANY_NAME))
                .thenReturn(new Company(5, EXISTING_COMPANY_NAME));

        productService.saveProduct(NEW_PRODUCT_NAME, NON_EXISTING_COMPANY_NAME, RANDOM_AMOUNT);
        productService.saveProduct(NEW_PRODUCT_NAME, NON_EXISTING_COMPANY_NAME, RANDOM_AMOUNT);

        verify(companyDAO, times(3)).get(anyString());
        verify(productDAO, times(2)).save(ArgumentMatchers.any(Product.class));
    }
}
