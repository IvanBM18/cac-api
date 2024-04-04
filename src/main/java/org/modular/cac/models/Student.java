package org.modular.cac.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    public Student(){
        this.registerDate = LocalDateTime.now();
    }

    @Id
    private Long student_id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String siiauCode;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registerDate;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + student_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", siiauCode='" + siiauCode + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
