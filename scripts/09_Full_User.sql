ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;

CREATE OR REPLACE VIEW cac.full_users AS (
SELECT
    u.user_id,
    u.password,
    u.student_id ,
    s.email,
    s.first_name,
    s.last_name,
    s.siiau_code,
    s.register_date
    FROM users u
    JOIN students s ON u.student_id = s.student_id
);