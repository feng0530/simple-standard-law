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
	subject_name varchar(30),
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
('Root'),
('Administrator'),
('Reporter'),
('User');

INSERT INTO system.users_role (user_id, role_id) VALUES
('1', '1'),
('2', '3'),
('3', '4');

INSERT INTO system.func (subject_name, func_name) VALUES
('系統管理','root'),
('系統管理','admin'),
('利率風險','IR01'),
('利率風險','IR02'),
('利率風險','IR03'),
('利率風險','IR04'),
('權益證卷風險','MS01'),
('權益證卷風險','MS02'),
('權益證卷風險','MS03'),
('外匯(含黃金)風險','FE01'),
('外匯(含黃金)風險','FE02'),
('外匯(含黃金)風險','FE03'),
('商品風險','PR01'),
('商品風險','PR02'),
('商品風險','PR03'),
('選擇權風險','SA01'),
('選擇權風險','SA02'),
('市場風險','MR01'),
('信用風險','CR01');

INSERT INTO system.role_func (role_id, func_id, level) VALUES
('1', '1', 'x'),
('2', '2', 'x'),
('3', '3', 'x'),
('3', '4', 'x'),
('3', '5', 'x'),
('3', '6', 'x'),
('4', '7', 'x'),
('4', '8', 'x'),
('4', '9', 'x'),
('4', '10', 'r'),
('4', '11', 'r'),
('4', '12', 'r');