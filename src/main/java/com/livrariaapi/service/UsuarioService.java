package com.livrariaapi.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.livrariaapi.dto.UsuarioDto;
import com.livrariaapi.dto.UsuarioFormDto;
import com.livrariaapi.infra.EnviadorDeEmail;
import com.livrariaapi.model.Perfil;
import com.livrariaapi.model.Usuario;
import com.livrariaapi.repository.PerfilRepository;
import com.livrariaapi.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	//biblioteca externa no pom.xml
	@Autowired // o model mapper foi adicionado no beans configurations para não precisar dar new modelMapper() 
	private ModelMapper modelMapper;
		
		@Autowired
		private UsuarioRepository usuarioRepository;
		
		@Autowired
		private PerfilRepository perfilRepository;
		
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		@Autowired
		private EnviadorDeEmail enviadorEmail;
		
		
		public Page<UsuarioDto> listar(Pageable paginacao) {
			
			Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
			
			return usuarios
					.map(t -> modelMapper.map(t, UsuarioDto.class));
		}

		public UsuarioDto cadastrar(UsuarioFormDto dto) {
			Usuario usuario = modelMapper.map(dto, Usuario.class);
			
			Perfil perfil = perfilRepository.getById(dto.getPerfilId());
			
			usuario.adicionarPerfil(perfil);
			
			String senha = new Random().nextInt(999999) + "";
			usuario.setSenha(bCryptPasswordEncoder.encode(senha));
			
			usuario.setId(null);
			usuarioRepository.save(usuario);
			
			//pesquisar biblioteca thymeleaf para construi template de página e customizar o email
			String destinatario = usuario.getEmail();
			String assunto = "Livraria Online - Bem-vindo";
			String mensagem = String.format("Olá %s! \n\n "
					+ "Segue seus dados de acesso ao sistema Livraria online \n "
					+ "Login: %s \n "
					+ "Senha: %s", 
					usuario.getNome(), usuario.getLogin(), senha);
			
			//enviar email
			enviadorEmail.enviarEmail(destinatario, assunto, mensagem);
			
			return modelMapper.map(usuario, UsuarioDto.class);
			
		}

}
