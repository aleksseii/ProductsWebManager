package ru.aleksseii.rest.controller;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.rest.dto.ProductDTO;
import ru.aleksseii.service.ProductService;

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

        return productService.getAllProducts();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public @NotNull Response addProduct(@NotNull ProductDTO productDTO) {

        productService.saveProduct(productDTO.productName(), productDTO.companyName(), productDTO.amount());
        return Response.ok().build();
    }
}
