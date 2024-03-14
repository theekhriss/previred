package com.previred.services;

import com.previred.entities.UserEntity;

/**
 * @author cristopher
 */
public interface UserService {
	public void guardar(UserEntity usuarioModel);

	public UserEntity getByLogin(String id);

}
