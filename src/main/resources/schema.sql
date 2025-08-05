use group_project;

drop table if exists items;
drop table if exists contacts;
drop table if exists users;
drop table if exists roles;
drop table if exists employees;
drop table if exists templates;

CREATE TABLE IF NOT EXISTS employees(
    id bigint auto_increment primary key,
    name varchar(50) NOT NULL,
    role varchar(50) NOT NULL,
    email varchar(70) NOT NULL default "test@test.com",
    start_date DATE
) engine = InnoDB;

ALTER TABLE employees
    ADD UNIQUE (email);

CREATE TABLE IF NOT EXISTS contacts(
    id bigint auto_increment primary key,
    name varchar(50) not null,
    role varchar(50) not null,
    email varchar(70) not null,
    phone_number varchar(10) not null
) engine = InnoDB;

ALTER TABLE contacts
    ADD UNIQUE (email),
    ADD UNIQUE (phone_number);

CREATE TABLE IF NOT EXISTS items(
    id bigint auto_increment primary key,
    title varchar(50) not null,
    description varchar(75) not null,
    due_date DATETIME null,
    is_complete BOOLEAN DEFAULT 0,
    owner_id VARCHAR(50) not null,
    contacts_id bigint null,
    CONSTRAINT fk_contacts_id FOREIGN KEY (contacts_id) REFERENCES contacts (id)
) engine = InnoDB;

CREATE TABLE if not exists roles(
    role_id int(11) NOT NULL AUTO_INCREMENT primary key,
    name varchar(45) NOT NULL
) engine = InnoDB;

create table if not exists users(
    id bigint auto_increment primary key,
    username varchar(50) not null,
    password varchar(500) not null,
    email varchar(70) NOT NULL default "test@test.com",
    reset_token VARCHAR(100),
    enabled boolean not null,
    role_id int(11) DEFAULT 2,
    assigned_id bigint null,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT fk_assigned_id FOREIGN KEY (assigned_id) REFERENCES employees (id)

) engine = InnoDB;

ALTER TABLE users
    ADD UNIQUE (username);


create table if not exists templates(
    id bigint auto_increment primary key,
    name varchar(50) not null
) engine = InnoDB;

DROP VIEW IF EXISTS user_authorities;
CREATE VIEW user_authorities AS
SELECT u.username AS username, CONCAT("ROLE_", r.name) AS authority
FROM users u
         JOIN roles r ON u.role_id = r.role_id;
