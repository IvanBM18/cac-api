package org.modular.cac.repositories;

import org.modular.cac.models.Classes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContestRepository  extends PagingAndSortingRepository<Classes,Long>, CrudRepository<Classes,Long> {

}
