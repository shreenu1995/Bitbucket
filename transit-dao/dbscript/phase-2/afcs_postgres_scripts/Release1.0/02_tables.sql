--From afcs_admin user

COMMENT ON SCHEMA afcs_qa IS '1.0';

SET search_path = afcs_qa, pg_catalog;

CREATE TABLE admin_session_management (
    id integer NOT NULL,
    user_id character varying(40) NOT NULL,
    employee_id character varying(40) NOT NULL,
    pto_id character varying(40) NOT NULL,
    equipment_id character varying(40) NOT NULL,
    transaction_id character varying(40) NOT NULL,
    generation_date_time timestamp without time zone NOT NULL,
    process_date_time timestamp without time zone
);

CREATE TABLE admin_transaction_data (
    id integer NOT NULL,
    user_id character varying(40) NOT NULL,
    password character varying(40) NOT NULL,
    transaction_id character varying(40) NOT NULL,
    date_time timestamp without time zone NOT NULL
);


CREATE TABLE card_master (
    id bigint NOT NULL,
    card_number character varying(255),
    card_type character varying(255),
    expiry_date character varying(255),
    issuer character varying(255),
    pto_id character varying(255)
);



CREATE TABLE card_transaction_info (
    id integer NOT NULL,
    ticket_number character varying NOT NULL,
    card_number character varying,
    card_type character varying,
    card_balance character varying,
    card_expiry_date character varying
);



CREATE TABLE card_type_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    card_type character varying,
    card_name character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    card_sub_type character varying,
    card_sub_type_name character varying
);



CREATE TABLE common_parameters (
    id integer NOT NULL,
    parameter character varying,
    value character varying
);


CREATE TABLE company_master (
    id integer,
    pto_id character varying,
    pto_name character varying,
    pto_operation_name character varying,
    super_pto_id character varying,
    super_pto_name character varying,
    pto_address character varying,
    pto_phone character varying,
    pto_email character varying,
    pto_website character varying
);



CREATE TABLE crew_transaction_info (
    id integer NOT NULL,
    ticket_number character varying NOT NULL,
    conductor_emp_id character varying,
    driver_emp_id character varying,
    shift_id character varying,
    trip_id character varying,
    route_code character varying,
    vehicle_number character varying,
    current_stop_id character varying,
    current_stop_seq_number character varying,
    total_stop_seq_number character varying
);

CREATE TABLE depot_master (
    id integer NOT NULL,
    depot_id integer DEFAULT nextval('for_depot_id'::regclass) NOT NULL,
    depot_name character varying,
    pto_operation_id character varying,
    status boolean,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone
);

CREATE TABLE depot_profile (
    id integer NOT NULL,
    depot_id integer NOT NULL,
    depot_name character varying(40),
    depot_address character varying(800),
    city character varying(40),
    district character varying(40),
    state character varying(40),
    country character varying(40),
    pincode character varying(40),
    depot_phone character varying(40),
    depot_mobile character varying(40),
    depot_email character varying(40),
    organisation character varying(40),
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone
);




CREATE TABLE device_transaction_info (
    id integer NOT NULL,
    ticket_number character varying,
    pto_operation_id character varying,
    device_type character varying,
    device_id character varying,
    software_version character varying,
    master_version character varying,
    device_serial_number character varying,
    device_model_number character varying,
    device_os_version character varying
);





CREATE TABLE device_type_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    device_type character varying,
    device_sub_type character varying,
    device_name character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    device_type_name character varying
);



CREATE TABLE equipment_master (
    id integer,
    pto_id character varying,
    equipment_id character varying,
    equipment_oem_id character varying,
    equipment_type character varying,
    configurable_master boolean,
    equipment_name character varying,
    device_type character varying(255)
);

CREATE TABLE equipment_setup_management (
    id integer NOT NULL,
    pto_id character varying(40) NOT NULL,
    equipment_id character varying(40) NOT NULL,
    equipment_oem_id character varying(40) NOT NULL,
    equipment_model character varying(40) NOT NULL,
    generation_date_time timestamp without time zone NOT NULL,
    processing_date_time timestamp without time zone
);



