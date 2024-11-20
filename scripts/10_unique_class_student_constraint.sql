ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;

ALTER TABLE cac.attendances
ADD CONSTRAINT attendance_student_class_unique UNIQUE (student_id, class_id);