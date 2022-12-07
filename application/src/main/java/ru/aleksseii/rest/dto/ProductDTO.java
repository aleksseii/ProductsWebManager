package ru.aleksseii.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import generated.tables.pojos.Product;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDTO(@NotNull @JsonProperty Integer productId,
                         @NotNull @JsonProperty String productName,
                         @NotNull @JsonProperty Integer amount,
                         @NotNull @JsonProperty String companyName) implements Serializable {

    @JsonCreator
    public ProductDTO(@NotNull @JsonProperty Integer productId,
                      @NotNull @JsonProperty(required = true) String productName,
                      @NotNull @JsonProperty(required = true) Integer amount,
                      @NotNull @JsonProperty(required = true) String companyName) {

        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.companyName = companyName;
    }

    public ProductDTO(@NotNull Product product, @NotNull String companyName) {
        this(
                product.getProductId(),
                product.getProductName(),
                product.getAmount(),
                companyName
        );
    }
}
