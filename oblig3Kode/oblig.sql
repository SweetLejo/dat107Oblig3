create schema oblig;
set search_path to oblig;

create table employee
(
  employee_id serial primary key,
  unique_name varchar(4) check(length(name) >= 3),
  first_name varchar(30),
  last_name varchar(30),
  employment_date date,
  stilling varchar(30),
  wage numeric(10, 2),
  department_id int not null on delete restrict,
  forgein key (department_id) references department(department_id),
  project int forgein key references project(id)
  );

create table departemnt
(
  department_id serial primary key,
  department_name varchar(30),
  boss_employee_id int not null references emplyee_id

);

create table project
(
  project_id serial primary key,
  project_name varchar(30),
  beskrivelse TEXT,
  number_of_hours int,
  employee_id int references employee(employee_id),
);

