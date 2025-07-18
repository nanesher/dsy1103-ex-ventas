package duoc.cl.ecomarket.examen.ventas.service;

import duoc.cl.ecomarket.examen.ventas.model.*;
import duoc.cl.ecomarket.examen.ventas.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${productos-api-url}")
    private String productosApiUrl;
    @Value("${envios-api-url}")
    private String enviosApiUrl;
    @Value("${usuarios-api-url}")
    private String usuariosApiUrl;
    @Value("${pagos-api-url}")
    private String pagosApiUrl;

    public List<Venta> findAll() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas;
    }
    public Venta findById(Integer id) {
        Venta venta = ventaRepository.findById(id).get();
        return venta;
    }
    public Venta save(VentaRequest ventaInicio) {
        Venta venta = new Venta();
        String urlU = usuariosApiUrl + ventaInicio.getIdUsuario();
        String urlP = productosApiUrl + ventaInicio.getIdProducto();
        String urlE = enviosApiUrl;
        String urlPa = pagosApiUrl;

        ResponseEntity<EntityModel<UsuarioDTO>> responseU = restTemplate.exchange(
                urlU,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assert responseU.getBody() != null;
        UsuarioDTO usuarioDTO = responseU.getBody().getContent();
        assert usuarioDTO != null;
        if (!usuarioDTO.getIsValid()){
            throw new RuntimeException("El usuario no está habilidato para una compra");
        }
        venta.setUsuarioId(usuarioDTO.getIdUsuario());
        ResponseEntity<EntityModel<ProductoDTO>> responseP = restTemplate.exchange(
                urlP,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        assert responseP.getBody() != null;
        ProductoDTO productoDTO = responseP.getBody().getContent();
        assert productoDTO != null;
        if (productoDTO.getStock()<1){
            throw new RuntimeException("El producto no tiene stock disponible");
        }
        productoDTO.setStock(productoDTO.getStock()-1);
        venta.setProductoId(productoDTO.getProductoId());
        venta.setPrecioVenta(productoDTO.getPrecio());

        PagoRequestDTO pagoRequestDTO=new PagoRequestDTO();
        pagoRequestDTO.setMetodoPago(ventaInicio.getMetodoPago());
        pagoRequestDTO.setIdUsuario(ventaInicio.getIdUsuario());
        pagoRequestDTO.setIdProducto(ventaInicio.getIdProducto());

        HttpHeaders headerPago = new HttpHeaders();
        headerPago.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PagoRequestDTO> requestPago = new HttpEntity<>(pagoRequestDTO, headerPago);
        ResponseEntity<PagoDTO> responsePago = restTemplate.exchange(urlPa, HttpMethod.POST, requestPago, PagoDTO.class);
        PagoDTO pago = responsePago.getBody();
        assert pago != null;
        if (!pago.getEstado().equals("Aprobado")){
            throw new RuntimeException("El pago no fue procesado");
        }
        venta.setPagoId(pago.getPagoId());

        HttpHeaders headerP = new HttpHeaders();
        headerP.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductoDTO> requestP = new HttpEntity<>(productoDTO, headerP);
        restTemplate.exchange(urlP, HttpMethod.PUT, requestP, Void.class);

        EnvioRequestDTO envioRequestDTO = new EnvioRequestDTO();
        envioRequestDTO.setIdUsuario(ventaInicio.getIdUsuario());
        envioRequestDTO.setTipoEntrega(ventaInicio.getTipoEnvio());

        HttpHeaders headerEnvio = new HttpHeaders();
        headerEnvio.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EnvioRequestDTO> requestEnvio = new HttpEntity<>(envioRequestDTO, headerEnvio);
        System.out.println(requestEnvio);
        ResponseEntity<EnvioDTO> responseEnvio =restTemplate.exchange(urlE, HttpMethod.POST, requestEnvio, EnvioDTO.class);
        EnvioDTO envio = responseEnvio.getBody();
        assert envio != null;
        venta.setEnvioId(envio.getEnvioId());
        return ventaRepository.save(venta);
    }

    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }
    public Venta update(Integer id, Venta venta2) {
        Venta venta1=findById(id);
        venta1.setFechaVenta(venta2.getFechaVenta());
        venta1.setPrecioVenta(venta2.getPrecioVenta());
        venta1.setPagoId(venta2.getPagoId());
        venta1.setProductoId(venta2.getProductoId());
        venta1.setUsuarioId(venta2.getUsuarioId());
        venta1.setEnvioId(venta2.getEnvioId());
        ventaRepository.save(venta1);
        return venta1;
    }
}
