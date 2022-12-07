package ru.aleksseii.dao;

import com.zaxxer.hikari.HikariDataSource;
import generated.tables.pojos.Company;
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

final class CompanyDAOTest {

    private static final int EXISTING_ID1 = 1;
    private static final @NotNull String EXISTING_NAME1 = "company 1";
    private static final @NotNull String NEW_NAME = "new name";
    private static final Company EXISTING_COMPANY1 = new Company(EXISTING_ID1, EXISTING_NAME1);


    private static final @NotNull HikariDataSource DATA_SOURCE = DataSourceManager.getHikariDataSource();

    private final @NotNull CompanyDAO companyDAO = new CompanyDAO(DATA_SOURCE);

    private static final @NotNull List<Company> ALL_COMPANIES = List.of(
            EXISTING_COMPANY1,
            new Company(2, "company 2"),
            new Company(3, "company 3")
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

        Company company = companyDAO.get(EXISTING_ID1);
        assertEquals(EXISTING_COMPANY1, company);
    }

    @Test
    void shouldGetByName() {

        Company company = companyDAO.get(EXISTING_NAME1);
        assertEquals(EXISTING_COMPANY1, company);
    }

    @Test
    void shouldGetAllCompanies() {

        List<@NotNull Company> companies = companyDAO.all();

        assertThat(ALL_COMPANIES, containsInAnyOrder(companies.toArray()));
    }

    @Test
    void shouldUpdateCompany() {

        int id = EXISTING_ID1;
        Company expectedCompany = new Company(id, NEW_NAME);

        int sizeBefore = companyDAO.all().size();
        companyDAO.update(expectedCompany);
        int sizeAfter = companyDAO.all().size();

        assertEquals(sizeBefore, sizeAfter);
        assertEquals(expectedCompany, companyDAO.get(id));
    }

    @Test
    void shouldSaveNewCompany() {


        int sizeBefore = companyDAO.all().size();
        int nextId = sizeBefore + 1;
        Company newCompany = new Company(nextId, NEW_NAME);

        companyDAO.save(newCompany);
        int sizeAfter = companyDAO.all().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(newCompany, companyDAO.get(nextId));
    }

    @Test
    void shouldDeleteById() {

        int id = EXISTING_ID1;
        int sizeBefore = companyDAO.all().size();
        companyDAO.delete(id);
        int sizeAfter = companyDAO.all().size();

        assertEquals(sizeBefore - 1, sizeAfter);
        assertNull(companyDAO.get(id));
    }

    @Test
    void shouldDeleteByName() {

        String name = EXISTING_NAME1;
        int sizeBefore = companyDAO.all().size();
        companyDAO.delete(name);
        int sizeAfter = companyDAO.all().size();

        assertEquals(sizeBefore - 1, sizeAfter);
        assertNull(companyDAO.get(name));
    }

    @Test
    void shouldDeleteAllCompanies() {

        assertFalse(companyDAO.all().isEmpty());
        companyDAO.deleteAll();
        assertTrue(companyDAO.all().isEmpty());
    }
}
