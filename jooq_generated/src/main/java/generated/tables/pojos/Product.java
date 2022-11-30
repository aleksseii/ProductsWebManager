/*
 * This file is generated by jOOQ.
 */
package generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer productId;
    private final String productName;
    private final Integer amount;
    private final Integer companyId;

    public Product(Product value) {
        this.productId = value.productId;
        this.productName = value.productName;
        this.amount = value.amount;
        this.companyId = value.companyId;
    }

    public Product(
        Integer productId,
        String productName,
        Integer amount,
        Integer companyId
    ) {
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.companyId = companyId;
    }

    /**
     * Getter for <code>public.product.product_id</code>.
     */
    public Integer getProductId() {
        return this.productId;
    }

    /**
     * Getter for <code>public.product.product_name</code>.
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Getter for <code>public.product.amount</code>.
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * Getter for <code>public.product.company_id</code>.
     */
    public Integer getCompanyId() {
        return this.companyId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Product other = (Product) obj;
        if (this.productId == null) {
            if (other.productId != null)
                return false;
        }
        else if (!this.productId.equals(other.productId))
            return false;
        if (this.productName == null) {
            if (other.productName != null)
                return false;
        }
        else if (!this.productName.equals(other.productName))
            return false;
        if (this.amount == null) {
            if (other.amount != null)
                return false;
        }
        else if (!this.amount.equals(other.amount))
            return false;
        if (this.companyId == null) {
            if (other.companyId != null)
                return false;
        }
        else if (!this.companyId.equals(other.companyId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.productId == null) ? 0 : this.productId.hashCode());
        result = prime * result + ((this.productName == null) ? 0 : this.productName.hashCode());
        result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
        result = prime * result + ((this.companyId == null) ? 0 : this.companyId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product (");

        sb.append(productId);
        sb.append(", ").append(productName);
        sb.append(", ").append(amount);
        sb.append(", ").append(companyId);

        sb.append(")");
        return sb.toString();
    }
}