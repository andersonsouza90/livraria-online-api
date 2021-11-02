package com.livrariaapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.livrariaapi.dto.AutorDto;
import com.livrariaapi.dto.AutorDtoForm;
import com.livrariaapi.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // para usar o properties-test
public class AutorServiceTest {

  @Mock
  private AutorRepository autorRepository;

  @InjectMocks
  private AutorService autorService;

  @Test
  void deveriaCadastrarUmAutor() {
    AutorDtoForm autorDtoForm = new AutorDtoForm("Autor", "autor@email.com", LocalDate.now(), "Aqui vai um texto grande!....");
    AutorDto autorDto = autorService.cadastrar(autorDtoForm);

    Mockito.verify(autorRepository).save(Mockito.any());

    assertEquals(autorDtoForm.getNome(), autorDto.getNome());
    assertEquals(autorDtoForm.getEmail(), autorDto.getEmail());
    assertEquals(autorDtoForm.getDataNascimento(), autorDto.getDataNascimento());
    assertEquals(autorDtoForm.getMiniCurriculo(), autorDto.getMiniCurriculo());
  }

}

