package com.jasper.entity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contractNumber;

}
