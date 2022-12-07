package ru.aleksseii.rest;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.rest.controller.HelpMessageController;
import ru.aleksseii.rest.controller.ProductController;
import ru.aleksseii.service.ProductService;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

public final class RESTGuiceListener extends GuiceResteasyBootstrapServletContextListener {

    private final @NotNull Injector mainInjector;

    public RESTGuiceListener(@NotNull Injector mainInjector) {
        this.mainInjector = mainInjector;
    }

    @Override
    protected List<? extends Module> getModules(ServletContext context) {

        return Collections.singletonList(new RESTGuiceModule());
    }

    private final class RESTGuiceModule extends AbstractModule {

        @Override
        protected void configure() {

            final ProductService productService = mainInjector.getInstance(ProductService.class);

            bind(ProductController.class).toInstance(new ProductController(productService));
            bind(HelpMessageController.class).toInstance(new HelpMessageController());
        }
    }
}
