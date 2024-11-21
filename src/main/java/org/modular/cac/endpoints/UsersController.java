package org.modular.cac.endpoints;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modular.cac.models.views.FullUser;
import org.modular.cac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "Users",description = "API for User")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsersController {

    private final UserService service;


    @PostMapping
    public ResponseEntity<FullUser> addUser(@RequestParam(name = "email")String email,
                                            @RequestParam(name = "password")String password,
                                            @RequestParam(name = "firstName")String firstName,
                                            @RequestParam(name = "lastName")String lastName){
        if(StringUtils.isEmpty(email) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(firstName) ||
                StringUtils.isEmpty(lastName)){
            throw new IllegalArgumentException("A Required Property is empty");
        }

        return ResponseEntity.ok(service.addUser(email,password,firstName,lastName));
    }

    @PutMapping
    public ResponseEntity<FullUser> updateUser(@RequestBody FullUser forUpdate){
        return ResponseEntity.ok(service.updateUser(forUpdate));
    }

    @GetMapping
    public ResponseEntity<FullUser> login(@RequestParam(name = "email")String email,
                                          @RequestParam(name = "password")String password) throws CredentialNotFoundException {
        return ResponseEntity.ok(service.login(email, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.deleteUser(id));
    }
}
