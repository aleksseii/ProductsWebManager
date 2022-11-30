package ru.aleksseii;

import org.jetbrains.annotations.NotNull;
import ru.aleksseii.common.database.FlywayInitializer;

public final class Main {

    public static void main(@NotNull String @NotNull [] args) {

        FlywayInitializer.initDB();
    }
}