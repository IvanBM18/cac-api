package org.modular.cac.codeProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modular.cac.models.CodeProfile;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CodeProfileService {

    private final CodeProfileRepository repo;

    @Autowired
    public CodeProfileService(CodeProfileRepository repo){
        this.repo = repo;
    }

    public List<CodeProfile> getProfiles(Pageable page) {
        return repo.findAll(page).stream().toList();
    }

    /**
     * Like a set adds a code profile if it doesnt exists
     * @param handle code profile to add
     * @return id if added, if it already exists returns it
     */
    public Long addIfNonExistent(String handle){
        var existentCodeProfile = repo.searchByHandle(handle);
        if(existentCodeProfile.isPresent()){
            return existentCodeProfile.get().getCodeProfileId();
        }
        var codeProfile = new CodeProfile(-1L,"CodeForces",handle,null);
        return repo.save(codeProfile).getCodeProfileId();
    }

    public CodeProfile getProfile(String handle){
        return repo.searchByHandle(handle).orElse(new CodeProfile());
    }
    public CodeProfile getProfile(Long id){
        return repo.findById(id).orElse(new CodeProfile());
    }
}
