package com.nikstanov.SpringRestApp.controllers;

import com.nikstanov.SpringRestApp.dto.PersonDTO;
import com.nikstanov.SpringRestApp.models.Person;
import com.nikstanov.SpringRestApp.services.PeopleService;
import com.nikstanov.SpringRestApp.utills.PersonErrorResponse;
import com.nikstanov.SpringRestApp.utills.PersonNotCreatedException;
import com.nikstanov.SpringRestApp.utills.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError err : errors){
                message.append(err.getField()).append(" - ").append(err.getDefaultMessage()).append(';');
            }

            throw new PersonNotCreatedException(message.toString());
        }

        peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse("Person with this id not found", System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlerExceptionNotCreated(PersonNotCreatedException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}