package duoc.cl.ecomarket.examen.ventas.assemblers;

import duoc.cl.ecomarket.examen.ventas.controller.VentaController;
import duoc.cl.ecomarket.examen.ventas.model.Venta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Value("${productos-api-url}")
    private String productosApiUrl;
    @Value("${envios-api-url}")
    private String enviosApiUrl;
    @Value("${usuarios-api-url}")
    private String usuariosApiUrl;
    @Value("${pagos-api-url}")
    private String pagosApiUrl;

    @Override
    public EntityModel<Venta> toModel(Venta venta) {

        return EntityModel.of(venta,
                linkTo(methodOn(VentaController.class).getById(venta.getVentaId())).withSelfRel(),
                linkTo(methodOn(VentaController.class).getAll()).withRel("Lista de todos los ventas(con metodo get)"),
                linkTo(methodOn(VentaController.class).delete(venta.getVentaId())).withRel("Borrar la venta (con metodo delete)"),
                linkTo(methodOn(VentaController.class).updateVenta(venta.getVentaId(),null)).withRel("Actualizar la venta(con metodo put y con body)"),
                Link.of(productosApiUrl + venta.getProductoId()).withRel("Producto de la venta"),
                Link.of(usuariosApiUrl + venta.getUsuarioId()).withRel("Usuario de la venta"),
                Link.of(pagosApiUrl +"/" +  venta.getPagoId()).withRel("Pago de la venta"),
                Link.of(enviosApiUrl + "/" + venta.getEnvioId()).withRel("Envio de la venta"));
    }

}
