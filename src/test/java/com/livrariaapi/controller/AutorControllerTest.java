package com.livrariaapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.livrariaapi.dto.PerfilDto;
import com.livrariaapi.infra.security.TokenService;
import com.livrariaapi.model.Perfil;
import com.livrariaapi.model.Usuario;
import com.livrariaapi.repository.PerfilRepository;
import com.livrariaapi.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private String token;
	
	@BeforeEach
	public void gerarToken() {
		Usuario logado = new Usuario("Dandy", "dandy", "123456", "email@email.com");
		Perfil admin = perfilRepository.findById(1l).get();
		
		PerfilDto adminDto = modelMapper.map(admin, PerfilDto.class);
		
		logado.adicionarPerfil(adminDto);
		usuarioRepository.save(logado);
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = tokenService.gerarToken(authentication);		
	}

	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {

		String json = "{}";

		mvc.perform(post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deveriaCadastrarAutorComDadosValidos() throws Exception {

		String json = "{\"nome\":\"Nome do Autor\", \"email\":\"autor@email.com\", \"dataDeNascimento\":\"1990-10-23\", \"miniCurriculo\":\"Lorem ipsum\"}";
		String jsonEsperado = "{\"nome\":\"Nome do Autor\", \"email\":\"autor@email.com\", \"dataDeNascimento\":\"23/10/1990\", \"miniCurriculo\":\"Lorem ipsum\"}";

		mvc.perform(post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"))
				.andExpect(content().json(jsonEsperado));
	}

}