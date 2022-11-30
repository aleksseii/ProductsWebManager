package ru.aleksseii.service;

import org.jetbrains.annotations.NotNull;
import ru.aleksseii.model.Product;

import java.util.List;

public interface ProductService {

    @NotNull List<@NotNull Product> getAllProducts();

    void saveProduct(@NotNull String productName,
                     @NotNull String companyName,
                     int amount);
}
