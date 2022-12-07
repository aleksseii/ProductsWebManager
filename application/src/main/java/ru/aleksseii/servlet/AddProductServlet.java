package ru.aleksseii.servlet;

import com.google.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.service.ProductService;

public final class AddProductServlet extends HttpServlet {

    private final @NotNull ProductService productServiceImpl;

    @Inject
    public AddProductServlet(@NotNull ProductService productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest request,
                          @NotNull HttpServletResponse response) {

        final String productName = request.getParameter("productName");
        final String companyName = request.getParameter("companyName");
        final int amount = Integer.parseInt(request.getParameter("amount"));

        productServiceImpl.saveProduct(productName, companyName, amount);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
