-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO public.company(company_name)
VALUES                    ( 'company 1'),
                          ( 'company 2'),
                          ( 'company 3');
       
       
INSERT INTO public.product(product_name, amount, company_id)
VALUES                    ( 'product 1',    5,        1    ),
                          ( 'product 2',    3,        2    ),
                          ( 'product 3',   10,        3    ),
                          ( 'product 4',    2,        1    ),
                          ( 'product 5',    1,        2    ),
                          ( 'product 6',    7,        3    ),
                          ( 'product 7',    9,        1    ),
                          ( 'product 8',    4,        2    ),
                          ( 'product 9',    8,        3    ),                                                                              
                          ('product 10',    6,        1    );
