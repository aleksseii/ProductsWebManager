package ru.aleksseii.servlet;

import com.google.gson.Gson;
import com.google.inject.Inject;
import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.service.ProductService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class GetProductsServlet extends HttpServlet {

    private final @NotNull ProductService productServiceImpl;

    @Inject
    public GetProductsServlet(@NotNull ProductService productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @Override
    protected void doGet(@NotNull HttpServletRequest request,
                         @NotNull HttpServletResponse response) throws IOException {

        final Gson gson = new Gson();

        List<@NotNull Product> allProducts = productServiceImpl.getAllProducts();
        final String json = gson.toJson(allProducts);

        response.setContentType("application/json");
        try (final PrintWriter responseWriter = response.getWriter()) {
            responseWriter.println(json);
        }
    }
}
