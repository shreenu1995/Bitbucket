############Steps to execute Afcs Release v1.0 DB Scripts in Order############

Connect as postgres superadmin User then execute the script from Release 1.0
01_database_admin_user.sql

--Connect as the user created on above step and execute the script
--Replace the schema name,Database name,user name before executing scripts in working environment
--SET search_path = <schme_name>, pg_catalog;

02_tables.sql
03_sequences.sql
04_seed_data.sql
05_constraints.sql

-- Login as postgres user and execute
06_grants.sql

if you are executing scripts other than Release 1.0 please execute the below script from postgres user

grants_to_app_user.sql

Command line execution of script

psql -h  -d  -U  -p  -a -q -f 

-h PostgreSQL server IP address
-d database name
-U user name
-p port which PostgreSQL server is listening on
-f path to SQL script
-a Print all nonempty input lines to standard output as they are read
-q Specifies that psql should do its work quietly. By default, it prints welcome messages and various informational output. If this option is used, none of this happens. 

