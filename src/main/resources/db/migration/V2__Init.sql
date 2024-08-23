DROP TABLE IF EXISTS system.users CASCADE;
CREATE TABLE system.users (
	u_id serial primary key,
	u_name varchar(30) not null,
	account varchar(50) not null UNIQUE,
	password varchar(256) not null,
	create_time timestamp DEFAULT now()
);

DROP TABLE IF EXISTS system.role CASCADE;
CREATE TABLE system.role (
	r_id serial primary key,
	r_name varchar(30) not null,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.users_role;
CREATE TABLE system.users_role (
	ur_id serial primary key,
    u_id integer REFERENCES system.users(u_id) ON DELETE CASCADE,
    r_id integer REFERENCES system.role(r_id) ON DELETE CASCADE,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.func CASCADE;
CREATE TABLE system.func (
	f_id serial primary key,
	f_name varchar(30) not null,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.role_func;
CREATE TABLE system.role_func (
	rf_id serial primary key,
	r_id integer REFERENCES system.role(r_id) ON DELETE CASCADE,
	f_id integer REFERENCES system.func(f_id) ON DELETE CASCADE,
	level char(1) not null,
	create_time timestamp DEFAULT NOW()
);

INSERT INTO system.users (u_name, account, password) VALUES
('root', 'root123', '$2a$10$qW4jrdeNriYK7Y9qq6XD1.MqE6xrFV4sGbO88ox5mgsPC.RFTewXS'),
('Frank', 'frank123', '$2a$10$zur3aJjKYmwoNjsHKfAsRuZlWh0MCFIDHNQzfttHuNj3bSNJdNOfq'),
('user', 'user123', '$2a$10$6QrQczRvK8NV3C6yR7RUJOk53NFO3dcqlLsIcr8TgVIknwzuRNK/C');

INSERT INTO system.role (r_name) VALUES
('Administrator'),
('Reporter'),
('User');

INSERT INTO system.users_role (u_id, r_id) VALUES
('1', '1'),
('2', '2'),
('3', '3');

INSERT INTO system.func (f_name) VALUES
('root'),
('業務1'),
('業務2'),
('業務3');

INSERT INTO system.role_func (r_id, f_id, level) VALUES
('1', '1', ''),
('2', '2', 'x'),
('2', '3', 'x'),
('2', '4', 'x'),
('3', '2', 'r'),
('3', '3', 'r'),
('3', '4', 'r');