CREATE TABLE equipment_type_master (
    id integer NOT NULL,
    equipment_type_id integer ,
    pto_operation_id character varying(40) NOT NULL,
    equipment_type_name character varying(500) NOT NULL,
    description character varying(800) NOT NULL,
    equipment_capabilities character varying(40) NOT NULL,
    created_time timestamp without time zone NOT NULL,
    updated_time timestamp without time zone NOT NULL,
    created_by character varying(40) NOT NULL,
    updated_by character varying(40) NOT NULL
);





CREATE TABLE financial_txn_data (
    id bigint NOT NULL,
    amount_0 integer,
    amount_1 integer,
    amount_10 integer,
    amount_11 integer,
    amount_12 integer,
    amount_13 integer,
    amount_14 integer,
    amount_15 integer,
    amount_16 integer,
    amount_17 integer,
    amount_18 integer,
    amount_19 integer,
    amount_2 integer,
    amount_20 integer,
    amount_21 integer,
    amount_22 integer,
    amount_23 integer,
    amount_24 integer,
    amount_25 integer,
    amount_26 integer,
    amount_27 integer,
    amount_28 integer,
    amount_29 integer,
    amount_3 integer,
    amount_30 integer,
    amount_31 integer,
    amount_32 integer,
    amount_33 integer,
    amount_34 integer,
    amount_35 integer,
    amount_36 integer,
    amount_37 integer,
    amount_38 integer,
    amount_39 integer,
    amount_4 integer,
    amount_40 integer,
    amount_41 integer,
    amount_42 integer,
    amount_43 integer,
    amount_44 integer,
    amount_45 integer,
    amount_46 integer,
    amount_47 integer,
    amount_48 integer,
    amount_49 integer,
    amount_5 integer,
    amount_6 integer,
    amount_7 integer,
    amount_8 integer,
    amount_9 integer,
    count_0 integer,
    count_1 integer,
    count_10 integer,
    count_11 integer,
    count_12 integer,
    count_13 integer,
    count_14 integer,
    count_15 integer,
    count_16 integer,
    count_17 integer,
    count_18 integer,
    count_19 integer,
    count_2 integer,
    count_20 integer,
    count_21 integer,
    count_22 integer,
    count_23 integer,
    count_24 integer,
    count_25 integer,
    count_26 integer,
    count_27 integer,
    count_28 integer,
    count_29 integer,
    count_3 integer,
    count_30 integer,
    count_31 integer,
    count_32 integer,
    count_33 integer,
    count_34 integer,
    count_35 integer,
    count_36 integer,
    count_37 integer,
    count_38 integer,
    count_39 integer,
    count_4 integer,
    count_40 integer,
    count_41 integer,
    count_42 integer,
    count_43 integer,
    count_44 integer,
    count_45 integer,
    count_46 integer,
    count_47 integer,
    count_48 integer,
    count_49 integer,
    count_5 integer,
    count_6 integer,
    count_7 integer,
    count_8 integer,
    count_9 integer,
    created_date_time timestamp without time zone,
    date_time character varying(255),
    equipment_component_version character varying(255),
    equipment_id character varying(255),
    equipment_model_no character varying(255),
    equipment_serial_no character varying(255),
    financial_txn_type character varying(255),
    master_version character varying(255),
    payment_host_batch_no character varying(255),
    payment_host_tid character varying(255),
    pto_operation_id character varying(255),
    shift_batch_no bigint,
    shift_code character varying(255),
    software_version character varying(255),
    trip_no character varying(255),
    updated_date_time timestamp without time zone,
    user_id character varying(255)
);

CREATE TABLE financial_txnid_record (
    id integer ,
    transaction_id character varying,
    value integer
);


CREATE TABLE operator_master (
    id integer,
    pto_id character varying,
    operator_id character varying,
    user_name character varying,
    pass_word character varying,
    designation character varying,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone,
    designation_name character varying
);





CREATE TABLE operator_session_management (
    id integer,
    pto_id character varying,
    operator_id character varying,
    admin_transaction_type character varying,
    equipment_id character varying,
    generation_date_time timestamp without time zone,
    process_date_time timestamp without time zone,
    shift_id character varying,
    trip_id character varying
);




CREATE TABLE packet_validation (
    id bigint NOT NULL,
    checksum character varying(255)
);




