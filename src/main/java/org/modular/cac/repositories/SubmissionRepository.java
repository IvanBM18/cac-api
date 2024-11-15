package org.modular.cac.repositories;

import org.modular.cac.models.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubmissionRepository extends CrudRepository<Submission,Long>, PagingAndSortingRepository<Submission,Long> {
}
