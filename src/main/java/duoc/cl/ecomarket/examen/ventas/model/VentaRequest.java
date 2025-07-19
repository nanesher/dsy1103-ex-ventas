package duoc.cl.ecomarket.examen.ventas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VentaRequest {
    @Schema(description = "Identificador unico del usuario de la venta", example = "5")
    private Integer idUsuario;
    @Schema(description = "Identificador unico del producto de la venta", example = "3")
    private Integer idProducto;
    @Schema(description = "Metodo de pago de la venta", example = "Tarjeta de debito")
    private String metodoPago;
    @Schema(description = "Metodo de recepcion valido para la venta", example = "Retiro en tienda")
    private String tipoEnvio;
}
