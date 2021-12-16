package com.livrariaapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
	private PerfilRepository perfilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private String token;

	@BeforeEach
	public void gerarToken() {
		Usuario logado = new Usuario("Anderson", "Anderson", "123456");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = tokenService.gerarToken(authentication);
	}

	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {
		String json = "{}";

		mvc.perform(post("/autores").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization",
				"Bearer " + token)).andExpect(status().isBadRequest());
	}

	@Test
	void DeveriaCadastrarAutorComDadosCompletos() throws Exception {
		String json = "{\"nome\":\"fulano da silva\"," + "\"email\":\"fulano@fulano.com\","
				+ "\"nascimento\":\"1990-01-01\"," + "\"miniCurriculo\":\"livros do fulano\"}";
		
		String jsonRetornado = "{\"nome\":\"fulano da silva\"," + "\"email\":\"fulano@fulano.com\","
				+ "\"miniCurriculo\":\"livros do fulano\"}";

		mvc.perform(post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json).header("Authorization", "Bearer " + token))
				.andExpect(status()
				.isCreated())
				.andExpect(header()
				.exists("Location"))
				.andExpect(content()
				.json(jsonRetornado));

	}

}
