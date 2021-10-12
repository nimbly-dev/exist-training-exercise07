create table IF NOT EXISTS PERSON (
    PERSON_ID  serial not null,
    first_name varchar(255),
    middle_name varchar(255),
    last_name varchar(255),
    suffix varchar(255),
    title varchar(255),
    address varchar(255),
    zip_code varchar(255),
    gwa float8,
    date_hired date,
    birthdate date,
    is_currently_employed boolean,
    ROLE_ID int4,
    primary key (PERSON_ID)

);


create table IF NOT EXISTS ROLE (
    ROLE_ID serial not null,
    role_name varchar(255),
    PERSON_ID int4,
    primary key (ROLE_ID)

);


create table IF NOT EXISTS CONTACT_INFORMATION (
    CONTACT_ID int4 not null,
    PERSON_ID int4,
    landline varchar(255),
    mobile_number varchar(255),
    email varchar(255),
    primary key (CONTACT_ID)

);

alter table CONTACT_INFORMATION 
add constraint FK_CONTACT_ID
foreign key (PERSON_ID) references PERSON;


alter table PERSON add constraint FK_ROLE_ID foreign key (ROLE_ID) 
references ROLE;


alter table ROLE add constraint FK_PERSON_ID foreign key (PERSON_ID)
references PERSON;