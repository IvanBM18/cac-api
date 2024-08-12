package org.modular.cac.codeProfile;

import org.modular.cac.models.CodeProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeProfileRepository extends  PagingAndSortingRepository<CodeProfile,Long>, CrudRepository<CodeProfile,Long> {

}
