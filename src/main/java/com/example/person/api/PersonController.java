package com.example.person.api;

import com.example.person.dl.dao.PersonEntity;
import com.example.person.dto.Person;
import com.example.person.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;






@RestController
@RequestMapping(value = "/v1/persons")
public class PersonController {


    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private PersonService personService;


    @Autowired
    public PersonController(PersonService personService){
        this.personService=personService;
    }



    @GetMapping(value = "/health")
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("Person Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity addPerson(@RequestBody @Valid Person person) {
        try {
            PersonEntity personEntity = personService.addPerson(person);
            logger.info("Person has been added successfully");
            return new ResponseEntity(personEntity,HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new Person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable Long id) {
        try {
            logger.info("Get Person by Id: "+ id);
            Person person = personService.getPerson(id);
            if (person==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(person);
        }catch (Exception e){
            logger.error("Failed to find person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // MULTI QUERY PARAM
    @GetMapping(value = "/queryFilter")
    public ResponseEntity getAllPersonsByFirstNameAndAge(@RequestParam(value="firstName") String firstName,@RequestParam(value="age") Integer age) {
        try {
            List<Person> persons = personService.getAllPersonsByFirstNameAndAge(firstName,age);
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // MULTI PATH PARAM +  USE SQL QUERY
    @GetMapping(value = "/pathFilter/{firstName}/{age}")
    public ResponseEntity getAllPersonsByFirstNameAndAgeHigherThen(@PathVariable(value="firstName") String firstName,@PathVariable(value="age") Integer age) {
        try {
            List<Person> persons = personService.getAllPersonByFirstNameAndAgeHigherThen(firstName,age);
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find persons",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping
    public ResponseEntity getAllPersons() {
        try {
            List<Person> persons = personService.getAllPersons();
            if (persons==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(persons);
        }catch (Exception e){
            logger.error("Failed to find all persons",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity updatePerson(@PathVariable Long id , @RequestBody Person person) {
        try {
            logger.info("Update Person : "+ person);
            Boolean isUpdated = personService.updatePerson(person,id);
            if(!isUpdated){
                logger.warn("The person not exists and can not be updated");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to update person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) {
        try {
            logger.info("Delete Person by Id: "+ id);
            Boolean isRemoved = personService.removePerson(id);
            if(!isRemoved){
                logger.warn("The person not exists and can not be deleted");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to delete person",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

