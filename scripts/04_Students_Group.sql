ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;

CREATE TABLE cac.students_group(
    student_id NUMBER CONSTRAINT sg_student_fk REFERENCES cac.students(student_id),
    group_id NUMBER CONSTRAINT sg_group_fk REFERENCES cac.groups(group_id)
);

CREATE PUBLIC SYNONYM students_group FOR cac.students_group;

INSERT INTO students_group VALUES(0,0);
INSERT INTO students_group VALUES(0,1);
INSERT INTO students_group VALUES(2,0);
INSERT INTO students_group VALUES(3,0);

CREATE or REPLACE VIEW cac.students_groups AS
    (SELECT
        s.student_id,
        s.siiau_code,
        s.first_name,
        s.last_name,
        LISTAGG(g.group_name, ', ') WITHIN GROUP (ORDER BY g.group_name) AS groups,
        s.register_date
    FROM
        students s
    JOIN
        students_group sg ON s.student_id = sg.student_id
    JOIN
        groups g ON sg.group_id = g.group_id
    GROUP BY
        s.student_id, s.first_name,s.last_name,
        s.siiau_code,s.register_date);

