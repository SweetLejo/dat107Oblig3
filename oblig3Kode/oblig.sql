DROP SCHEMA IF EXISTS oblig CASCADE;
create schema oblig;
set search_path to oblig;

create table employee
(
  employee_id serial primary key,
  unique_name varchar(4) check(length(name) >= 3),
  first_name varchar(30),
  last_name varchar(30),
  employment_date date,
  job_title varchar(30),
  wage numeric(10, 2),
  department_id int not null on delete restrict,

  project_id int,
  forgein key (department_id) references department(department_id),
  forgein key (project_id) references project(project_id)
  );

create table departemnt
(
  department_id serial primary key,
  department_name varchar(30),
  boss_employee_id int not null on delete restrict,
  forgein key (boss_employee_id) references employee(employee_id)

);

create table project
(
  project_id serial primary key,
  project_name varchar(30),
  beskrivelse TEXT,
  number_of_hours int,
  employee_id int references employee(employee_id),
);


create table project_participation
(
  participation_id serial primary key,
  employee_id int not null references employee(employee_id),
  project_id int not null references project(project_id),
  time_spent int,

);
