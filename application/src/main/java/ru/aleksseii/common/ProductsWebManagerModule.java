package ru.aleksseii.common;

import com.google.inject.AbstractModule;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.service.ProductService;
import ru.aleksseii.service.ProductServiceImpl;

public final class ProductsWebManagerModule extends AbstractModule {

    private final @NotNull HikariDataSource dataSource;

    public ProductsWebManagerModule(@NotNull HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure() {

        bind(ProductService.class).to(ProductServiceImpl.class);
        bind(HikariDataSource.class).toInstance(dataSource);
    }
}
