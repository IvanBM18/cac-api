ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;

CREATE OR REPLACE VIEW cac.STUDENT_SUBMISSIONS AS(
    SELECT c.contest_id,
    c.total_problems,
    c.difficulty,
    s.verdict,
    fp.student_id
    FROM CONTESTS c
    LEFT JOIN SUBMISSIONS s ON s.contest_id = c.contest_id
    LEFT JOIN (SELECT s.student_id,cp.code_profile_id FROM students s JOIN CODE_PROFILES cp ON s.student_id = cp.student_id) fp
        ON fp.code_profile_id  = s.code_profile_id
);