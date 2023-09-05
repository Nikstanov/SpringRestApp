package com.nikstanov.SpringRestApp.utills;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String mes){
        super(mes);
    }
}
