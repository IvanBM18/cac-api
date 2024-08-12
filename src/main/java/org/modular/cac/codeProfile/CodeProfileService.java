package org.modular.cac.codeProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modular.cac.models.CodeProfile;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
