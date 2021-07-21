package com.practice.oauth2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.oauth2.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
