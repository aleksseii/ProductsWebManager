package ru.aleksseii.servlet;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AddProductServlet extends HttpServlet {

    private final @NotNull ProductService productService;

    @Inject
    public AddProductServlet(@NotNull ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request,
                          @NotNull HttpServletResponse response) {

        final String productName = request.getParameter("productName");
        final String companyName = request.getParameter("companyName");
        final int amount = Integer.parseInt(request.getParameter("amount"));

        productService.saveProduct(productName, companyName, amount);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
