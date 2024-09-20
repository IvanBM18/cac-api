package org.modular.cac.repositories;

import org.modular.cac.models.Classes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectsRepository extends PagingAndSortingRepository<Classes,Long>, CrudRepository<Classes,Long> {

    @Query( value = "SELECT c.class_id AS class_id, " +
            "    c.name AS name," +
            "    c.description AS description, " +
            "    c.class_date AS class_date," +
            "    c.group_id AS group_id, " +
            "    c.professor_id AS professor_id" +
            "    FROM Classes c JOIN groups g " +
            "    ON g.group_name = :groupName"
            ,nativeQuery = true)
    List<Classes> findByGroupName(@Param("groupName") String groupName);
}
