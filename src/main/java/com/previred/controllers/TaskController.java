package com.previred.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.previred.dto.ResponseDTO;
import com.previred.dto.TaskDTO;
import com.previred.entities.StatusTaskEntity;
import com.previred.entities.TaskEntity;
import com.previred.services.StatusTaskService;
import com.previred.services.TaskService;
import com.previred.utils.Constants;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

/**
 * @author cristopher
 */
@RestController
public class TaskController {

	private static final Logger logger = LogManager.getLogger(TaskController.class);
	@Autowired
	private TaskService servicioTarea;
	@Autowired
	private StatusTaskService servicioEstadoTarea;

	/**
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("guardar_tarea")
	public ResponseDTO crearTarea(@RequestBody TaskDTO td) {
		ResponseDTO rrd = new ResponseDTO();
		try {
			TaskEntity tm = new TaskEntity();
			StatusTaskEntity et = servicioEstadoTarea.getTareaById(td.getIdEstadoTarea());
			tm.setEstadoTarea(et);
			tm.setNombre(td.getNombre());
			servicioTarea.guardar(tm);
			rrd.setEstado(Constants.RESPUESTA_REST_OK);
			rrd.setComentario("Tarea Almacenado con exito");
			return rrd;
		} catch (Exception e) {
			rrd.setEstado(Constants.RESPUESTA_REST_FAIL);
			rrd.setComentario("Problemas al almacenar la tarea: " + e.toString());
			logger.error("No se pudo guardar la tarea", e);
			return rrd;

		}

	}

	/**
	 * Funcion para obtener todos los estados de las tareas
	 * 
	 * @return
	 */
	@PostMapping("obtener_todas_tareas")
	public List<TaskDTO> listarTareas() {
		List<TaskDTO> tareasDTO = null;
		try {
			List<TaskEntity> tareasEntity = servicioTarea.getAllTareas();
			tareasDTO = new ArrayList<>();
			for (int i = 0; i < tareasEntity.size(); i++) {
				TaskDTO tdto = new TaskDTO();
				tdto.setDescripcionEstadoTarea(tareasEntity.get(i).getEstadoTarea().getDescripcion());
				tdto.setId(tareasEntity.get(i).getId());
				tdto.setIdEstadoTarea(tareasEntity.get(i).getEstadoTarea().getId());
				tdto.setNombre(tareasEntity.get(i).getNombre());
				tareasDTO.add(tdto);
			}

		} catch (Exception e) {
			logger.error("No se pudo obtener la lista de tareas", e);
			return tareasDTO;
		}
		return tareasDTO;
	}

	/**
	 * Funcion para actualizar la tarea
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("actualizar_tarea")
	public ResponseDTO actualizarTarea(@RequestBody TaskDTO td) {
		ResponseDTO rrd = new ResponseDTO();
		try {
			TaskEntity tareaEntity = servicioTarea.getTareaById(td.getId());
			tareaEntity.setNombre(td.getNombre());
			StatusTaskEntity ete = servicioEstadoTarea.getTareaById(td.getIdEstadoTarea());
			tareaEntity.setEstadoTarea(ete);
			servicioTarea.update(tareaEntity);
			rrd.setEstado(Constants.RESPUESTA_REST_OK);
			rrd.setComentario("Tarea Actualizada con exito");
			return rrd;
		} catch (Exception e) {
			rrd.setEstado(Constants.RESPUESTA_REST_FAIL);
			rrd.setComentario("Problemas al actualizar la tarea: " + e.toString());
			logger.error("No se pudo actualizar la tarea", e);
			return rrd;
		}

	}

	/**
	 * Funcion para eliminar la tarea
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("eliminar_tarea")
	public ResponseDTO eliminarTarea(@RequestBody TaskDTO td) {
		ResponseDTO rrd = new ResponseDTO();
		try {
			servicioTarea.delete(td.getId());
			rrd.setEstado(Constants.RESPUESTA_REST_OK);
			rrd.setComentario("Tarea Eliminada con exito");
			return rrd;
		} catch (Exception e) {
			rrd.setEstado(Constants.RESPUESTA_REST_FAIL);
			rrd.setComentario("Problemas al eliminar la tarea: " + e.toString());
			logger.error("No se pudo eliminar la tarea", e);
			return rrd;
		}

	}
}
