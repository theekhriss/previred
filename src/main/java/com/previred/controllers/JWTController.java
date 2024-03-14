package com.previred.controllers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.previred.dto.TokenDTO;
import com.previred.entities.UserEntity;
import com.previred.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * @author cristopher
 */
@RestController
public class JWTController {
    private static final Logger logger = LogManager.getLogger(JWTController.class);	  

	@Autowired
	private UserService usuarioService;

	@PostMapping("generateJWT")
	public TokenDTO generate(@RequestParam("usuario") String usuario, @RequestParam("clave") String clave) {
		TokenDTO tokenDTO = new TokenDTO();
		try {
			UserEntity usuarioModel = usuarioService.getByLogin(usuario);
			if (usuarioModel == null) {
				tokenDTO.setDescripcion("Usuario y password no coinciden");
				tokenDTO.setToken("");
				return tokenDTO;
			}
			String claveDB = usuarioModel.getClave();
			if (!claveDB.equals(clave)) {
				tokenDTO.setDescripcion("Usuario y password no coinciden");
				tokenDTO.setToken("");
				return tokenDTO;
			}
			String token = getJWTToken(usuario);
			tokenDTO.setToken(token);	
			tokenDTO.setDescripcion("Token Generado exitisamente");
			return tokenDTO;
		}catch(Exception e) {
			tokenDTO.setDescripcion("Usuario y password no coinciden");
			tokenDTO.setToken("");
			logger.error("No se pudo generar el token", e);
			return tokenDTO;
		}
		
	}
	/**
	 * Genera token JWT
	 * @param username
	 * @return
	 */
	private String getJWTToken(String username) {
		String secretKey = "claveSecretaPrevired";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		Date fechaCreacion = new Date(System.currentTimeMillis());
		Date fechaExpiracion = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));

		String token = Jwts
				.builder()
				.setId("previredJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(fechaCreacion)
				.setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
