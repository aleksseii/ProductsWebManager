INSERT INTO security.roles(role_name)
VALUES                    ( 'guest' ),
                          ('manager');
                          
INSERT INTO security.users(user_name, password)
VALUES                    ('guest_1',  'first'),
                          ('guest_2', 'second'),
                     ('almost_admin', 'admin' );
                     
INSERT INTO security.users_roles(user_id, role_id)
VALUES                          (   1,       1   ),
                                (   2,       1   ),
                                (   3,       2   );