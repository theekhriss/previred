package com.previred.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.previred.entities.UserEntity;


/**
 * @author cristopher
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> { 
	
}
