DROP SCHEMA IF EXISTS oblig CASCADE;
CREATE SCHEMA oblig;
SET search_path TO oblig;

CREATE TABLE department (
  department_id SERIAL PRIMARY KEY,
  department_name VARCHAR(30),
  boss_employee_id INT NOT NULL --REFERENCES employee(employee_id) ON DELETE RESTRICT
);

CREATE TABLE employee (
  employee_id SERIAL PRIMARY KEY,
  unique_name VARCHAR(4) CHECK(length(unique_name) >= 3) UNIQUE,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  employment_date DATE,
  job_title VARCHAR(30),
  wage NUMERIC(10, 2),
  department_id INT NOT NULL REFERENCES department(department_id) ON DELETE RESTRICT,
  project_id INT --REFERENCES project(project_id)
);

CREATE TABLE project (
  project_id SERIAL PRIMARY KEY,
  project_name VARCHAR(30),
  beskrivelse TEXT,
  number_of_hours INT,
  employee_id INT REFERENCES employee(employee_id)
);

CREATE TABLE project_participation (
  participation_id SERIAL PRIMARY KEY,
  employee_id INT NOT NULL REFERENCES employee(employee_id) ON DELETE RESTRICT,
  project_id INT NOT NULL REFERENCES project(project_id) ON DELETE CASCADE,
  time_spent INT CHECK (time_spent >= 0)
);

INSERT INTO department (department_name, boss_employee_id) VALUES
('Sales', 1),
('Marketing', 2),
('HR', 3),
('IT', 4);


INSERT INTO employee (unique_name, first_name, last_name, employment_date, job_title, wage, department_id) VALUES
('ABC', 'John', 'Doe', '2022-01-01', 'Sales Manager', 5000, 1),
('DEF', 'Jane', 'Smith', '2022-01-01', 'Sales Manager', 5000, 4),
('GHI', 'Bob', 'Johnson', '2022-02-01', 'Sales Representative', 3000, 3),
('JKL', 'Sara', 'Lee', '2022-03-01', 'Marketing Manager', 4500, 2),
('MNO', 'Tom', 'Wilson', '2022-04-01', 'Marketing Representative', 2500, 2),
('PQR', 'Amy', 'Nguyen', '2022-05-01', 'Marketing Representative', 2500, 2);


ALTER TABLE employee ADD CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE SET NULL;
alter table department add constraint fk_employee foreign key (boss_employee_id) references employee(employee_id) ON DELETE RESTRICT;


INSERT INTO project (project_name, beskrivelse, number_of_hours, employee_id)
VALUES
  ('entities.Project 6', 'Description 1', 200, 1),
  ('entities.Project 7', 'Description 2', 300, 2);


INSERT INTO project_participation (employee_id, project_id, time_spent)
VALUES
  (1, 1, 10),
  (2, 1, 5),
  (3, 2, 20),
  (4, 2, 15),
  (5, 1, 30);











