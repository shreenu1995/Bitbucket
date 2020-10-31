COMMENT ON SCHEMA afcs_qa IS '2.0';

SET search_path = afcs_qa, pg_catalog;

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('admin_transaction', 1, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('tms_transaction', 2, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('payment_transaction', 3, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('financial_transaction', 4, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('transit_transaction', 5, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('equipment_transaction', 6, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('operator_transaction', 7, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');

INSERT INTO afcs_dev.transaction_category_master (transaction_category_name, transaction_category, transaction_status, created_time, updated_time, created_by, updated_by)
VALUES('ticket_transaction', 8, true, '2018-11-15 20:09:03', '2018-11-15 20:09:03', 'shakthivel.ganesan@girmiti.com', 'shakthivel.ganesan@girmiti.com');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(1, 'WA01', 'web_login',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(2, 'WA02', 'web_logout',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(3, 'WA03', 'web_session_alive',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(4, 'WA04', 'web_session_timout',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(5, 'WA05', 'user_registration',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(6, 'WA06', 'user_name_already_exists_check_api',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(7, 'WA07', 'forgot_password',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(8, 'WA08', 'user_profile_update_api',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(9, 'WA09', 'change_password',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(10, 'WA10', 'user_status_update',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(11, 'WA11', 'user_status_check',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(12, 'WA12', 'user_list_view',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(13, 'WA13', 'role_registration',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(14, 'WA14', 'role_profile_update',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(15, 'WA15', 'role_status_update',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(16, 'WA16', 'role_status_check',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(17, 'WA17', 'role_list_view',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(18, 'WA18', 'role_previlages',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(19, 'WA19', 'role_exist_check',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(20, 'WA20', 'previlage_registration',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(21, 'WA21', 'previlage_profile_update',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(22, 'WA22', 'previlage_status_update',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(23, 'WA23', 'previlage_status_check',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(24, 'WA24', 'previlage_list_view',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(25, 'WA25', 'privilage_exist_check',  true, '1', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(26, 'TM01', 'software_registration',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(27, 'TM02', 'software_profile_update',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(28, 'TM03', 'software_status_update',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(29, 'TM04', 'software_upload',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(30, 'TM05', 'software_download',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(31, 'TM06', 'software_assign',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(32, 'TM07', 'software_status_check',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(33, 'TM08', 'software_list_view',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(34, 'TM09', 'software_verison_check',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(35, 'TM10', 'software_exist_check',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(36, 'TM11', 'software_info_check_data',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(37, 'TM12', 'master_registration',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(38, 'TM13', 'master_profile_update',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(39, 'TM14', 'master_status_update',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(40, 'TM15', 'master_upload',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(41, 'TM16', 'master_download',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(42, 'TM17', 'master_assign',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(43, 'TM18', 'master_status_check',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(44, 'TM19', 'master_list_view',  true, '2', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(45, 'P001', 'payment_mode_registration',  true, '3', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(46, 'P002', 'payment_mode_profile_update',  true, '3', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(47, 'P003', 'payment_mode_status_update',  true, '3', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(48, 'P004', 'payment_mode_status_check',  true, '3', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(49, 'P005', 'payment_mode_list_view',  true, '3', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(50, 'F001', 'shift_end',  true, '4', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(51, 'F002', 'trip_end',  true, '4', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(52, 'F003', 'settlement',  true, '4', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(53, 'TR01', 'pto_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(54, 'TR02', 'pto_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(55, 'TR03', 'pto_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(56, 'TR04', 'pto_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(57, 'TR05', 'pto_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(58, 'TR06', 'pto_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(59, 'TR07', 'pto_operation_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(60, 'TR08', 'pto_operation_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(61, 'TR09', 'pto_operation_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(62, 'TR10', 'pto__operation_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(63, 'TR11', 'pto_operation_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(64, 'TR12', 'pto_operation_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(65, 'TR13', 'pto_operation_permission_request',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(66, 'TR14', 'depot_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(67, 'TR15', 'depot_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(68, 'TR16', 'depot_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(69, 'TR17', 'depot_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(70, 'TR18', 'depot_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(71, 'TR19', 'depot_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(72, 'TR20', 'station_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(73, 'TR21', 'station_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(74, 'TR22', 'station_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(75, 'TR23', 'station_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(76, 'TR24', 'station_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(77, 'TR25', 'station_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(78, 'TR26', 'route_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(79, 'TR27', 'route_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(80, 'TR28', 'route_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(81, 'TR29', 'route_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(82, 'TR30', 'route_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(83, 'TR31', 'route_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(84, 'TR32', 'add_stop_station_to_route',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(85, 'TR33', 'stop_station_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(86, 'TR34', 'update_stop_station_of_route',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(87, 'TR35', 'stop_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(88, 'TR36', 'stop_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(89, 'TR37', 'stop_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(90, 'TR38', 'stop_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(91, 'TR39', 'stop_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(92, 'TR40', 'fare_logic_registartion',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(93, 'TR41', 'fare_logic_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(94, 'TR42', 'fare_logic_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(95, 'TR43', 'fare_logic_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(96, 'TR44', 'fare_logic_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(97, 'TR45', 'pass_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(98, 'TR46', 'pass_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(99, 'TR47', 'pass_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(100, 'TR48', 'pass_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(101, 'TR49', 'pass_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(102, 'TR50', 'shift_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(103, 'TR51', 'shift_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(104, 'TR52', 'shift_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(105, 'TR53', 'shift_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(106, 'TR54', 'shift_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(107, 'TR55', 'shift_exist_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(108, 'TR56', 'trip_registration',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(109, 'TR57', 'trip_profile_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(110, 'TR58', 'trip_status_update',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(111, 'TR59', 'trip_status_check',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(112, 'TR60', 'trip_list_view',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(113, 'TR61', 'trip_existcheck',  true, '5', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(114, 'EQ01', 'equipment_registration',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(115, 'EQ02', 'equipment_profile_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(116, 'EQ03', 'equipment_status_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(117, 'EQ04', 'equipment_lending_service',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(118, 'EQ05', 'equipment_status_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(119, 'EQ06', 'equipment_list_view',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(120, 'EQ07', 'equipment_exist_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(121, 'EQ08', 'equipment_type_registration',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(122, 'EQ09', 'equipment_type_profile_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(123, 'EQ10', 'equipment_type_status_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(124, 'EQ11', 'equipment_type_status_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(125, 'EQ12', 'equipment_type_list_view',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(126, 'EQ13', 'equipment_type_exist_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(127, 'EQ14', 'equipment_model_registration',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(128, 'EQ15', 'equipment_model_profile_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(129, 'EQ16', 'equipment_model_status_update',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(130, 'EQ17', 'equipment_model_status_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(131, 'EQ18', 'equipment_model_list_view',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(132, 'EQ19', 'equipment_model_exist_check',  true, '6', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(133, 'OP01', 'employee_registration',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(134, 'OP02', 'employee_profile_update',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(135, 'OP03', 'employee_status_update',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(136, 'OP04', 'employee_status_check',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(137, 'OP05', 'employee_list_view',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(138, 'OP06', 'employee_exist_check',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(139, 'OP07', 'designation_registration',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(140, 'OP08', 'designation_profile_update',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(141, 'OP09', 'designation_status_update',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(142, 'OP10', 'designation_status_check',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');


INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(143, 'OP11', 'designation_list_view',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(144, 'OP12', 'designation_exist_check',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(145, 'OP13', 'operator_login',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(146, 'OP14', 'operator_logout',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(147, 'OP15', 'operator_session_alive',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(148, 'OP16', 'operator_session_timeout',  true, '7', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(149, 'TK01', 'luggage_ticket_light_weight',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(150, 'TK02', 'luggage_ticket_heavy_weight',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(151, 'TK03', 'penalty_ticket_adult',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(152, 'TK04', 'penalty_ticket_child',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(153, 'TK05', 'penalty_ticket_senior_citizen',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(154, 'TK06', 'penalty_ticket_deaf',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(155, 'TK07', 'penalty_ticket_dumb',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(156, 'TK08', 'penalty_ticket_blind',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(157, 'TK09', 'penalty_ticket_luggage',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(158, 'TK10', 'penalty_station_platform_ticket',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(159, 'TK11', 'travel_ticket_adult',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(160, 'TK12', 'travel_ticket_child',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');



INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(161, 'TK13', 'travel_ticket_senior_citizen',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(162, 'TK14', 'travel_ticket_deaf',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(163, 'TK15', 'travel_ticket_dumb',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(164, 'TK16', 'travel_ticket_blind',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(165, 'TK17', 'station_platform_ticket',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');

INSERT INTO afcs_dev.transaction_id_master (id, transaction_id, transaction_name, transaction_status, transaction_category,created_date_time, updated_date_time)
VALUES(166, 'TK18', 'top_up',  true, '8', '2018-11-16 10:30:30', '2018-11-16 10:30:30');
