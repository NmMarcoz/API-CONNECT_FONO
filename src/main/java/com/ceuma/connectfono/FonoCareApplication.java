package com.ceuma.connectfono;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ceuma.connectfono.utils.OsValidator;

import java.io.File;


@SpringBootApplication
public class FonoCareApplication {
	public static void main(String[] args) {
		//Dotenv dotenv = Dotenv.load();
		String dbPath = OsValidator.determineDbPath();

		new File(dbPath).mkdirs();
		SpringApplication.run(FonoCareApplication.class, args);
		System.out.println("Rodando na 8080");
	}

}
