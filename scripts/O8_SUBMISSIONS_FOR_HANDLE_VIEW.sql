ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = cac;


CREATE OR REPLACE VIEW cac.handle_submissions AS (
    SELECT
        cp.code_profile_id,
        cp.identifier,
        cp.student_id,
        s.submission_id,
        s.problem,
        s.verdict,
        c.contest_id,
        c.name,
        c.total_problems,
        c.difficulty,
        c.resource_id
    FROM code_profiles cp
    JOIN submissions s on cp.code_profile_id = s.code_profile_id
    JOIN contests c on c.contest_id = s.contest_id
);