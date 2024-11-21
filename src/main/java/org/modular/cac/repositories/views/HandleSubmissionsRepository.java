package org.modular.cac.repositories.views;

import org.modular.cac.models.views.HandleSubmissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HandleSubmissionsRepository extends JpaRepository<HandleSubmissions, Long>, PagingAndSortingRepository<HandleSubmissions,Long> {

    Page<HandleSubmissions> findByStudentId(Long studentId, Pageable pageable);


    @Query(value = """
            SELECT hs.*
            FROM handle_submissions hs
            JOIN students st ON hs.student_id = st.student_id
            WHERE st.siiau_code = :siiauCode
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM handle_submissions hs
            JOIN students st ON hs.student_id = st.student_id
            WHERE st.siiau_code = :siiauCode
            """,
            nativeQuery = true)
    Page<HandleSubmissions> findBySiiauCode(@Param("siiauCode") String siiauCode, Pageable pageable);


    Page<HandleSubmissions> findByIdentifier(String identifier, Pageable pageable);
}
