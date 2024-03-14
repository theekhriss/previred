package com.previred.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previred.entities.StatusTaskEntity;
import com.previred.repositories.StatusTaskRepository;
import com.previred.services.StatusTaskService;

@Service("estadoTareaService")
public class StatusTaskServiceImpl implements StatusTaskService {
	
	@Autowired
	private StatusTaskRepository estadoTareaRepository;

	/**
	 * Funcion para almacenar tarea
	 * 
	 * @param estadoTareaModel
	 */
	public void guardar(StatusTaskEntity estadoTareaModel) {
		estadoTareaRepository.save(estadoTareaModel);
	}

	/**
	 * se obtiene la tarea por id
	 * 
	 * @param idTarea
	 * @return
	 */
	public StatusTaskEntity getTareaById(Long idEstadpTarea) {
		return estadoTareaRepository.findById(idEstadpTarea).get();
	}
}
