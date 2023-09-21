package com.gerenciamentotarefa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gerenciamentotarefa")
public class GerenciamentotarefaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentotarefaApplication.class, args);
	}

}
