--from postgres user
CREATE DATABASE afcs_qa WITH OWNER = postgres;
create user afcs_admin with password 'Adm1naf05';
\c afcs_qa
CREATE SCHEMA afcs_qa AUTHORIZATION afcs_admin;  