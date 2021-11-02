package com.livrariaapi.controller;

import static org.junit.jupiter.api.Assertions.*;

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
class AutorControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {
		String dados = "{}";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void deveriaCadastrarAutorComDadosCompletos() throws Exception {
		String dados = "{\r\n"
				+ "  \"dataNascimento\": \"1990-05-02\",\r\n"
				+ "  \"email\": \"dandy@gmail.com\",\r\n"
				+ "  \"miniCurriculo\": \"Aqui vai o mini curriculo\",\r\n"
				+ "  \"nome\": \"Anderson\"\r\n"
				+ "}";
		
		String dadosRetorno = "{\r\n"
				+ "  \"dataNascimento\": \"1990-05-02\",\r\n"
				+ "  \"email\": \"dandy@gmail.com\",\r\n"
				+ "  \"miniCurriculo\": \"Aqui vai o mini curriculo\",\r\n"
				+ "  \"nome\": \"Anderson\"\r\n"
				+ "}";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"))
			.andExpect(MockMvcResultMatchers.content().json(dadosRetorno));
	}
	
	@Test
    void naoDeveriaCadastrarUmAutorComDadosVazios() throws Exception {
		String dados = "{\r\n"
				+ "  \"dataNascimento\": \"\",\r\n"
				+ "  \"email\": \"\",\r\n"
				+ "  \"miniCurriculo\": \"\",\r\n"
				+ "  \"nome\": \"\"\r\n"
				+ "}";
	
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
	
	@Test
    void naoDeveriaCadastrarUmAutorComDataDeNascimentoFutura() throws Exception {
		String dados = "{\r\n"
				+ "  \"dataNascimento\": \"2022-05-02\",\r\n"
				+ "  \"email\": \"dandy@gmail.com\",\r\n"
				+ "  \"miniCurriculo\": \"Aqui vai o mini curriculo\",\r\n"
				+ "  \"nome\": \"Anderson\"\r\n"
				+ "}";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
