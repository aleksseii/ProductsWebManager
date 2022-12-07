package ru.aleksseii.dao;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import generated.tables.pojos.Product;
import generated.tables.records.ProductRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static generated.Tables.PRODUCT;

public final class ProductDAO implements CrudDAO<Product> {

    private final @NotNull HikariDataSource dataSource;

    @Inject
    public ProductDAO(@NotNull HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param id id to get product entity by
     * @return product entity by id if found, null otherwise
     */
    @Override
    public @Nullable Product get(int id) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final ProductRecord productRecord = context.fetchOne(PRODUCT, PRODUCT.PRODUCT_ID.equal(id));
            if (productRecord != null) {
                return productRecord.into(Product.class);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public @NotNull List<@NotNull Product> get(@NotNull String name) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<ProductRecord> productRecords = context.fetch(PRODUCT, PRODUCT.PRODUCT_NAME.equal(name));
            return productRecords.stream()
                    .map(productRecord -> productRecord.into(Product.class))
                    .toList();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull List<@NotNull Product> getByCompany(int companyId) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<ProductRecord> productRecords = context.fetch(PRODUCT, PRODUCT.COMPANY_ID.equal(companyId));

            return productRecords.stream()
                    .map(productRecord -> productRecord.into(Product.class))
                    .toList();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<@NotNull Product> all() {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<ProductRecord> productRecords = context.fetch(PRODUCT);
            return productRecords.stream()
                    .map(productRecord -> productRecord.into(Product.class))
                    .toList();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Product entity) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            context
                    .update(PRODUCT)
                    .set(PRODUCT.PRODUCT_NAME, entity.getProductName())
                    .set(PRODUCT.AMOUNT, entity.getAmount())
                    .set(PRODUCT.COMPANY_ID, entity.getCompanyId())
                    .where(PRODUCT.PRODUCT_ID. equal(entity.getProductId()))
                    .execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Product entity) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final ProductRecord productRecord = context.newRecord(PRODUCT);

            productRecord
                    .setProductName(entity.getProductName())
                    .setAmount(entity.getAmount())
                    .setCompanyId(entity.getCompanyId())
                    .store();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final ProductRecord productRecord = context.fetchOne(PRODUCT, PRODUCT.PRODUCT_ID.equal(id));
            if (productRecord != null) {
                productRecord.delete();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * delete a few product entities having name same as provided name
     * @param name name to delete product entities by
     * @return amount of deleted entities
     */
    public int delete(@NotNull String name) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            Result<ProductRecord> productRecords = context.fetch(PRODUCT, PRODUCT.PRODUCT_NAME.equal(name));
            int deletedAmount = productRecords.size();

            for (ProductRecord product : productRecords) {
                product.delete();
            }
            return deletedAmount;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            context.deleteFrom(PRODUCT).execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