CREATE TABLE pass_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    pass_type character varying,
    pass_sub_type character varying,
    pass_name character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);




CREATE TABLE pass_transaction_info (
    id integer NOT NULL,
    ticket_number character varying NOT NULL,
    pass_type character varying,
    pass_sub_type character varying,
    pass_validity character varying,
    pass_fare character varying,
    renewable_pass character varying
);




CREATE TABLE payment_mode_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    payment_mode character varying,
    payment_mode_name character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);


CREATE TABLE privilege_master (
    bit_index bigint NOT NULL,
    privilege_id integer NOT NULL,
    pto_id character varying(40) NOT NULL,
    privilege_name character varying(40) NOT NULL,
    privilege_description character varying(40) NOT NULL,
    status boolean NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);



CREATE TABLE pto_master (
    id integer NOT NULL,
    pto_id text NOT NULL,
    pto_name character varying(40) NOT NULL,
    status boolean NOT NULL,
    created_by character varying(40),
    updated_by character varying(40),
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);



CREATE TABLE pto_operation_master (
    id integer NOT NULL,
    pto_id character varying(40),
    pto_operation_id character varying(40) NOT NULL,
    pto_operation_name character varying(40),
    status boolean,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone
);


CREATE TABLE pto_operation_profile (
    id integer  NOT NULL,
    pto_operation_id character varying(40) NOT NULL,
    pto_operation_name character varying(40),
    pto_operation_address character varying(800),
    city character varying(40),
    district character varying(40),
    state character varying(40),
    country character varying(40),
    pin_code character varying(40),
    pto_operation_phone character varying(40),
    pto_operation_mobile character varying(40),
    pto_operation_email character varying(40),
    organisation character varying(40),
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone
);



CREATE TABLE pto_profile (
    id integer  NOT NULL,
    pto_id character varying(40) NOT NULL,
    pto_name character varying(40) NOT NULL,
    pto_address character varying(800) NOT NULL,
    city character varying(40) NOT NULL,
    district character varying(40) NOT NULL,
    state character varying(40) NOT NULL,
    country character varying(40) NOT NULL,
    pin_code character varying(40) NOT NULL,
    pto_phone character varying(40) NOT NULL,
    pto_mobile character varying(40) NOT NULL,
    pto_email character varying(40) NOT NULL,
    organisation character varying(40) NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);


CREATE TABLE recon_transaction_info (
    id integer NOT NULL,
    ticket_number character varying,
    payment_txn_date_time timestamp without time zone,
    payment_txn_unique_id character varying,
    payment_txn_terminal_id character varying
);



CREATE TABLE role_master (
    role_id bigint NOT NULL,
    id bigint DEFAULT nextval('seq_role_master'::regclass) NOT NULL,
    created_date_time timestamp without time zone,
    privileges character varying(255),
    pto_id character varying(255),
    role_description character varying(255),
    role_name character varying(255),
    status boolean,
    updated_date_time timestamp without time zone
);



CREATE TABLE shift_information_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    shift_id character varying,
    shift_name character varying,
    shift_start_time time without time zone NOT NULL,
    shift_end_time time without time zone NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);



CREATE TABLE software_download (
    id integer NOT NULL,
    software_id character varying NOT NULL,
    pto_id character varying(500) NOT NULL,
    equipment_id character varying(800) NOT NULL,
    full_path character varying(500) NOT NULL,
    version character varying NOT NULL,
    created_time timestamp without time zone NOT NULL,
    updated_time timestamp without time zone NOT NULL,
    created_by character varying(40) NOT NULL,
    updated_by character varying(40) NOT NULL
);



CREATE TABLE software_master (
    id integer NOT NULL,
    software_id bigint NOT NULL,
    software_name character varying(40) NOT NULL,
    pto_id character varying(500) NOT NULL,
    equipment_id character varying(800) NOT NULL,
    description character varying(40) NOT NULL,
    base_path character varying(100),
    latest_version character varying(40),
    status boolean NOT NULL,
    created_time timestamp without time zone NOT NULL,
    updated_time timestamp without time zone NOT NULL,
    created_by character varying(40) NOT NULL,
    updated_by character varying(40) NOT NULL
);






