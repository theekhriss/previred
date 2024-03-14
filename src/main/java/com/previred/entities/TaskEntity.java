package com.previred.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tareas")
public class TaskEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8303717925230345423L;
	private Long id;
	private String nombre;
	private StatusTaskEntity estadoTarea;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@ManyToOne
	@JoinColumn(name = "id_estado_tarea", nullable = true)
	public StatusTaskEntity getEstadoTarea() {
		return estadoTarea;
	}
	public void setEstadoTarea(StatusTaskEntity estadoTarea) {
		this.estadoTarea = estadoTarea;
	}
	
	
}
