package com.practice.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.oauth2.entity.AnimalType;
import com.practice.oauth2.entity.Pet;

@RestController
@RequestMapping("/api/pet/")
public class PetController {

	@GetMapping("/sample")
	public Pet samplePet() {

		Pet pet = new Pet();
		pet.setAnimalType(AnimalType.DOG);
		pet.setId(1L);
		pet.setName("Runo");

		return pet;
	}

}
