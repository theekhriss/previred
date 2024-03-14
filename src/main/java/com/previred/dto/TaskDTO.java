package com.previred.dto;

/**
 * @author cristopher
 */
public class TaskDTO {
	
	private Long id;
	private String nombre;
	private Long  idEstadoTarea;
	private String descripcionEstadoTarea;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getIdEstadoTarea() {
		return idEstadoTarea;
	}
	public void setIdEstadoTarea(Long idEstadoTarea) {
		this.idEstadoTarea = idEstadoTarea;
	}
	public String getDescripcionEstadoTarea() {
		return descripcionEstadoTarea;
	}
	public void setDescripcionEstadoTarea(String descripcionEstadoTarea) {
		this.descripcionEstadoTarea = descripcionEstadoTarea;
	}
	
	

}
