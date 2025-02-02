ALTER SESSION SET CONTAINER = FREEPDB1;
CREATE user cac IDENTIFIED BY "PuroPincheOracle1.";
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE PUBLIC SYNONYM TO cac;
GRANT SYSDBA to cac;
ALTER USER cac QUOTA UNLIMITED ON USERS;
ALTER SESSION SET CURRENT_SCHEMA = cac;
--Remove this lines of code if using the freedb image abd comment the next one
--ALTER SESSION SET CURRENT_SCHEMA = API;

CREATE TABLE cac.students(
	student_id NUMBER  GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT student_pk PRIMARY KEY,
	first_name varchar2(64) NOT NULL,
	email varchar2(64),
	last_name varchar2(64) NOT NULL,
	register_date date DEFAULT CURRENT_TIMESTAMP,
	siiau_code varchar2(9)  CONSTRAINT unique_siiau_code UNIQUE
);

CREATE TABLE cac.groups(
    group_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT group_pk PRIMARY KEY,
    group_name VARCHAR2(64) NOT NULL
);

CREATE TABLE cac.users(
	user_id NUMBER  GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0 START WITH 0 CACHE 20 CONSTRAINT user_pk PRIMARY KEY,
	password varchar2(64) NOT NULL,
	student_id NUMBER  CONSTRAINT user_student_fk REFERENCES students(student_id) --Nullable?
);

CREATE TABLE cac.code_profiles(
	code_profile_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT code_profile_pk PRIMARY KEY,
	platform varchar2(64) NOT NULL,
	identifier varchar2(64) NOT NULL,
	student_id NUMBER CONSTRAINT code_profile_student_fk REFERENCES students(student_id)
);

CREATE TABLE cac.classes(
	class_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT class_pk PRIMARY KEY,
	name varchar2(128) NOT NULL,
	description varchar2(256),
	class_date TIMESTAMP,
    group_id NUMBER NOT NULL CONSTRAINT class_group_fk REFERENCES groups(group_id),
	professor_id NUMBER  CONSTRAINT resource_user_fk REFERENCES users(user_id)
);

CREATE TABLE cac.class_resources(
	resource_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT resource_pk PRIMARY KEY,
	content_type CHAR(1) NOT NULL,
	blob_content BLOB,
	clob_content CLOB,
	content_url varchar2(256),
	description varchar2(512),
	upload_date TIMESTAMP DEFAULT SYSDATE,
	user_id NUMBER  CONSTRAINT class_professor_fk REFERENCES students(student_id),
	class_id NUMBER  CONSTRAINT resource_class_fk REFERENCES classes(class_id)
);

CREATE TABLE cac.attendances(
	attendance_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT attendance_pk PRIMARY KEY,
    student_id NUMBER NOT NULL CONSTRAINT attendance_student_fk REFERENCES students(student_id),
	class_id NUMBER NOT NULL CONSTRAINT attendance_class_fk REFERENCES classes(class_id)
);

CREATE TABLE cac.contests(
	contest_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT contest_pk PRIMARY KEY,
	total_problems NUMBER(2) NOT NULL,
	start_date TIMESTAMP DEFAULT SYSDATE,
	resource_id NUMBER CONSTRAINT contest_resource_fk REFERENCES class_resources(resource_id),
	difficulty NUMBER,
	name VARCHAR2(256)
);

CREATE TABLE cac.submissions(
	submission_id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 0  START WITH 0 CACHE 20 CONSTRAINT submission_pk PRIMARY KEY,
	problem VARCHAR2(64) NOT NULL,
	verdict VARCHAR2(50)  NOT NULL,
	penalty NUMBER(5) DEFAULT 0,
	code_profile_id NUMBER NOT NULL CONSTRAINT submission_profile_fk REFERENCES code_profiles(code_profile_id),
	contest_id NUMBER NOT NULL CONSTRAINT submission_contest_fk REFERENCES contests(contest_id)
);
