package ru.aleksseii.service;

import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ProductService {

    @NotNull List<@NotNull Product> getAllProducts();

    void saveProduct(@NotNull String productName,
                     @NotNull String companyName,
                     int amount);
}
