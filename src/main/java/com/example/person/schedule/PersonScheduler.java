package com.example.person.schedule;

import com.example.person.dl.dao.PersonEntity;
import com.example.person.dto.Person;
import com.example.person.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





@Service
@EnableScheduling
public class PersonScheduler {

    @Value("${personSvc.personScheduler.files}")
    private List<String> files;

    @Value("${personSvc.personScheduler.urls}")
    private List<String> urls;

    private Logger logger = LoggerFactory.getLogger(PersonScheduler.class);
    private PersonService personService;


    @Autowired
    public PersonScheduler(PersonService personService) {
        this.personService = personService;
    }


    @Scheduled(fixedDelayString = "${personSvc.personScheduler.delay:20000}")
    public void fetchPersonsFromExternal() {
        try {
            List<PersonEntity> personsFromFiles = personService.fetchPersonsFromFiles(files);
            personService.addPersons(personsFromFiles);
            List<PersonEntity> personsFromUrls = personService.fetchPersonsFromUrls(urls);
            personService.addPersons(personsFromUrls);
        } catch (Exception e) {
            logger.error("Failed to fetch Persons from external reasources",e);
        }

    }




}
