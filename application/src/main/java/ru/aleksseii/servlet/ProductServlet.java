package ru.aleksseii.servlet;

import com.google.gson.Gson;
import com.google.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.model.Product;
import ru.aleksseii.service.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class ProductServlet extends HttpServlet {

    private final @NotNull ProductService productServiceImpl;

    @Inject
    public ProductServlet(@NotNull ProductService productServiceImpl) {
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

    @Override
    protected void doPost(@NotNull HttpServletRequest request,
                          @NotNull HttpServletResponse response) {

        final String productName = request.getParameter("productName");
        final String companyName = request.getParameter("companyName");
        int amount = Integer.parseInt(request.getParameter("amount"));

        productServiceImpl.saveProduct(productName, companyName, amount);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
