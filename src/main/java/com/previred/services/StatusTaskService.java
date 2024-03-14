package com.previred.services;

import com.previred.entities.StatusTaskEntity;

public interface StatusTaskService {

	public void guardar(StatusTaskEntity estadoTareaModel);

	public StatusTaskEntity getTareaById(Long idEstadpTarea);


}
