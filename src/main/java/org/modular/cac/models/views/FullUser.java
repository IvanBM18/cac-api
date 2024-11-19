package org.modular.cac.models.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modular.cac.models.Student;
import org.modular.cac.models.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "full_users")
public class FullUser {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "password")
    private String password;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "siiau_code")
    private String siiauCode;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    public Student parseStudent(){
        return new Student(studentId,firstName,lastName,email,siiauCode,registerDate);
    }

    public User parseUser(){
        return new User(userId,password,studentId);
    }
}
