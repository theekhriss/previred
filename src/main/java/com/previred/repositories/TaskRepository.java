package com.previred.repositories;



import org.springframework.stereotype.Repository;

import com.previred.entities.TaskEntity;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author cristopher
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> { 
	
}

