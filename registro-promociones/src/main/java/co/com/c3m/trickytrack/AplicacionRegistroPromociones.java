package co.com.c3m.trickytrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacionRegistroPromociones {

	public static void main(String[] args) {
		SpringApplication.run(AplicacionRegistroPromociones.class);
	}
	
	/*
	@Bean
	public CommandLineRunner demo(EstablecimientoDAO dao) {
		return (args) -> {
			dao.save(new Establecimiento("Mi establecimiento","3115321435",-74.069437,4.697528));
		};
	}*/
}
