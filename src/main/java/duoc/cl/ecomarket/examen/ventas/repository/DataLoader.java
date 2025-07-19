package duoc.cl.ecomarket.examen.ventas.repository;

import duoc.cl.ecomarket.examen.ventas.model.Venta;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.Locale;
import java.util.Random;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {
    @Autowired
    private VentaRepository ventaRepository;
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es"));
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            Venta venta = new Venta();
            venta.setProductoId(faker.number().numberBetween(1,15));
            venta.setUsuarioId(faker.number().numberBetween(1,15));
            venta.setPrecioVenta(1 + random.nextInt(10000));
            venta.setFechaVenta(LocalDate.now());
            venta.setPagoId(faker.number().numberBetween(1,15));
            venta.setEnvioId(faker.number().numberBetween(1,15));

            ventaRepository.save(venta);
        }
    }
}
