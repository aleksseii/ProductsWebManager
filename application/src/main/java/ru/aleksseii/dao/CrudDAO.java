package ru.aleksseii.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CrudDAO<T> {

    @Nullable T get(int id);

    @NotNull List<@NotNull T> all();

    void update(@NotNull T entity);

    void save(@NotNull T entity);

    void delete(int id);

    void deleteAll();
}
