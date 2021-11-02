package com.livrariaapi.controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // para usar o properties-test
@Transactional
class LivroControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String dados = "{}";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaCadastrarAutorComDadosCompletos() throws Exception {
		String dados = "\"autorId\": 1,\r\n"
				+ "  \"dataLancamento\": \"1900-01-01\",\r\n"
				+ "  \"numeroPaginas\": 100,\r\n"
				+ "  \"titulo\": \"Aqui vai um título\"";
		
		String dadosRetorno = "\"autorId\": 1,\r\n"
				+ "  \"dataLancamento\": \"1900-01-01\",\r\n"
				+ "  \"numeroPaginas\": 100,\r\n"
				+ "  \"titulo\": \"Aqui vai um título\"";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"))
			.andExpect(MockMvcResultMatchers.content().json(dadosRetorno));
	}

}
