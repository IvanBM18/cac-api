package org.modular.cac.codeProfile;

import org.modular.cac.models.CodeProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeProfileRepository extends  PagingAndSortingRepository<CodeProfile,Long>, CrudRepository<CodeProfile,Long> {

    @Query(value = "SELECT * FROM code_profiles WHERE identifier = :handle", nativeQuery = true)
    public Optional<CodeProfile> searchByHandle(@Param("handle") String handle);

}
