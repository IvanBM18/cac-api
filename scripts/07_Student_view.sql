ALTER SESSION SET CURRENT_SCHEMA = cac;

CREATE OR REPLACE VIEW STUDENT_SUBMISSIONS AS(
    SELECT C.contest_id,
    c.total_problems,
    c.difficulty,
    s.verdict,
    st.student_id
    FROM CONTESTS c
    JOIN SUBMISSIONS s ON c.contest_id = s.contest_id
    JOIN CODE_PROFILES cp ON cp.code_profile_id = s.code_profile_id
    JOIN STUDENTS st ON st.student_id = st.student_id
);