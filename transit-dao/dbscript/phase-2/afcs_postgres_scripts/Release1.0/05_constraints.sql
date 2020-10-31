--From afcs_admin user

COMMENT ON SCHEMA afcs_qa IS '1.0';

SET search_path = afcs_qa, pg_catalog;

ALTER TABLE ONLY admin_session_management
    ADD CONSTRAINT admin_session_management_pkey PRIMARY KEY (id);



ALTER TABLE ONLY admin_transaction_data
    ADD CONSTRAINT admin_transaction_data_pkey PRIMARY KEY (id);


ALTER TABLE ONLY card_master
    ADD CONSTRAINT card_master_pkey PRIMARY KEY (id);


ALTER TABLE ONLY depot_master
    ADD CONSTRAINT depot_master_pk PRIMARY KEY (id, depot_id);


ALTER TABLE ONLY depot_profile
    ADD CONSTRAINT depot_profile_pk PRIMARY KEY (id);


ALTER TABLE ONLY equipment_master
    ADD CONSTRAINT equipment_master_un UNIQUE (equipment_oem_id);



ALTER TABLE ONLY equipment_setup_management
    ADD CONSTRAINT equipment_setup_management_pkey PRIMARY KEY (id);


ALTER TABLE ONLY financial_txn_data
    ADD CONSTRAINT financial_txn_data_pkey PRIMARY KEY (id);

ALTER TABLE ONLY packet_validation
    ADD CONSTRAINT packet_validation_pkey PRIMARY KEY (id);


ALTER TABLE ONLY privilege_master
    ADD CONSTRAINT privilege_master_pk PRIMARY KEY (privilege_id, bit_index);



ALTER TABLE ONLY pto_master
    ADD CONSTRAINT pto_master_pkey PRIMARY KEY (id, pto_id);


ALTER TABLE ONLY pto_operation_master
    ADD CONSTRAINT pto_operation_master_pkey PRIMARY KEY (id, pto_operation_id);


ALTER TABLE ONLY pto_operation_profile
    ADD CONSTRAINT pto_operation_profile_pkey PRIMARY KEY (id, pto_operation_id);



ALTER TABLE ONLY pto_operation_userid_relation
    ADD CONSTRAINT pto_operation_userid_relation_pkey PRIMARY KEY (id);



ALTER TABLE ONLY pto_profile
    ADD CONSTRAINT pto_profile_pkey PRIMARY KEY (id, pto_id);


ALTER TABLE ONLY role_master
    ADD CONSTRAINT role_master_pkey PRIMARY KEY (role_id, id);



ALTER TABLE ONLY software_download
    ADD CONSTRAINT software_download_table_pkey PRIMARY KEY (id);



ALTER TABLE ONLY ticket_master
    ADD CONSTRAINT ticket_master_pkey PRIMARY KEY (id);



ALTER TABLE ONLY trip_end
    ADD CONSTRAINT trip_end_pkey PRIMARY KEY (id);



ALTER TABLE ONLY user_credentials
    ADD CONSTRAINT user_credentials_pkey PRIMARY KEY (id);


ALTER TABLE ONLY user_profile
    ADD CONSTRAINT user_profile_pkey PRIMARY KEY (id);