create table department
(
    dept_name varchar(20),
    building  varchar(15),
    budget    numeric(12, 2) check (budget > 0),
    primary key (dept_name)
);

create table instructor
(
    ID        varchar(5),
    name      varchar(20) not null,
    dept_name varchar(20),
    salary    numeric(8, 2) check (salary > 29000),
    primary key (ID),
    foreign key (dept_name) references department (dept_name)
        on delete set null
);