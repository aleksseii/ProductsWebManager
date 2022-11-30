package ru.aleksseii;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.common.DefaultServer;
import ru.aleksseii.common.ProductsWebManagerModule;
import ru.aleksseii.common.database.DataSourceManager;
import ru.aleksseii.common.database.FlywayInitializer;
import ru.aleksseii.servlet.ProductServlet;

public final class Main {

    public static void main(@NotNull String @NotNull [] args) {

        FlywayInitializer.initDB();

        final HikariDataSource dataSource = DataSourceManager.getHikariDataSource();
        final Injector injector = Guice.createInjector(new ProductsWebManagerModule(dataSource));

        try {

            final Server server = DefaultServer.build();
            final ServletContextHandler context = new ServletContextHandler();
            final ProductServlet productServlet = injector.getInstance(ProductServlet.class);

            context.setContextPath("/");
            context.addServlet(
                    new ServletHolder("product-servlet", productServlet),
                    "/product"
            );

            server.setHandler(context);

            server.start();

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
