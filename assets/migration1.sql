CREATE TABLE administrators (
	_id integer NOT NULL,
	name varchar(255),
	phone_numbers varchar(255),
	email varchar(255),
	office_id integer,
	PRIMARY KEY (_id)
);
CREATE TABLE cities (
	_id integer NOT NULL,
	name varchar(255),
	code varchar(255),
	country_id integer,
	PRIMARY KEY (_id)
);
CREATE TABLE companies (
	_id integer NOT NULL,
	name varchar(255),
	PRIMARY KEY (_id)
);
CREATE TABLE countries (
	_id integer NOT NULL,
	name varchar(255),
	code varchar(255),
	PRIMARY KEY (_id)
);
CREATE TABLE offices (
	_id integer NOT NULL,
	name varchar(255),
	address varchar(255),
	longitude numeric(0),
	latitude numeric(0),
	phone_numbers varchar(255),
	map varchar(255),
	email varchar(255),
	city_id integer,
	company_id integer,
	PRIMARY KEY (_id)
);
CREATE TABLE place_types (
	_id integer NOT NULL,
	name varchar(255),
	PRIMARY KEY (_id)
);
CREATE TABLE places (
	_id integer NOT NULL,
	name varchar(255),
	description varchar(255),
	address varchar(255),
	longitude numeric(0),
	latitude numeric(0),
	phone_numbers varchar(255),
	email varchar(255),
	place_type_id integer,
	city_id integer,
	PRIMARY KEY (_id)
);