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
			return modelMapper.map(usuario, UsuarioDto.class);
			
		}

}
