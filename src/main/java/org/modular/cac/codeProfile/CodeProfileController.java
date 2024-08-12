package org.modular.cac.codeProfile;

import org.modular.cac.models.CodeProfile;
import org.modular.cac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/codeProfile")
public class CodeProfileController {

    @Autowired
    CodeProfileService service;

    @GetMapping()
    public List<CodeProfile> getCodeProfiles(@RequestParam(name = "page",defaultValue = "0")int page,
                                     @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pagedRequest = PageRequest.of(page,size);
        return service.getProfiles(pagedRequest);
    }
}
