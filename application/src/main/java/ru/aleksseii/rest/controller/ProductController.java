package ru.aleksseii.rest.controller;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.dao.CompanyDAO;
import ru.aleksseii.rest.dto.ProductDTO;
import ru.aleksseii.service.ProductService;
import ru.aleksseii.service.ProductServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/product")
public final class ProductController {

    private final @NotNull ProductService productService;

    @Inject
    public ProductController(@NotNull ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public @NotNull List<@NotNull ProductDTO> getAllProducts() {

        final CompanyDAO companyDAO = ((ProductServiceImpl) productService).getCompanyDAO();

        return productService.getAllProducts()
                .stream()
                .map(p -> {
                    final Integer companyId = p.getCompanyId();
                    final String companyName = companyDAO.get(companyId).getCompanyName();
                    return new ProductDTO(p, companyName);
                }).toList();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public @NotNull Response addProduct(@NotNull ProductDTO productDTO) {

        productService.saveProduct(productDTO.productName(), productDTO.companyName(), productDTO.amount());
        return Response.ok().build();
    }
}
