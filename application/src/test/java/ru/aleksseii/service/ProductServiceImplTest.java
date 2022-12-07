package ru.aleksseii.service;

import generated.tables.pojos.Company;
import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.dao.ProductDAO;
import ru.aleksseii.rest.dto.ProductDTO;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

final class ProductServiceImplTest {

    private static final @NotNull List<@NotNull Product> ALL_PRODUCTS = List.of(
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

    private static final @NotNull List<@NotNull ProductDTO> ALL_PRODUCT_DTOS = List.of(
            new ProductDTO(ALL_PRODUCTS.get(0), "company 1"),
            new ProductDTO(ALL_PRODUCTS.get(1), "company 2"),
            new ProductDTO(ALL_PRODUCTS.get(2), "company 3"),
            new ProductDTO(ALL_PRODUCTS.get(3), "company 1"),
            new ProductDTO(ALL_PRODUCTS.get(4), "company 2"),
            new ProductDTO(ALL_PRODUCTS.get(5), "company 3"),
            new ProductDTO(ALL_PRODUCTS.get(6), "company 1"),
            new ProductDTO(ALL_PRODUCTS.get(7), "company 2"),
            new ProductDTO(ALL_PRODUCTS.get(8), "company 3"),
            new ProductDTO(ALL_PRODUCTS.get(9), "company 1")
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
    void shouldGetAllProducts() {

        when(productDAO.all()).thenReturn(ALL_PRODUCTS);
        for (int i = 0; i < ALL_PRODUCTS.size(); i++) {
            final Product product = ALL_PRODUCTS.get(i);
            final ProductDTO productDTO = ALL_PRODUCT_DTOS.get(i);
            final Company company = new Company(product.getCompanyId(), productDTO.companyName());

            when(companyDAO.get(i + 1)).thenReturn(company);
        }

        List<@NotNull ProductDTO> actualProductDTOs = productService.getAllProducts();

        assertThat(ALL_PRODUCT_DTOS, containsInAnyOrder(actualProductDTOs.toArray()));

        verify(productDAO, times(1)).all();
    }

    @Test
    void shouldGetByCompany() {

        final int companyId = 1;
        final String companyName = "company 1";
        final Company company = new Company(companyId, companyName);

        final List<Product> soughtProducts = List.of(
                ALL_PRODUCTS.get(0),
                ALL_PRODUCTS.get(3),
                ALL_PRODUCTS.get(6),
                ALL_PRODUCTS.get(9)
        );

        final List<ProductDTO> expectedProductDTOs = List.of(
                ALL_PRODUCT_DTOS.get(0),
                ALL_PRODUCT_DTOS.get(3),
                ALL_PRODUCT_DTOS.get(6),
                ALL_PRODUCT_DTOS.get(9)
        );

        when(companyDAO.get(companyName)).thenReturn(company);
        when(productDAO.getByCompany(companyId)).thenReturn(soughtProducts);

        List<@NotNull ProductDTO> actualProductDTOs = productService.getProductsByCompany(companyName);
        assertThat(expectedProductDTOs, containsInAnyOrder(actualProductDTOs.toArray()));

        verify(companyDAO, times(1)).get(anyString());
        verify(productDAO, times(1)).getByCompany(anyInt());
    }

    @Test
    void shouldSaveProduct() {

        when(companyDAO.get(NON_EXISTING_COMPANY_NAME))
                .thenReturn(null)
                .thenReturn(new Company(4, NON_EXISTING_COMPANY_NAME))
                .thenReturn(new Company(5, EXISTING_COMPANY_NAME));

        productService.saveProduct(NEW_PRODUCT_NAME, NON_EXISTING_COMPANY_NAME, RANDOM_AMOUNT);
        productService.saveProduct(NEW_PRODUCT_NAME, NON_EXISTING_COMPANY_NAME, RANDOM_AMOUNT);

        verify(companyDAO, times(3)).get(anyString());
        verify(productDAO, times(2)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    void shouldDeleteByName() {

        final String nameToDeleteBy = "same name";
        final int amountOfSameNameProducts = 2;
        when(productDAO.delete(nameToDeleteBy)).thenReturn(amountOfSameNameProducts);

        assertEquals(amountOfSameNameProducts, productService.deleteProductsByName(nameToDeleteBy));

        verify(productDAO, times(1)).delete(anyString());
    }
}
