package com.previred.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previred.entities.TaskEntity;
import com.previred.repositories.TaskRepository;import com.previred.services.TaskService;

@Service("tareaService")
public class TaskServiceImpl implements TaskService{
	@Autowired
    private TaskRepository tareaRepository;
    
    /**
     * metodo para almacenar tareas
     * @param tarea
     */
    public void guardar(TaskEntity tarea) {
        tareaRepository.save(tarea);
       
    }
    

    /**
     * metodo para eliminar tareas
     * @param idTarea
     */
    public void delete(Long idTarea) {
    	tareaRepository.deleteById(idTarea);
    }

   /**
    * obtiene todas las tareas
    * @return
    */
    public List <TaskEntity> getAllTareas() {
       return tareaRepository.findAll();
    }

    /**
     * se obtiene la tarea por id
     * @param idTarea
     * @return
     */
    public TaskEntity getTareaById(Long idTarea) {
        return tareaRepository.findById(idTarea).get();
    }
    /**
     * actualiza la tarea
     * @param tarea
     */
    public void update(TaskEntity tarea) {
    	TaskEntity tareaBD = tareaRepository.findById(tarea.getId()).get();
    	tareaBD.setEstadoTarea(tarea.getEstadoTarea());
    	tareaBD.setNombre(tarea.getNombre());
    	guardar(tareaBD);
    }
}
