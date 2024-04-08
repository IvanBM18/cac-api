package org.modular.cac.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.id.IdentityGenerator;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    public Student(){
        this.registerDate = LocalDate.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String siiauCode;
    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

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

    public boolean isValid(){
        return  siiauCode.length() == 9;
    }
}
