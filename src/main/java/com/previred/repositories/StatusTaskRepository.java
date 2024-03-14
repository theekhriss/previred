package com.previred.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.previred.entities.StatusTaskEntity;


/**
 * @author cristopher
 */
@Repository
public interface StatusTaskRepository extends JpaRepository<StatusTaskEntity,Long> { 
	
}
