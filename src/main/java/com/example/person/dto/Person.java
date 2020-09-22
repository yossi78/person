package com.example.person.dto;

import com.example.person.dl.dao.PersonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private Long id;

    @JsonProperty("firstName")
    @CsvBindByName(column = "Fname")
    @NotEmpty
    private String firstName;


    @JsonProperty("lastName")
    @CsvBindByName(column = "lname")
    @NotEmpty
    private String lastName;


    @JsonProperty("age")
    @CsvBindByName(column = "howOld")
    @NotNull(message = "Age can't be null")
    @Min(value = 1, message = "The min value for age is 1")
    @Max(value = 120, message = "The max value for age is 120")
    private Integer age;


    public Person() {
    }

    public Person(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public PersonEntity convertToDao(){
        PersonEntity personEntity = new PersonEntity(id,firstName,lastName,age);
        return personEntity;
    }

}

