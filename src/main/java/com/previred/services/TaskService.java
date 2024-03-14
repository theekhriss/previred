package com.previred.services;

import com.previred.entities.TaskEntity;

import java.util.List;

/**
 * @author cristopher
 */

public interface TaskService {

	public void guardar(TaskEntity tarea);

	public void delete(Long idTarea);

	public List<TaskEntity> getAllTareas();

	public TaskEntity getTareaById(Long idTarea);

	public void update(TaskEntity tarea);

}
