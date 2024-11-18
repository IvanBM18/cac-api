package org.modular.cac.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "students")
@Table(name = "students")
public class Student {

    public Student(){
        this.registerDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String siiauCode;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registerDate;

    @Override
    public String toString() {
        return "{" +
                "id=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", siiauCode='" + siiauCode + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }

    @JsonIgnore
    public boolean isValid(){
        return  siiauCode.length() ==  9;
    }
}
