package ru.aleksseii.model;

import org.jetbrains.annotations.NotNull;

public record Company(int companyId,
                      @NotNull String companyName) {

    public Company(@NotNull String companyName) {
        this(0, companyName);
    }
}
