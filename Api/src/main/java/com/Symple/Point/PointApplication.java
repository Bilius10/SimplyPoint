package com.Symple.Point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PointApplication {

	//tentar adicionar um mapa
	//ao fim do codigo, refatorar o backend e acabou
	//threads difeentes para enviar email e bater ponto
	public static void main(String[] args) {
		SpringApplication.run(PointApplication.class, args);
	}

}
