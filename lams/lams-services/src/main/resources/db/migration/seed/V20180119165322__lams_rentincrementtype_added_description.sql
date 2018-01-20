delete from eglams_rentincrementtype where tenant_id = 'default';

INSERT INTO eglams_rentincrementtype (id, type, description, asset_category, flat_amount, fromdate, todate, percentage, created_by, last_modified_by, created_date, last_modified_date,tenant_id) VALUES (nextval('seq_eglams_rentincrement'), 'Goodwill Auction Basis', '33.33% excess over the lease amount', 1, 0.00, '2018-01-01 00:00:00', '2030-12-31 00:00:00', 33.33, '61', null, '2018-01-01 00:00:00', null,'default');
INSERT INTO eglams_rentincrementtype (id, type, description, asset_category, flat_amount, fromdate, todate, percentage, created_by, last_modified_by, created_date, last_modified_date,tenant_id) VALUES (nextval('seq_eglams_rentincrement'), 'Normal Basis', '10% of the current market value', 1, 0.00, '2018-01-01 00:00:00', '2030-12-31 00:00:00', 10.00, '61', null, '2018-01-01 00:00:00', null,'default');
INSERT INTO eglams_rentincrementtype (id, type, description, asset_category, flat_amount, fromdate, todate, percentage, created_by, last_modified_by, created_date, last_modified_date,tenant_id) VALUES (nextval('seq_eglams_rentincrement'), 'Normal Basis', '33.33% above of the earlier amount', 1, 0.00, '2018-01-01 00:00:00', '2030-12-31 00:00:00', 33.33, '61', null, '2018-01-01 00:00:00', null,'default');
INSERT INTO eglams_rentincrementtype (id, type, description, asset_category, flat_amount, fromdate, todate, percentage, created_by, last_modified_by, created_date, last_modified_date,tenant_id) VALUES (nextval('seq_eglams_rentincrement'), 'Normal Basis', 'Prevailing rent of such properties in the vicinity', 1, 0.00, '2018-01-01 00:00:00', '2030-12-31 00:00:00', 0.00, '61', null, '2018-01-01 00:00:00', null,'default');