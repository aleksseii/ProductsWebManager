-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

CREATE TABLE IF NOT EXISTS public.company(
    company_id      SERIAL          PRIMARY KEY,
    company_name    VARCHAR(50)     NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.product(
    product_id      SERIAL          PRIMARY KEY,
    product_name    VARCHAR(50)     NOT NULL,
    amount          INT             NOT NULL
        CONSTRAINT non_negative_amount CHECK (amount >= 0),
    
    company_id      INT
        REFERENCES public.company(company_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);
