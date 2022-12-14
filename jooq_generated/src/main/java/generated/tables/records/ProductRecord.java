/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Product;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductRecord extends UpdatableRecordImpl<ProductRecord> implements Record4<Integer, String, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.product.product_id</code>.
     */
    public ProductRecord setProductId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.product.product_id</code>.
     */
    public Integer getProductId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.product.product_name</code>.
     */
    public ProductRecord setProductName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.product.product_name</code>.
     */
    public String getProductName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.product.amount</code>.
     */
    public ProductRecord setAmount(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.product.amount</code>.
     */
    public Integer getAmount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.product.company_id</code>.
     */
    public ProductRecord setCompanyId(Integer value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.product.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, String, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Product.PRODUCT.PRODUCT_ID;
    }

    @Override
    public Field<String> field2() {
        return Product.PRODUCT.PRODUCT_NAME;
    }

    @Override
    public Field<Integer> field3() {
        return Product.PRODUCT.AMOUNT;
    }

    @Override
    public Field<Integer> field4() {
        return Product.PRODUCT.COMPANY_ID;
    }

    @Override
    public Integer component1() {
        return getProductId();
    }

    @Override
    public String component2() {
        return getProductName();
    }

    @Override
    public Integer component3() {
        return getAmount();
    }

    @Override
    public Integer component4() {
        return getCompanyId();
    }

    @Override
    public Integer value1() {
        return getProductId();
    }

    @Override
    public String value2() {
        return getProductName();
    }

    @Override
    public Integer value3() {
        return getAmount();
    }

    @Override
    public Integer value4() {
        return getCompanyId();
    }

    @Override
    public ProductRecord value1(Integer value) {
        setProductId(value);
        return this;
    }

    @Override
    public ProductRecord value2(String value) {
        setProductName(value);
        return this;
    }

    @Override
    public ProductRecord value3(Integer value) {
        setAmount(value);
        return this;
    }

    @Override
    public ProductRecord value4(Integer value) {
        setCompanyId(value);
        return this;
    }

    @Override
    public ProductRecord values(Integer value1, String value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductRecord
     */
    public ProductRecord() {
        super(Product.PRODUCT);
    }

    /**
     * Create a detached, initialised ProductRecord
     */
    public ProductRecord(Integer productId, String productName, Integer amount, Integer companyId) {
        super(Product.PRODUCT);

        setProductId(productId);
        setProductName(productName);
        setAmount(amount);
        setCompanyId(companyId);
    }

    /**
     * Create a detached, initialised ProductRecord
     */
    public ProductRecord(generated.tables.pojos.Product value) {
        super(Product.PRODUCT);

        if (value != null) {
            setProductId(value.getProductId());
            setProductName(value.getProductName());
            setAmount(value.getAmount());
            setCompanyId(value.getCompanyId());
        }
    }
}
