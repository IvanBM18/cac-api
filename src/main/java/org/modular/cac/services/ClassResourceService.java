package org.modular.cac.services;

import lombok.RequiredArgsConstructor;
import org.modular.cac.models.ClassResource;
import org.modular.cac.repositories.ClassResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClassResourceService {

    private final ClassResourcesRepository repository;

    public Long add(ClassResource resource){
        resource.setResourceId(resource.getResourceId() == null ? -1 : resource.getResourceId());
        return repository.save(resource).getResourceId();
    }
}
