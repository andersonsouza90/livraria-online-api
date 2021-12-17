package com.livrariaapi.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class EnviadorEmailFake implements EnviadorDeEmail{
	
	@Async
	public void enviarEmail(String destinatario, String assunto, String mensagem) {
		
		System.out.println("Enviando Email:");
		System.out.println("destinatario" + destinatario);
		System.out.println("assunto" + assunto);
		System.out.println("mensagem" + mensagem);
		
	}

}
