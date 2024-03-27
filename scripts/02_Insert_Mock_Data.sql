ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;
COMMIT;

INSERT INTO students(first_name,last_name,register_date,siiau_code)
VALUES('Ivan','Barba macias',TO_DATE('2020 Feb 01','YYYY MON DD'),'219747662');
INSERT INTO students(first_name,last_name,register_date,siiau_code)
VALUES('Dana Caro','Ramirez Algo',TO_DATE('2024 Jan 01','YYYY MON DD'),'219727123');
INSERT INTO students(first_name,last_name,register_date,siiau_code)
VALUES('Moises','Martinez',TO_DATE('2022 Mar 12','YYYY MON DD'),'216123456');
INSERT INTO students(first_name,last_name,register_date,siiau_code)
VALUES('Alexa','Salcedo Arellano',TO_DATE('2024 Dec 12','YYYY MON DD'),'216654321');
INSERT INTO students(first_name,last_name,register_date,siiau_code)
VALUES('Argenis','Algo Robles',TO_DATE('2024 Aug 31','YYYY MON DD'),'216101010');

INSERT INTO users(email,password,student_id)
VALUES ('ivanbarba.m@gmail.com','123456',0);

INSERT INTO groups (group_name,student_id)
VALUES('Basicos',0);
INSERT INTO groups (group_name,student_id)
VALUES('Intermedios',0);
INSERT INTO groups (group_name,student_id)
VALUES('Basicos',1);
INSERT INTO groups (group_name,student_id)
VALUES('Basicos',2);
INSERT INTO groups (group_name,student_id)
VALUES('Basicos',3);

INSERT INTO code_profiles(platform,identifier,student_id)
VALUES('CodeForces','ivanbm',0);
INSERT INTO code_profiles(platform,identifier,student_id)
VALUES('VJudge','ivanbm8',0);

INSERT INTO classes
VALUES(NULL,'Strings I','Introduccion a Strings', TO_DATE('2024 FEB 07','YYYY MON DD'),0);
INSERT INTO classes
VALUES(NULL,'Estructuras de Datos I','Stack, Queue, Deque, Map', TO_DATE('2024 JAN 31','YYYY MON DD'),0);

INSERT INTO class_resources
VALUES(NULL,'U',null,null,'https://codeforces.com/group/cG3CULqTG9/contest/510084',
'CodeForces URL to Contest', DEFAULT,0,0);

INSERT INTO attendances
VALUES(NULL,0,0);
INSERT INTO attendances
VALUES(NULL,0,1);
INSERT INTO attendances
VALUES(NULL,2,0);
INSERT INTO attendances
VALUES(NULL,2,1);
INSERT INTO attendances
VALUES(NULL,3,0);

INSERT INTO contests
VALUES(DEFAULT,7,DEFAULT,0);
COMMIT;
