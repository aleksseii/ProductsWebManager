package ru.aleksseii.dao;

import com.zaxxer.hikari.HikariDataSource;
import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aleksseii.common.database.DataSourceManager;
import ru.aleksseii.common.database.FlywayInitializer;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;


final class ProductDAOTest {

    private static final int EXISTING_ID10 = 10;
    private static final @NotNull String EXISTING_NAME10 = "same name";
    private static final @NotNull String NEW_NAME = "new name";

    private static final int EXISTING_COMPANY_ID1 = 1;
    private static final int EXISTING_COMPANY_ID3 = 3;

    private static final int NEW_AMOUNT = 17;

    private static final @NotNull Product EXISTING_PRODUCT9 = new Product(9, EXISTING_NAME10, 8, 3);
    private static final @NotNull Product EXISTING_PRODUCT10 = new Product(EXISTING_ID10, EXISTING_NAME10, 6, 1);

    private static final @NotNull HikariDataSource DATA_SOURCE = DataSourceManager.getHikariDataSource();

    private final @NotNull ProductDAO productDAO = new ProductDAO(DATA_SOURCE);


    public static final @NotNull List<@NotNull Product> ALL_PRODUCTS = List.of(
            new Product(1,  "product 1", 5,           1),
            new Product(2,  "product 2", 3,           2),
            new Product(3,  "product 3",10,           3),
            new Product(4,  "product 4", 2,           1),
            new Product(5,  "product 5", 1,           2),
            new Product(6,  "product 6", 7,           3),
            new Product(7,  "product 7", 9,           1),
            new Product(8,  "product 8", 4,           2),
            EXISTING_PRODUCT9,
            EXISTING_PRODUCT10
    );

    @AfterAll
    static void closeDataSource() {

        FlywayInitializer.initDB();
        DATA_SOURCE.close();
    }

    @BeforeEach
    void initDB() {
        FlywayInitializer.initDB();
    }

    @Test
    void shouldGetById() {

        Product product = productDAO.get(EXISTING_ID10);
        assertEquals(EXISTING_PRODUCT10, product);
    }

    @Test
    void shouldGetProductsByName() {

        List<@NotNull Product> products = productDAO.get(EXISTING_NAME10);
        assertThat(products, containsInAnyOrder(EXISTING_PRODUCT9, EXISTING_PRODUCT10));
    }

    @Test
    void shouldGetAllProducts() {

        List<@NotNull Product> products = productDAO.all();
        assertThat(ALL_PRODUCTS, containsInAnyOrder(products.toArray()));
    }

    @Test
    void shouldGetProductsByCompanyId() {

        List<Product> expectedProducts = List.of(
                ALL_PRODUCTS.get(0),
                ALL_PRODUCTS.get(3),
                ALL_PRODUCTS.get(6),
                ALL_PRODUCTS.get(9)
        );
        List<@NotNull Product> products = productDAO.getByCompany(EXISTING_COMPANY_ID1);
        assertThat(expectedProducts, containsInAnyOrder(products.toArray()));
    }

    @Test
    void shouldUpdateProduct() {

        int id = EXISTING_ID10;
        Product expectedProduct = new Product(id, NEW_NAME, NEW_AMOUNT, EXISTING_COMPANY_ID3);

        int sizeBefore = productDAO.all().size();
        productDAO.update(expectedProduct);
        int sizeAfter = productDAO.all().size();

        assertEquals(sizeBefore, sizeAfter);
        assertEquals(expectedProduct, productDAO.get(id));
    }

    @Test
    void shouldSaveProduct() {

        int sizeBefore = productDAO.all().size();
        int nextId = sizeBefore + 1;
        Product newProduct = new Product(nextId, NEW_NAME, NEW_AMOUNT, EXISTING_COMPANY_ID3);

        productDAO.save(newProduct);
        int sizeAfter = productDAO.all().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(newProduct, productDAO.get(nextId));
    }

    @Test
    void shouldDeleteById() {

        int id = EXISTING_ID10;
        int sizeBefore = productDAO.all().size();
        productDAO.delete(id);
        int sizeAfter = productDAO.all().size();

        assertEquals(sizeBefore - 1, sizeAfter);
        assertNull(productDAO.get(id));
    }

    @Test
    void shouldDeleteProductsByName() {

        String name = EXISTING_NAME10;

        int sizeBefore = productDAO.all().size();
        int deletedAmount = productDAO.delete(name);
        int sizeAfter = productDAO.all().size();

        assertEquals(sizeBefore - deletedAmount, sizeAfter);
        assertTrue(productDAO.get(name).isEmpty());
    }

    @Test
    void shouldDeleteAll() {

        assertFalse(productDAO.all().isEmpty());
        productDAO.deleteAll();
        assertTrue(productDAO.all().isEmpty());
    }
}
