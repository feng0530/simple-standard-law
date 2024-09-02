DROP TABLE IF EXISTS system.users CASCADE;
CREATE TABLE system.users (
	user_id serial primary key,
	user_name varchar(30) not null,
	account varchar(50) not null UNIQUE,
	password varchar(256) not null,
	create_time timestamp DEFAULT now()
);

DROP TABLE IF EXISTS system.role CASCADE;
CREATE TABLE system.role (
	role_id serial primary key,
	role_name varchar(30) not null UNIQUE,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.users_role;
CREATE TABLE system.users_role (
	user_role_id serial primary key,
    user_id integer REFERENCES system.users(user_id) ON DELETE CASCADE,
    role_id integer REFERENCES system.role(role_id) ON DELETE CASCADE,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.func CASCADE;
CREATE TABLE system.func (
	func_id serial primary key,
	func_name varchar(30) not null UNIQUE,
	create_time timestamp DEFAULT NOW()
);

DROP TABLE IF EXISTS system.role_func;
CREATE TABLE system.role_func (
	role_func_id serial primary key,
	role_id integer REFERENCES system.role(role_id) ON DELETE CASCADE,
	func_id integer REFERENCES system.func(func_id) ON DELETE CASCADE,
	level char(1) not null,
	create_time timestamp DEFAULT NOW()
);

INSERT INTO system.users (user_name, account, password) VALUES
('root', 'root123', '$2a$10$qW4jrdeNriYK7Y9qq6XD1.MqE6xrFV4sGbO88ox5mgsPC.RFTewXS'),
('Frank', 'frank123', '$2a$10$zur3aJjKYmwoNjsHKfAsRuZlWh0MCFIDHNQzfttHuNj3bSNJdNOfq'),
('user', 'user123', '$2a$10$6QrQczRvK8NV3C6yR7RUJOk53NFO3dcqlLsIcr8TgVIknwzuRNK/C');

INSERT INTO system.role (role_name) VALUES
('Administrator'),
('Reporter'),
('User');

INSERT INTO system.users_role (user_id, role_id) VALUES
('1', '1'),
('2', '2'),
('3', '3');

INSERT INTO system.func (func_name) VALUES
('root'),
('business1'),
('business2'),
('business3');

INSERT INTO system.role_func (role_id, func_id, level) VALUES
('1', '1', '*'),
('2', '2', 'x'),
('2', '3', 'x'),
('2', '4', 'x'),
('3', '2', 'r'),
('3', '3', 'r'),
('3', '4', 'r');