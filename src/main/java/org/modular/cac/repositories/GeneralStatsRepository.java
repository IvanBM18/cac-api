package org.modular.cac.repositories;

import org.modular.cac.models.GeneralStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GeneralStatsRepository extends JpaRepository<GeneralStats,Long> {

    @Query(value = "SELECT * FROM stat_general sg WHERE LOWER(sg.name) = LOWER(:name)",
            nativeQuery = true)
    public List<GeneralStats> getStats(@Param("name")String name);
}
