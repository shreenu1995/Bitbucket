
--from postgres user
CREATE ROLE afcs_app_role NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA afcs_qa TO afcs_app_role;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA afcs_qa TO afcs_app_role;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA afcs_qa TO afcs_app_role;
GRANT USAGE ON SCHEMA afcs_qa TO afcs_app_role;
create user afcs_qa with password 'AFCSqa$27';
grant afcs_app_role to afcs_qa;

