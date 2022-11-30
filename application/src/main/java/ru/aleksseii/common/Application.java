package ru.aleksseii.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.common.database.DataSourceManager;
import ru.aleksseii.common.database.FlywayInitializer;
import ru.aleksseii.servlet.ProductServlet;

import java.io.IOException;
import java.net.URL;

public final class Application {

    public static void run() {

        FlywayInitializer.initDB();

        final HikariDataSource dataSource = DataSourceManager.getHikariDataSource();
        final Injector injector = Guice.createInjector(new ProductsWebManagerModule(dataSource));

        final ProductServlet productServlet = injector.getInstance(ProductServlet.class);

        try {

            final Server server = DefaultServer.build();
            final ServletContextHandler context = getConfiguredContext(productServlet);

            server.setHandler(context);

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static @NotNull ServletContextHandler getConfiguredContext(@NotNull ProductServlet productServlet)
            throws IOException {

        final ServletContextHandler context = new ServletContextHandler();

        final URL helpURL = getHelpURL();

        context.setContextPath("/");
        context.setBaseResource(Resource.newResource(helpURL.toExternalForm()));
        context.addServlet(
                new ServletHolder("default-servlet", DefaultServlet.class),
                "/*"
        );
        context.addServlet(
                new ServletHolder("product-servlet", productServlet),
                "/product"
        );

        return context;
    }

    private static @NotNull URL getHelpURL() {

        final URL helpURL = Application.class.getResource("/static/help.html");
        assert helpURL != null;

        return helpURL;
    }
}