CREATE TABLE software_upload (
    id integer,
    software_id bigint NOT NULL,
    pto_id character varying(500) NOT NULL,
    file_path character varying(500),
    software_version character varying(500) NOT NULL,
    status boolean NOT NULL,
    created_time timestamp without time zone NOT NULL,
    updated_time timestamp without time zone NOT NULL,
    created_by character varying(40) NOT NULL,
    updated_by character varying(40) NOT NULL
);




CREATE TABLE stop_master (
    id integer,
    stop_code character varying,
    stop_name character varying
);





CREATE TABLE ticket_master (
    id bigint NOT NULL,
    created_date_time timestamp without time zone,
    pto_operation_id character varying(255),
    ticket_name timestamp without time zone,
    ticket_sub_type character varying(255),
    ticket_type character varying(255),
    updated_date_time timestamp without time zone
);


CREATE TABLE ticket_transaction_info (
    id integer NOT NULL,
    ticket_number character varying NOT NULL,
    transaction_id character varying,
    transport_mode character varying,
    ticket_date_time timestamp without time zone,
    ticket_fare character varying,
    ticket_operation_date date,
    ticket_payment_mode character varying,
    ticket_origin_station_code character varying,
    ticket_dest_station_code character varying,
    ticket_passenger_count character varying,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone,
    duplicate_ticket boolean
);




CREATE TABLE tickets_txn_data (
    id integer,
    ticket_number character varying,
    transaction_id character varying,
    ticket_date_time timestamp without time zone,
    ticket_fare_amount bigint,
    ticket_fare_optional_positive_amount character varying,
    ticket_fare_optional_negative_amount character varying,
    ticket_operation_date date,
    ticket_payment_mode character varying,
    ticket_origin_stop character varying,
    ticket_destination_stop character varying,
    ticket_passenger_count character varying,
    pto_operation_id character varying,
    equipment_id character varying,
    user_employee_id1 character varying,
    user_employee_id2 character varying,
    shift_code character varying,
    shift_batch_number bigint,
    trip_number character varying,
    route_code character varying,
    transport_id character varying,
    current_stop_id character varying,
    card_number character varying,
    card_balance character varying,
    card_expiry_date character varying,
    payment_txn_unique_id character varying,
    payment_txn_terminal_id character varying,
    pass_type_code character varying,
    checksum character varying
);



CREATE TABLE transaction_id_master (
    id integer NOT NULL,
    transaction_id character varying NOT NULL,
    transaction_name character varying NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    transaction_status boolean,
    transaction_type character varying
);




CREATE TABLE transport_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    transport_type character varying,
    transport_sub_type character varying,
    transport_branch_id character varying,
    transport_name character varying,
    transport_address character varying,
    transport_phone character varying,
    transport_mobile character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    transport_mode character varying
);





