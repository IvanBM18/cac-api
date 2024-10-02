package org.modular.cac.repositories;

import org.modular.cac.models.Classes;
import org.modular.cac.models.Contest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContestRepository  extends PagingAndSortingRepository<Contest,Long>, CrudRepository<Contest,Long> {

}
