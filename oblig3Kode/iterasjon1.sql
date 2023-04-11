DROP SCHEMA IF EXISTS iterasjon1 CASCADE;
CREATE SCHEMA iterasjon1;
SET search_path TO iterasjon1;


CREATE TABLE employee (
  employee_id SERIAL PRIMARY KEY,
  unique_name VARCHAR(4) CHECK(length(unique_name) >= 3),
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  employment_date DATE,
  job_title VARCHAR(30),
  wage NUMERIC(10, 2)
);

insert into employee(unique_name, first_name, last_name, employment_date, job_title, wage) values
('ABC', 'John', 'Doe', '2022-01-01', 'Sales Manager', 50),
('DEF', 'Jane', 'Smith', '2022-01-01', 'Sales Manager', 60);