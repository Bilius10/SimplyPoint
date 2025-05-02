package com.Symple.Point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PointApplication {

	//inserir uma forma de pegar a localização
	//filtro para selecionar mes e ano
	//verificar porque quando eu salvo um info usuario igual, ele cria outro
	//ao fim do codigo, refatorar o backend e acabou
	public static void main(String[] args) {
		SpringApplication.run(PointApplication.class, args);
	}

}
