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
	create_time timestamp DEFAULT NOW()
);

INSERT INTO system.users (u_name, account, password) VALUES
('root', 'root123', 'root999'),
('Frank', 'frank123', 'frank999'),
('user', 'user123', 'user999');

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
('資料匯入'),
('下載報表'),
('查詢報表');

INSERT INTO system.role_func (r_id, f_id) VALUES
('1', '1'),
('1', '2'),
('1', '3'),
('1', '4'),
('2', '2'),
('2', '3'),
('2', '4'),
('3', '4');