package org.modular.cac.student;

import org.modular.cac.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student,Long>, CrudRepository<Student,Long> {


    @Query( value = "Select * from Students s WHERE s.siiau_code = ?1"
    ,nativeQuery = true)
    Optional<Student> findBySiiauCode(String code);

    @Query( value = "Select * FROM Students s " +
            "WHERE (Select lower(s.first_name || s.last_name) FROM DUAL)" +
            "LIKE '%' || ?1 || '%'"
            ,nativeQuery = true)
    //TODO: Test this
    List<Student> findByName(String name);

//    @Query("Select s,g.group as group FROM Student s JOIN groups")
//    Page<Student> findAllWithGroup(Pageable pageable);
}
