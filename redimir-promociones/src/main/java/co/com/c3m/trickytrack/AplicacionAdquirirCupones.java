package co.com.c3m.trickytrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacionAdquirirCupones {
	
	public static void main(String[] args) {
		SpringApplication.run(AplicacionAdquirirCupones.class);
	}

	/*
	@Autowired 
	private ClienteDAO clienteDao;
	
	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			Cliente cliente = new Cliente("Mi cliente", "3115321435", 29, TipoGenero.HOMBRE);
			clienteDao.save(cliente);
		};
	}
	*/
}
