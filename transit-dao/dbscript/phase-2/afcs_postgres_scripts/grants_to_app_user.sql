--Run the script after each DDL changes
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA afcs_dev TO afcs_app_role;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA afcs_dev TO afcs_app_role;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA afcs_dev TO afcs_app_role;
GRANT USAGE ON SCHEMA afcs_dev TO afcs_app_role;
grant afcs_app_role to afcs_dev;

