package duoc.cl.ecomarket.examen.ventas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentasApplication.class, args);
	}
	@Value("${productos-api-url}")
	private String productosApiUrl;
	@Value("${envios-api-url}")
	private String enviosApiUrl;
	@Value("${usuarios-api-url}")
	private String usuariosApiUrl;
	@Value("${pagos-api-url}")
	private String pagosApiUrl;

}
