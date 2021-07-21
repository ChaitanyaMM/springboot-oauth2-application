package com.practice.oauth2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.oauth2.entity.Pet;
import com.practice.oauth2.repository.PetRepository;

@Service
public class PetService {
	@Autowired
	private PetRepository petRepository;

	public Pet save(Pet pet) {

		return petRepository.save(pet);
	}

	public Optional<Pet> findById(Long petId) {

		return petRepository.findById(petId);
	}

}
