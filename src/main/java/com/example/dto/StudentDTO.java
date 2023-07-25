package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class StudentDTO {

    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String  level;
    private String gender;
    private LocalDate createdDate = LocalDate.now();
}
