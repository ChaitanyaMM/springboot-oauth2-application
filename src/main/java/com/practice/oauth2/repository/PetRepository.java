package com.practice.oauth2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.oauth2.entity.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long>{

}
