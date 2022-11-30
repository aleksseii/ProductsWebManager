package ru.aleksseii.model;

import org.jetbrains.annotations.NotNull;

public record Product(int productId,
                      @NotNull String productName,
                      int amount,
                      int companyId) {

    public Product(@NotNull String productName, int amount, int companyId) {
        this(0, productName, amount, companyId);
    }
}