CREATE TABLE trip_end (
    id bigint DEFAULT nextval('seq_trip_end_table'::regclass) NOT NULL,
    amount_0 integer,
    amount_1 integer,
    amount_10 integer,
    amount_11 integer,
    amount_12 integer,
    amount_13 integer,
    amount_14 integer,
    amount_15 integer,
    amount_16 integer,
    amount_17 integer,
    amount_18 integer,
    amount_19 integer,
    amount_2 integer,
    amount_20 integer,
    amount_21 integer,
    amount_22 integer,
    amount_23 integer,
    amount_24 integer,
    amount_25 integer,
    amount_26 integer,
    amount_27 integer,
    amount_28 integer,
    amount_29 integer,
    amount_3 integer,
    amount_30 integer,
    amount_31 integer,
    amount_32 integer,
    amount_33 integer,
    amount_34 integer,
    amount_35 integer,
    amount_36 integer,
    amount_37 integer,
    amount_38 integer,
    amount_39 integer,
    amount_4 integer,
    amount_40 integer,
    amount_41 integer,
    amount_42 integer,
    amount_43 integer,
    amount_44 integer,
    amount_45 integer,
    amount_46 integer,
    amount_47 integer,
    amount_48 integer,
    amount_49 integer,
    amount_5 integer,
    amount_6 integer,
    amount_7 integer,
    amount_8 integer,
    amount_9 integer,
    count_0 integer,
    count_1 integer,
    count_10 integer,
    count_11 integer,
    count_12 integer,
    count_13 integer,
    count_14 integer,
    count_15 integer,
    count_16 integer,
    count_17 integer,
    count_18 integer,
    count_19 integer,
    count_2 integer,
    count_20 integer,
    count_21 integer,
    count_22 integer,
    count_23 integer,
    count_24 integer,
    count_25 integer,
    count_26 integer,
    count_27 integer,
    count_28 integer,
    count_29 integer,
    count_3 integer,
    count_30 integer,
    count_31 integer,
    count_32 integer,
    count_33 integer,
    count_34 integer,
    count_35 integer,
    count_36 integer,
    count_37 integer,
    count_38 integer,
    count_39 integer,
    count_4 integer,
    count_40 integer,
    count_41 integer,
    count_42 integer,
    count_43 integer,
    count_44 integer,
    count_45 integer,
    count_46 integer,
    count_47 integer,
    count_48 integer,
    count_49 integer,
    count_5 integer,
    count_6 integer,
    count_7 integer,
    count_8 integer,
    count_9 integer,
    created_date_time timestamp without time zone,
    date_time character varying(255),
    equipment_component_version character varying(255),
    equipment_id character varying(255),
    equipment_model_no character varying(255),
    equipment_serial_no character varying(255),
    financial_txn_type character varying(255),
    master_version character varying(255),
    payment_host_batch_no character varying(255),
    payment_host_tid character varying(255),
    pto_operation_id character varying(255),
    shift_batch_no character varying(255),
    shift_code character varying(255),
    software_version character varying(255),
    trip_no character varying(255),
    updated_date_time timestamp without time zone,
    user_id character varying(255)
);






CREATE TABLE trip_information_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    trip_id character varying,
    route_id character varying,
    route_direction character varying,
    trip_name character varying,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL
);


CREATE TABLE user_credentials (
    id integer NOT NULL,
    user_id character varying(40) NOT NULL,
    pass_word character varying(500) NOT NULL,
    status boolean NOT NULL,
    current_login_status boolean NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    user_role character varying(40)
);



CREATE TABLE user_management_master (
    id integer NOT NULL,
    pto_operation_id character varying,
    user_id character varying,
    pass_word character varying,
    user_status boolean,
    address character varying,
    phone character varying,
    mobile character varying,
    email character varying,
    website character varying,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone,
    user_role smallint,
    current_login_status boolean,
    user_name character varying(40),
    city character varying(40),
    district character varying(40),
    state character varying(40),
    country character varying(40),
    pincode character varying(40)
);


CREATE TABLE user_profile (
    id integer NOT NULL,
    user_id character varying(40) NOT NULL,
    user_name character varying(40) NOT NULL,
    pass_word character varying(500) NOT NULL,
    address character varying(800) NOT NULL,
    city character varying(40) NOT NULL,
    district character varying(40) NOT NULL,
    state character varying(40) NOT NULL,
    country character varying(40) NOT NULL,
    pincode character varying(40) NOT NULL,
    phone character varying(40) NOT NULL,
    mobile character varying(40) NOT NULL,
    email character varying(40) NOT NULL,
    organisation character varying(40) NOT NULL,
    created_date_time timestamp without time zone NOT NULL,
    updated_date_time timestamp without time zone NOT NULL,
    user_role integer NOT NULL,
    transaction_id character varying
);


CREATE TABLE user_role_master (
    id integer NOT NULL,
    user_role smallint,
    user_role_description character varying,
    view_transaction_reports boolean,
    create_role boolean,
    view_revenue_reports boolean,
    view_reconciliation_reports boolean,
    created_date_time timestamp without time zone,
    updated_date_time timestamp without time zone
);



CREATE TABLE user_session_management (
    id integer NOT NULL,
    pto_operation_id character varying,
    user_id character varying,
    user_last_activity_date_time timestamp without time zone NOT NULL,
    admin_transaction_type character varying,
    user_name character varying
);

CREATE TABLE pto_operation_userid_relation (
    id integer NOT NULL,
    user_id character varying(40) NOT NULL,
    pto_operation_id character varying(40) NOT NULL,
    process_date_time timestamp without time zone NOT NULL
);