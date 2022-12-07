package ru.aleksseii.service;

import org.jetbrains.annotations.NotNull;
import ru.aleksseii.rest.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    @NotNull List<@NotNull ProductDTO> getAllProducts();

    @NotNull List<@NotNull ProductDTO> getProductsByCompany(@NotNull String companyName);

    void saveProduct(@NotNull String productName,
                     @NotNull String companyName,
                     int amount);

    int deleteProductsByName(@NotNull String productName);
}
