package com.previred.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previred.entities.UserEntity;
import com.previred.repositories.UserRepository;
import com.previred.services.UserService;


@Service("usuarioService")
public class UserServiceImpl implements UserService {
	@Autowired
    private UserRepository usuarioRepository;
	/**
	 * funcion para almacenar usuario
	 * @param usuarioModel
	 */
	public void guardar(UserEntity usuarioModel) {
		usuarioRepository.save(usuarioModel);
	}
	public UserEntity getByLogin(String id) {
		return usuarioRepository.getReferenceById(id);
	}

}