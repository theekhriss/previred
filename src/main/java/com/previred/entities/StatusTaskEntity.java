package com.previred.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "estados_tarea")
public class StatusTaskEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6624106114286082913L;
	private Long id;
	private String descripcion;
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
