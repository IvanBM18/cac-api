package org.modular.cac.repositories;

import org.modular.cac.views.StudentSubmissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentSubmissionRepository extends JpaRepository<StudentSubmissions,Long> {

    @Query(value = "SELECT * FROM STUDENT_SUBMISSIONS WHERE student_id = :studentId ORDER BY CONTEST_ID",nativeQuery = true)
    public List<StudentSubmissions> findSubmissionsByStudentId(@Param("studentId") Long student_id);

}
