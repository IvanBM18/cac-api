package org.modular.cac.repositories.views;

import org.modular.cac.models.views.FullUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FullUserRepository extends JpaRepository<FullUser,Long> {

    @Query(nativeQuery = true, name = """
            SELECT * FROM full_users WHERE student_id = :studentId
            """)
    public Optional<FullUser> searchByStudentId(@Param("studentId")Long studentId);
    @Query(nativeQuery = true, name = """
            SELECT * FROM full_users WHERE email = :email
            """)
    public Optional<FullUser> searchByEmail(@Param("email")String email);
}
