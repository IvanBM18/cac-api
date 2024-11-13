ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;
--COMMIT;

--ALTER SESSION SET CONTAINER = CACDB;

INSERT INTO students(first_name,last_name,register_date,siiau_code,email)
VALUES('Ivan','Barba macias',TO_DATE('2020 Feb 01','YYYY MON DD'),'219747662','ivan.barba7476@alumnos.udg.mx');
INSERT INTO students(first_name,last_name,register_date,siiau_code,email)
VALUES('Dana Caro','Ramirez Algo',TO_DATE('2024 Jan 01','YYYY MON DD'),'219727123','dana.ramirez@alumnos.udg.mx');
INSERT INTO students(first_name,last_name,register_date,siiau_code,email)
VALUES('Moises','Martinez',TO_DATE('2022 Mar 12','YYYY MON DD'),'216123456','moises.martinez@alumnos.udg.mx');
INSERT INTO students(first_name,last_name,register_date,siiau_code,email)
VALUES('Alexa','Salcedo Arellano',TO_DATE('2024 Dec 12','YYYY MON DD'),'216654321','alexa.salcedo@alumnos.udg.mx');
INSERT INTO students(first_name,last_name,register_date,siiau_code,email)
VALUES('Argenis','Algo Robles',TO_DATE('2024 Aug 31','YYYY MON DD'),'216101010','Argenis.lopez@alumnos.udg.mx');

INSERT INTO users(email,password,student_id)
VALUES ('ivanbarba.m@gmail.com','123456',0);

INSERT INTO groups
VALUES(DEFAULT,'Basicos');
INSERT INTO groups
VALUES(DEFAULT,'Intermedios');

INSERT INTO code_profiles(platform,identifier,student_id)
VALUES('CodeForces','ivanbm',0);
INSERT INTO code_profiles(platform,identifier,student_id)
VALUES('VJudge','ivanbm8',0);

INSERT INTO classes
VALUES(DEFAULT,'Strings I','Introduccion a Strings', TO_DATE('2024 FEB 07','YYYY MON DD'),0,0);
INSERT INTO classes
VALUES(DEFAULT,'Estructuras de Datos I','Stack, Queue, Deque, Map', TO_DATE('2024 JAN 31','YYYY MON DD'),0,0);

INSERT INTO class_resources
VALUES(NULL,'U',null,null,'https://codeforces.com/group/cG3CULqTG9/contest/510084',
'CodeForces URL to Contest', DEFAULT,0,0);

INSERT INTO attendances
VALUES(DEFAULT,0,0);
INSERT INTO attendances
VALUES(DEFAULT,0,1);
INSERT INTO attendances
VALUES(DEFAULT,2,0);
INSERT INTO attendances
VALUES(DEFAULT,2,1);
INSERT INTO attendances
VALUES(DEFAULT,3,0);

INSERT INTO contests
VALUES(0,7,DEFAULT,0,1283);
COMMIT;
