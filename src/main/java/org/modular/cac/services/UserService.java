package org.modular.cac.services;

import lombok.RequiredArgsConstructor;
import org.modular.cac.models.Student;
import org.modular.cac.models.User;
import org.modular.cac.models.views.FullUser;
import org.modular.cac.repositories.UserRepository;
import org.modular.cac.repositories.views.FullUserRepository;
import org.modular.cac.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private final UserRepository userRepo;
    private final FullUserRepository fullUser;
    private final StudentService students;

    public FullUser addUser(String email, String password, String firstName, String lastName){
        var possibleStudent = students.searchByEmail(email);
        Long studentId;
        if(possibleStudent.isEmpty()){
            studentId = students.addStudentWithoutCode(new Student(-1L,firstName,lastName,email,"", LocalDateTime.now()))
                    .getStudentId();
        }else if (userRepo.serchByStudentId(possibleStudent.get().getStudentId()).isPresent() ) {
            throw new IllegalArgumentException("Email already in use");
        }else {
            studentId = possibleStudent.get().getStudentId();
        }
        var newUser = userRepo.save(new User(-1L,password,studentId));
        return fullUser.findById(newUser.getUserId()).get();
    }

    public FullUser updateUser(FullUser toUpdate){
        if(toUpdate.getStudentId() == null || toUpdate.getStudentId() == -1L){
            throw new IllegalStateException("Not linked user");
        }

        var relatedStudent = students.searchStudent(toUpdate.getStudentId());
        var fullUserForUpdate = fullUser.findById(toUpdate.getUserId());
        User savedUser;

        if(fullUserForUpdate.isEmpty()){
            throw new IllegalArgumentException("Invalid User Id");
        }

        var emailMatches = students.searchByEmail(relatedStudent.get().getEmail());
        if(emailMatches.isEmpty()){
            var updatedStudent = fullUserForUpdate.get().parseStudent();
            updatedStudent.setEmail(toUpdate.getEmail());
            updatedStudent.setFirstName(toUpdate.getFirstName());
            updatedStudent.setLastName(toUpdate.getLastName());

            var updatedUser = fullUserForUpdate.get().parseUser();
            updatedUser.setPassword(toUpdate.getPassword());

            savedUser = userRepo.save(updatedUser);

        }else {
            if(!emailMatches.get().getStudentId().equals(fullUserForUpdate.get().getStudentId())){
                throw new IllegalArgumentException("Email already in use");
            }

            var updatedStudent = fullUserForUpdate.get().parseStudent();
            updatedStudent.setEmail(toUpdate.getEmail());
            updatedStudent.setFirstName(toUpdate.getFirstName());
            updatedStudent.setLastName(toUpdate.getLastName());

            var updatedUser = fullUserForUpdate.get().parseUser();
            updatedUser.setPassword(toUpdate.getPassword());

            students.updateStudent(updatedStudent);
            savedUser= userRepo.save(updatedUser);
        }
        return fullUser.findById(savedUser.getUserId()).get();
    }

    public boolean deleteUser(Long id){
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return true;
        }else {
            throw new IllegalArgumentException("User Id not Found");
        }
    }

    public FullUser login(String email, String password) throws CredentialNotFoundException {
        var searchedUser = fullUser.searchByEmailAnAndPassword(email,password);
        if(searchedUser.isEmpty()){
            throw new CredentialNotFoundException("User with given email and password not found");
        }
        return searchedUser.get();
    }



}
