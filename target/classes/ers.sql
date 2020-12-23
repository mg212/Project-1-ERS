-- author @mgonchar
-- public@youcan
-- revaturedb.caqmi7gfbcow.us-east-2.rds.amazonaws.com 5432

DROP TABLE IF exists ers_user_roles CASCADE;
DROP TABLE IF exists ers_reimbursements_types CASCADE;
DROP TABLE IF exists ers_reimbursements_status CASCADE;
DROP TABLE IF exists ers_users CASCADE;
DROP TABLE IF exists ers_reimbursements CASCADE;

COMMIT;
ROLLBACK;

SELECT * FROM ers_users;
SELECT * FROM ers_user_roles;
SELECT * FROM ers_reimbursements;
SELECT * FROM ers_reimbursements_status;
SELECT * FROM ers_reimbursements_types;

-- DDL
-- ers_user_roles
CREATE TABLE ers_user_roles(
	ers_role_id SERIAL PRIMARY KEY,	
	ers_role_name VARCHAR(50) NOT NULL
);

-- ers_reimbursement_type
CREATE TABLE ers_reimbursements_types(
	type_id serial PRIMARY KEY,
	type_name varchar(10) NOT NULL
);

-- ers_reimbursement_status
CREATE TABLE ers_reimbursements_status(
	status_id SERIAL PRIMARY KEY,
	status_name VARCHAR(10) NOT NULL
);

COMMIT;
ROLLBACK; 

-- ers_users
CREATE TABLE ers_users(
	ers_user_id SERIAL PRIMARY KEY,
	ers_user_role_id INTEGER REFERENCES ers_user_roles(ers_role_id) NOT NULL, 	
	ers_username VARCHAR(50) UNIQUE NOT NULL,
	ers_user_password VARCHAR(50) NOT NULL,
	ers_user_first_name VARCHAR(100) NOT NULL,
	ers_user_last_name VARCHAR(100) NOT NULL,
	ers_user_email VARCHAR(150) UNIQUE NOT NULL
	-- user_role_id is an INTEGER 1: Employee 2: Finance Manager
);

-- ers_reimbursement
CREATE TABLE ers_reimbursements(
	reimb_id SERIAL PRIMARY KEY,
	reimb_amount NUMERIC(7,2) NOT NULL,
	reimb_submitted TIMESTAMP NOT NULL,	
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(300),
	reimb_author_id INTEGER REFERENCES ers_users(ers_user_id) NOT NULL,	
	reimb_resolver_id INTEGER REFERENCES ers_users(ers_user_id) NOT NULL,
	reimb_status_id INTEGER REFERENCES ers_reimbursements_status(status_id) NOT NULL,
	reimb_type_id INTEGER REFERENCES ers_reimbursements_types(type_id) NOT NULL
);

COMMIT;
ROLLBACK;

--ers_user_roles table data
INSERT INTO ers_user_roles(ers_role_name)
VALUES 
	('Employee'),
	('Finance Manager');

-- ers_reimbursements_types table data
INSERT INTO ers_reimbursements_types(type_name)
VALUES
	('LODGING'),
	('TRAVEL'),
	('FOOD'),
	('OTHER');

-- ers_reimbursements_status table data
INSERT INTO ers_reimbursements_status(status_name)
VALUES
	('Approved'),
	('Pending'),
	('Denied');

COMMIT;

--ers_users table data 	-- role_id is an INTEGER 1: Employee 2: Finance Manager
INSERT INTO ers_users (
	ers_user_role_id, 
	ers_username, 
	ers_user_password, 
	ers_user_first_name, 
	ers_user_last_name, 
	ers_user_email)
VALUES 
	(1, 'ashleyo', 'p4ssw0rd', 'Ashley', 'O', 'ashley@ers.com'),
	(1, 'hatsunem', 'p4ssw0rd', 'Hatsune', 'Miku', 'hatsune@ers.com'),
	(2, 'maxg', 'p4ssw0rd', 'Max', 'Manager', 'max@ers.com');

COMMIT;
ROLLBACK;

TRUNCATE ers_reimbursements;

--ers_reimbursement table data
INSERT INTO ers_reimbursements(
	reimb_amount, 
	reimb_submitted, 
	reimb_resolved, 
	reimb_description, 
--	
	reimb_author_id, 
	reimb_resolver_id, 
	reimb_status_id, 
	reimb_type_id) 
VALUES

	(350.00, '2020-01-01 10:10:00', NULL, 'Three nights at a Tokyo hotel.', 2, 3, 2, 1),
	(2400.00, '2020-01-01 10:10:00', NULL, 'Flight from New York to Brisbaine.', 1, 3, 2, 2),
	(45.00, '2020-01-01 10:10:00', NULL, 'Taxi from the airport to hotel.', 1, 3, 2, 2),
	(100.00, '2020-01-01 10:10:00', '2020-01-01 12:00:00', 'Stakehouse Dinner allowance.', 2, 3, 1, 3);

COMMIT;


SELECT * FROM ers_users;
SELECT * FROM ers_user_roles;
SELECT * FROM ers_reimbursements;
SELECT * FROM ers_reimbursements_status;
SELECT * FROM ers_reimbursements_types;

COMMIT;
ROLLBACK;

---------------------------------------
---------------------------------------
---------------------------------------
---------------------------------------

COMMIT;
ROLLBACK;

SELECT * FROM ers_users;
SELECT * FROM ers_user_roles;
SELECT * FROM ers_reimbursements;
SELECT * FROM ers_reimbursements_status;
SELECT * FROM ers_reimbursements_types;

--TRUNCATE TABLE ers_reimbursements;

--FKs of type SERIAL link back to PKs of type SERIAL
--reimb_author_id is FK INTEGER relating to SERIAL ers_user_id
