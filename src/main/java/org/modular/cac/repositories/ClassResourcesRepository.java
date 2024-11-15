package org.modular.cac.repositories;

import org.modular.cac.models.ClassResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClassResourcesRepository extends CrudRepository<ClassResource,Long>, PagingAndSortingRepository<ClassResource,Long> {
}
