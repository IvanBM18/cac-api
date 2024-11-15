package org.modular.cac.repositories;

import org.modular.cac.models.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends CrudRepository<Attendance,Long>, PagingAndSortingRepository<Attendance,Long> {

    @Query(nativeQuery = true,
            value = "Select * from attendances where student_id = :studentId")
    public List<Attendance> getAllByStudent(@Param("studentId") Long studentId );

}
