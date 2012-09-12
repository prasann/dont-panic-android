CREATE TABLE administrators (
	id integer NOT NULL,
	name varchar(255),
	phone_numbers varchar(255),
	email varchar(255),
	office_id integer,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE cities (
	id integer NOT NULL,
	name varchar(255),
	code varchar(255),
	country_id integer,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE companies (
	id integer NOT NULL,
	name varchar(255),
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE countries (
	id integer NOT NULL,
	name varchar(255),
	code varchar(255),
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE offices (
	id integer NOT NULL,
	name varchar(255),
	address varchar(255),
	longitude numeric(0),
	latitude numeric(0),
	phone_numbers varchar(255),
	email varchar(255),
	city_id integer,
	company_id integer,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE place_types (
	id integer NOT NULL,
	name varchar(255),
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE places (
	id integer NOT NULL,
	name varchar(255),
	description varchar(255),
	address varchar(255),
	longitude numeric(0),
	latitude numeric(0),
	phone_numbers varchar(255),
	email varchar(255),
	place_type_id integer,
	city_id integer,
	created_at timestamp NOT NULL,
	updated_at timestamp NOT NULL,
	PRIMARY KEY (id)
);