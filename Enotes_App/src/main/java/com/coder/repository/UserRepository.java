package com.coder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.User;



public interface UserRepository extends JpaRepository<User, Integer> {

	public boolean existsByEmail(String email);

}