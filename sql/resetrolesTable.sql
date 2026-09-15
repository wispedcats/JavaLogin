DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
       name varchar(255),
       banUser boolean,
       resetUserPassword boolean,
       deleteUser boolean
)