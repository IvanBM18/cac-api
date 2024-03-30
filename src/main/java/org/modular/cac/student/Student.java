package org.modular.cac.student;


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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "student_id_squence",sequenceName = "student_pk_sequence",initialValue = 0,allocationSize = 1)
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
