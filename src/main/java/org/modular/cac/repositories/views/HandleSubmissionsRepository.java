package org.modular.cac.repositories.views;

import org.modular.cac.models.views.HandleSubmissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandleSubmissionsRepository extends JpaRepository<HandleSubmissions, Long> {

    Page<HandleSubmissions> findByStudentId(Long studentId, Pageable pageable);

    // Método para buscar por siiauCode con paginación
    Page<HandleSubmissions> findByStudent_SiiauCode(String siiauCode, Pageable pageable);

    // Método para buscar por identifier (handle) con paginación
    Page<HandleSubmissions> findByIdentifier(String identifier, Pageable pageable);
}
