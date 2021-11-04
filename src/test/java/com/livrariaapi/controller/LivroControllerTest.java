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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.livrariaapi.dto.AutorDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // para usar o properties-test
@Transactional
class LivroControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	private AutorDto cadastrarAutor() throws Exception {
		String jsonAutor = "{\r\n"
				+ "  \"dataNascimento\": \"1990-05-02\",\r\n"
				+ "  \"email\": \"dandy@gmail.com\",\r\n"
				+ "  \"miniCurriculo\": \"Aqui vai o mini curriculo\",\r\n"
				+ "  \"nome\": \"Anderson\"\r\n"
				+ "}";
	
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAutor))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.header().exists("Location"))
				.andExpect(MockMvcResultMatchers.content().json(jsonAutor))
				.andReturn();
	
		String autor = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		return objectMapper.readValue(autor, AutorDto.class);
	}

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
		AutorDto autorDto = cadastrarAutor();
		
		String dados = "{\"idAutor\":"+ autorDto.getId() +" ,"
				+ "\"nome\":\"Anderson\","
				+ "\"email\":\"dandy@gmail.com\","
				+ "\"dataNascimento\":\"1990-05-02\","
				+ "\"miniCurriculo\":\"Aqui vai o mini curriculo\"}";
		
		String dadosRetorno = "{\"autor\":"+ autorDto +" ,"
				+ "\"nome\":\"Anderson\","
				+ "\"email\":\"dandy@gmail.com\","
				+ "\"dataNascimento\":\"1990-05-02\","
				+ "\"miniCurriculo\":\"Aqui vai o mini curriculo\"}";
		
		mvc
		.perform(
				MockMvcRequestBuilders.post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dados))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.header().exists("Location"));
			//.andExpect(MockMvcResultMatchers.content().json(dadosRetorno));
	}

}
