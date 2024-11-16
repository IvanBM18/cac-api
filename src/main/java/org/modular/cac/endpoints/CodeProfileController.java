package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modular.cac.codeProfile.CodeProfileService;
import org.modular.cac.models.CodeProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/codeProfile")
@Tag(name = "Code Profiles",description = "API for Code profiles")
public class CodeProfileController {

    @Autowired
    CodeProfileService service;

    @GetMapping()
    public List<CodeProfile> getCodeProfiles(@RequestParam(name = "page",defaultValue = "0")int page,
                                     @RequestParam(name = "size",defaultValue = "20")int size){
        Pageable pagedRequest = PageRequest.of(page,size);
        return service.getProfiles(pagedRequest);
    }

    @GetMapping("/{handle}")
    public CodeProfile getCodeProfiles(@PathVariable("handle")String handle){
        return service.getProfile(handle);
    }
}
