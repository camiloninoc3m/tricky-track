package co.com.c3m.trickytrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacionConsultaPromociones {

	public static void main(String[] args) {
		SpringApplication.run(AplicacionConsultaPromociones.class);
	}
	
	/*
	 * 
	 * @Autowired 
	private EstablecimientoDAO establecimientoDao;
	
	@Autowired 
	private PromocionDAO promocionDao;
	
	@Autowired 
	private ClienteDAO clienteDao;
	
	@Autowired 
	private CuponDAO promocionClienteDao;
	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			Date hoyTemprano = FechaUtil.contruirInicioDia(new Date());
			Date hoyTarde = FechaUtil.contruirFinDia(new Date());
		
			Establecimiento establecimiento = new Establecimiento("Mi establecimiento","3115321435",-74.069437,4.697528);
			
			Promocion promocion1 = new Promocion("Mi promocion 1", 10000, new Date(), new Date(), establecimiento);
			Promocion promocion2 = new Promocion("Mi promocion 2", 10000, hoyTemprano, hoyTarde, establecimiento);
			promocion2.setCantidad(100);
			Promocion promocion3 = new Promocion("Mi promocion 3", 10000, hoyTemprano, hoyTarde, establecimiento);
			promocion3.setCantidad(0);
			Promocion promocion4 = new Promocion("Mi promocion 4", 10000, hoyTemprano,	hoyTarde, establecimiento);
			promocion4.setCantidad(1);
			
			Cliente cliente = new Cliente("Mi cliente", "3115321435", 29, TipoGenero.HOMBRE);
			Cupon promocionCliente = new Cupon(promocion4, cliente, "");
			
			establecimientoDao.save(establecimiento);
			promocionDao.save(promocion1);
			promocionDao.save(promocion2);
			promocionDao.save(promocion3);
			promocionDao.save(promocion4);
			clienteDao.save(cliente);
			promocionClienteDao.save(promocionCliente);
		};
	}*/
}
