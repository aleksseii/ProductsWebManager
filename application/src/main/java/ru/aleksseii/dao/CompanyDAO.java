package ru.aleksseii.dao;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import generated.tables.pojos.Company;
import generated.tables.records.CompanyRecord;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static generated.Tables.COMPANY;

public final class CompanyDAO implements CrudDAO<Company> {

    private final @NotNull HikariDataSource dataSource;

    @Inject
    public CompanyDAO(@NotNull HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param id id to get company entity by
     * @return company entity by id if found, null otherwise
     */
    @Override
    public @Nullable Company get(int id) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final CompanyRecord companyRecord = context.fetchOne(COMPANY, COMPANY.COMPANY_ID.equal(id));
            if (companyRecord != null) {
                return companyRecord.into(Company.class);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * @param name unique name to get entity by
     * @return company entity by name if found, null otherwise
     */
    public @Nullable Company get(@NotNull String name) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final CompanyRecord companyRecord = context.fetchOne(COMPANY, COMPANY.COMPANY_NAME.equal(name));
            if (companyRecord != null) {
                return companyRecord.into(Company.class);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public @NotNull List<@NotNull Company> all() {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final Result<CompanyRecord> companyRecords = context.fetch(COMPANY);
            return companyRecords.stream()
                    .map(companyRecord -> companyRecord.into(Company.class))
                    .toList();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull Company entity) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            int companyId = entity.getCompanyId();
            final CompanyRecord companyRecord = context.fetchOne(COMPANY, COMPANY.COMPANY_ID.equal(companyId));

            if (companyRecord != null) {

                companyRecord
                        .setCompanyName(entity.getCompanyName())
                        .store();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull Company entity) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final CompanyRecord companyRecord = context.newRecord(COMPANY);

            companyRecord
                    .setCompanyName(entity.getCompanyName())
                    .store();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final CompanyRecord companyRecord = context.fetchOne(COMPANY, COMPANY.COMPANY_ID.equal(id));

            if (companyRecord != null) {
                companyRecord.delete();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(@NotNull String name) {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            final CompanyRecord companyRecord = context.fetchOne(COMPANY, COMPANY.COMPANY_NAME.equal(name));

            if (companyRecord != null) {
                companyRecord.delete();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {

        try (final Connection connection = dataSource.getConnection()) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            context.deleteFrom(COMPANY).execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
