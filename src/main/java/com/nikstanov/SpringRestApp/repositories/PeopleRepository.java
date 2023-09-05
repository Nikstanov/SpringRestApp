package com.nikstanov.SpringRestApp.repositories;

import com.nikstanov.SpringRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
