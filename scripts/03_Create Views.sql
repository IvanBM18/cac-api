ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;

CREATE OR REPLACE VIEW cac.group_attendance AS
    SELECT g.group_id,
    g.group_name,
    a.attendance_id,
    a.student_id,
    a.class_id,
    c.name,
    c.professor_id
    FROM attendances a
    JOIN classes c ON c.class_id = a.class_id
    JOIN groups g ON g.group_id = c.group_id;