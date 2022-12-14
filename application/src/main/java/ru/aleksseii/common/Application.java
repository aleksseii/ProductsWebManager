package ru.aleksseii.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.common.database.DataSourceManager;
import ru.aleksseii.common.database.FlywayInitializer;
import ru.aleksseii.rest.RESTGuiceListener;
import ru.aleksseii.security.SecurityHandlerBuilder;

import java.net.URL;

public final class Application {

    public static void run() {

        FlywayInitializer.initDB();

        final HikariDataSource dataSource = DataSourceManager.getHikariDataSource();
        Injector mainInjector = Guice.createInjector(new ProductsWebManagerModule(dataSource));

        try {

            final Server server = DefaultServer.build();

            final ServletContextHandler context = new ServletContextHandler();

            context.setContextPath("/");
            context.addServlet(HttpServletDispatcher.class, "/");

            final RESTGuiceListener restGuiceListener = new RESTGuiceListener(mainInjector);
            context.addEventListener(restGuiceListener);

            final URL jdbcConfigURL = getJdbcConfigURL();

            final LoginService loginService = new JDBCLoginService(
                    "jdbc-login",
                    jdbcConfigURL.toExternalForm()
            );

            final ConstraintSecurityHandler securityHandler =
                    new SecurityHandlerBuilder().build(loginService);

            context.setHandler(securityHandler);

            server.setHandler(context);

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static @NotNull URL getJdbcConfigURL() {

        final URL configURL = Application.class.getResource("/db/jdbc_config.properties");
        assert configURL != null;

        return configURL;
    }
}
