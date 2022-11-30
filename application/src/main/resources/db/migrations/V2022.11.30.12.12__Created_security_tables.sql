CREATE TABLE IF NOT EXISTS security.roles(
    role_id      SERIAL         PRIMARY KEY,
    role_name    VARCHAR(50)    NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS security.users(
    user_id      SERIAL         PRIMARY KEY,
    user_name    VARCHAR(50)    NOT NULL UNIQUE,
    password     VARCHAR(50)    NOT NULL
);

CREATE TABLE IF NOT EXISTS security.users_roles(
    user_id     INT     NOT NULL,
    role_id     INT     NOT NULL,
    CONSTRAINT users_roles_pk PRIMARY KEY(user_id, role_id)
);