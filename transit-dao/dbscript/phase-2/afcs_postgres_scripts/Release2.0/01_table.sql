--Added on 15-11-18
COMMENT ON SCHEMA afcs_qa IS '2.0';

SET search_path = afcs_qa, pg_catalog;

CREATE TABLE email_service (
    email_id numeric(20) NOT NULL,
    event_type varchar(100) NOT NULL,
    subject varchar(300) NULL DEFAULT NULL::character varying,
    email_address varchar(1000) NOT NULL,
    email_body bytea NULL,
    status varchar(50) NOT NULL,
    scheduled_date timestamp NULL,
    created_date timestamp NULL DEFAULT now(),
    updated_date timestamp NULL,
    PRIMARY KEY (email_id)
);



CREATE TABLE employee_credentials (
    id serial NOT NULL,
    employee_id varchar NOT NULL,
    password varchar(100) NOT NULL,
    designation varchar NOT NULL,
    status bool NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    PRIMARY KEY (id)
) ;


CREATE TABLE employee_profile (
    id serial NOT NULL,
    employee_id varchar NOT NULL,
    employee_name varchar NOT NULL,
    password varchar(100) NOT NULL,
    employee_address varchar(500) NOT NULL,
    city varchar(40) NOT NULL,
    district varchar(40) NOT NULL,
    state varchar(40) NULL,
    country varchar(40) NULL,
    pin_code varchar NOT NULL,
    employee_phone varchar NOT NULL,
    employee_mobile varchar NOT NULL,
    employee_email varchar NOT NULL,
    organisation varchar NOT NULL,
    designation varchar NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    PRIMARY KEY (id)
);

alter table user_profile add email_token varchar(50); 

alter table user_credentials add pto_id varchar(20); 

--Added on 16-11-18

CREATE TABLE transaction_category_master (
    id serial NOT NULL,
    transaction_category_name varchar NOT NULL,
    transaction_category int4 NOT NULL,
    transaction_status bool NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    PRIMARY KEY (transaction_category)
) ;

j.merchant_code

CREATE TABLE transaction_id_master (
        id serial NOT NULL,
    transaction_id varchar NOT NULL,
    transaction_name varchar NOT NULL,
    transaction_status bool NULL,
    transaction_category int4 NOT NULL,
    created_date_time timestamp NOT NULL,
    updated_date_time timestamp NOT NULL,
    PRIMARY KEY (transaction_id),
     FOREIGN KEY (transaction_category) REFERENCES transaction_category_master (transaction_category)) ;

CREATE TABLE equipment_model (
    id serial NOT NULL,
    model_id int8 NOT NULL,
    model_name varchar(500) NULL,
    model_capabilities varchar(500) NULL,
    pto_id varchar(500) NOT NULL,
    equipment_type varchar NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    PRIMARY KEY (model_id)) ;




CREATE TABLE route_master (
    id serial NOT NULL,
    pto_operation_id varchar(40) NOT NULL,
    route_id int4 NOT NULL,
    route_name varchar(40) NOT NULL,
    status bool NOT NULL,
    city varchar(800) NOT NULL,
    district varchar(40) NOT NULL,
    state varchar(100) NULL,
    country varchar(40) NULL,
    depot_code varchar NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    CONSTRAINT route_master_pkey PRIMARY KEY (route_id)) ;



CREATE TABLE equipment_type_master (
    id serial NOT NULL,
    equipment_type_id serial NULL,
    pto_operation_id varchar(40) NOT NULL,
    equipment_type_name varchar(500) NOT NULL,
    description varchar(800) NOT NULL,
    equipment_capabilities varchar(40) NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL) ;





DROP TABLE equipment_master;
CREATE TABLE equipment_master (
    id serial NOT NULL,
    equipment_id varchar NOT NULL,
    station_id int4 NOT NULL,
    depot_id int4 NOT NULL,
    pto_operation_id varchar NOT NULL,
    description varchar(500) NOT NULL,
    model varchar(50) NOT NULL,
    serial_number varchar NOT NULL,
    assigned_software_version varchar NULL,
    assigned_master_version varchar NULL,
    additional_special_data varchar NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL,
    CONSTRAINT equipment_master_pkey PRIMARY KEY (id)) ;

CREATE TABLE software_master (
    id serial NOT NULL,
    software_id int8 NULL,
    software_name varchar(40) NOT NULL,
    pto_id varchar(500) NOT NULL,
    equipment_id varchar(800) NOT NULL,
    description varchar(40) NOT NULL,
    base_path varchar(100) NULL,
    latest_version varchar(40) NULL,
    status bool NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL) ;


CREATE TABLE station_master (
    pto_operation_id varchar NOT NULL,
    station_id int4 NOT NULL,
    station_name varchar(500) NOT NULL,
    status bool NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL) ;


CREATE TABLE station_profile (
    station_id int4 NOT NULL,
    station_name varchar(50) NOT NULL,
    station_address varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    district varchar(50) NOT NULL,
    state varchar(50) NOT NULL,
    country varchar(50) NOT NULL,
    pin_code varchar(50) NOT NULL,
    station_phone varchar(50) NOT NULL,
    station_mobile varchar(50) NOT NULL,
    station_email varchar(50) NOT NULL,
    organisation varchar(50) NOT NULL,
    station_coordinate varchar(50) NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL) ;



CREATE TABLE stop_master (
    pto_operation_id varchar NOT NULL,
    stop_id int4 NOT NULL,
    stop_name varchar(500) NOT NULL,
    status bool NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT null) ;


CREATE TABLE stop_profile (
    stop_id int4 NOT NULL,
    stop_name varchar(50) NULL,
    stop_address varchar(50) NULL,
    city varchar(50) NOT NULL,
    district varchar(50) NOT NULL,
    state varchar(50) NOT NULL,
    country varchar(50) NOT NULL,
    pin_code varchar(50) NOT NULL,
    stop_coordinate varchar(50) NOT NULL,
    organisation varchar(50) NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT NULL) ;


-- Added on 19-nov-18
ALTER TABLE software_download DROP created_by;

ALTER TABLE software_download DROP updated_by;  


--Added on 20-nov-18

CREATE TABLE file_master (
    id int8 NULL,
    master_file varchar(200) NULL,
    software_file varchar(200) NULL,
    desktop_file varchar(200) NULL,
    CONSTRAINT file_master_pk PRIMARY KEY (id)
);

--Added on 22-nov-18

drop table stop_master;
CREATE TABLE stop_master (
    stop_id int4 NOT NULL,
    stop_name varchar(500) NOT NULL,
    status bool NOT NULL,
    pto_operation_id varchar NOT NULL,
    created_time timestamp NOT NULL,
    updated_time timestamp NOT NULL,
    created_by varchar(40) NOT NULL,
    updated_by varchar(40) NOT null,
    CONSTRAINT stop_id_pk PRIMARY KEY (stop_id)
);

ALTER TABLE employee_credentials ADD pto_operation_id varchar(50) NULL;
ALTER TABLE employee_profile ADD pto_operation_id varchar(50) NULL;
ALTER TABLE equipment_setup_management RENAME COLUMN pto_id TO pto_operation_id;
drop table operator_session_management;
ALTER TABLE admin_session_management RENAME TO operator_session_management;
ALTER TABLE operator_session_management RENAME COLUMN pto_id TO pto_operation_id;

--Added 26-nov-18
ALTER TABLE user_profile ADD previous_passwords varchar(500) NULL;

--Added 30-nov-18
ALTER TABLE equipment_model RENAME pto_id TO pto_operation_id;

ALTER TABLE equipment_model ADD COLUMN description varchar(50);

ALTER TABLE equipment_model ADD COLUMN status boolean;

ALTER TABLE equipment_type_master ADD COLUMN status boolean;

--Added 04-dec-18
ALTER TABLE depot_profile DROP COLUMN organisation;
ALTER TABLE pto_profile DROP COLUMN organisation;
ALTER TABLE station_profile DROP COLUMN organisation;
ALTER TABLE user_profile DROP COLUMN organisation;
ALTER TABLE stop_profile DROP COLUMN organisation;
ALTER TABLE pto_operation_profile DROP COLUMN organisation;
ALTER TABLE employee_profile DROP COLUMN organisation;
